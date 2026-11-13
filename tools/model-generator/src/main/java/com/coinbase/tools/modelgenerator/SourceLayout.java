/*
 * Copyright 2026-present Coinbase Global, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.coinbase.tools.modelgenerator;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Reads the member layout of client-surface sources that already exist, so regeneration keeps their
 * field order and documentation instead of re-deriving them from the spec.
 *
 * <p>Only presentation is taken from the existing file: which members exist, their types, and every
 * annotation still come from the spec. Members the file does not know about are new and use spec
 * order and spec documentation. This is what keeps an unchanged spec from rewriting vended classes.
 */
final class SourceLayout {
  static final SourceLayout NONE = new SourceLayout(null);


  /** A field that is already declared by an existing class. */
  static final class Member {
    private final String documentation;
    private final boolean annotated;

    Member(String documentation, boolean annotated) {
      this.documentation = documentation;
      this.annotated = annotated;
    }

    /** Existing field documentation, or an empty string when the field had none. */
    String documentation() {
      return documentation;
    }

    /** Whether the existing field carries {@code @JsonProperty}. */
    boolean annotated() {
      return annotated;
    }
  }

  private final Path sourceRoot;

  private SourceLayout(Path sourceRoot) {
    this.sourceRoot = sourceRoot;
  }

  static SourceLayout of(Path sourceRoot) {
    return new SourceLayout(sourceRoot);
  }

  /**
   * Chooses the file whose layout a class should inherit: the class's own file when it exists,
   * otherwise the first existing alias file (a canonical class that renames a vended one keeps the
   * vended layout), otherwise the class's own, absent file.
   */
  Path reference(Path file, List<Path> aliasFiles) {
    if (exists(file)) return file;
    for (Path alias : aliasFiles) {
      if (exists(alias)) return alias;
    }
    return file;
  }

  boolean exists(Path file) {
    return sourceRoot != null && Files.isRegularFile(sourceRoot.resolve(file));
  }

  /** Whether the existing file contains {@code text}; false for files that do not exist yet. */
  boolean contains(Path file, String text) {
    return read(file).map(source -> source.contains(text)).orElse(false);
  }

  private static final Pattern CLASS_DECLARATION =
      Pattern.compile("(?m)^public\\s+(?:final\\s+)?(?:class|interface)\\s");
  private static final Pattern SERVICE_METHOD = Pattern.compile("^  \\w+Response (\\w+)\\(.*");

  /** The existing class Javadoc, an empty string if documented as none, or {@code fallback} if new. */
  String classDocumentation(Path file, String fallback) {
    java.util.Optional<String> source = read(file);
    if (!source.isPresent()) return fallback;
    Matcher declaration = CLASS_DECLARATION.matcher(source.get());
    if (!declaration.find()) return "";
    String head = source.get().substring(0, declaration.start());
    int end = head.lastIndexOf("*/");
    int start = end < 0 ? -1 : head.lastIndexOf("/**", end);
    // Only annotations may sit between the Javadoc and the class declaration.
    if (start < 0 || !head.substring(end + 2).replaceAll("@\\w+(?:\\([^)]*\\))?", "").trim().isEmpty()) {
      return "";
    }
    return clean(head.substring(start + 3, end));
  }

  /**
   * Fields declared before the nested builder, in declaration order. Scanned linearly: Javadoc
   * blocks and annotations accumulate until the next {@code private} declaration claims them.
   */
  Map<String, Member> fields(Path file) {
    java.util.Optional<String> source = read(file);
    if (!source.isPresent()) return Collections.emptyMap();
    String text = source.get();
    int builder = text.indexOf("public static class Builder");
    if (builder >= 0) text = text.substring(0, builder);
    Map<String, Member> fields = new LinkedHashMap<>();
    List<String> docs = new ArrayList<>();
    boolean annotated = false;
    int index = 0;
    while (index < text.length()) {
      char current = text.charAt(index);
      if (Character.isWhitespace(current)) {
        index++;
      } else if (text.startsWith("/**", index)) {
        int end = text.indexOf("*/", index + 3);
        if (end < 0) break;
        docs.add(clean(text.substring(index + 3, end)));
        index = end + 2;
      } else if (text.startsWith("/*", index)) {
        int end = text.indexOf("*/", index + 2);
        if (end < 0) break;
        index = end + 2;
      } else if (text.startsWith("//", index)) {
        int end = text.indexOf('\n', index);
        index = end < 0 ? text.length() : end + 1;
      } else if (current == '@') {
        int name = index + 1;
        while (name < text.length() && Character.isJavaIdentifierPart(text.charAt(name))) name++;
        if (text.substring(index + 1, name).equals("JsonProperty")) annotated = true;
        index = name;
        int open = name;
        while (open < text.length() && text.charAt(open) == ' ') open++;
        if (open < text.length() && text.charAt(open) == '(') {
          int depth = 0;
          for (; open < text.length(); open++) {
            if (text.charAt(open) == '(') depth++;
            else if (text.charAt(open) == ')' && --depth == 0) break;
          }
          index = open + 1;
        }
      } else if (text.startsWith("private ", index)) {
        int end = text.indexOf(';', index);
        if (end < 0) break;
        String declaration = text.substring(index, end).trim();
        int space = declaration.lastIndexOf(' ');
        fields.put(
            declaration.substring(space + 1),
            new Member(String.join(SourceTemplates.DOC_SEPARATOR, docs), annotated));
        docs = new ArrayList<>();
        annotated = false;
        index = end + 1;
      } else {
        // Any other member or clause ends the pending Javadoc and annotations.
        docs = new ArrayList<>();
        annotated = false;
        int end = index;
        while (end < text.length() && ";{}".indexOf(text.charAt(end)) < 0) end++;
        index = end + 1;
      }
    }
    return fields;
  }

  /** Service interface method names in declaration order. */
  List<String> serviceMethods(Path file) {
    return new ArrayList<>(serviceMethodDocs(file).keySet());
  }

  /**
   * Existing service-interface methods in declaration order, each with its verbatim Javadoc block
   * (with indentation) or an empty string when the method was undocumented.
   */
  Map<String, String> serviceMethodDocs(Path file) {
    Map<String, String> docs = new LinkedHashMap<>();
    java.util.Optional<String> source = read(file);
    if (!source.isPresent()) return docs;
    StringBuilder block = null;
    String pending = "";
    for (String line : source.get().split("\\R")) {
      if (block == null && line.startsWith("  /**")) {
        block = new StringBuilder();
      }
      if (block != null) {
        if (block.length() > 0) block.append('\n');
        block.append(line);
        if (line.contains("*/")) {
          pending = block.toString();
          block = null;
        }
        continue;
      }
      Matcher method = SERVICE_METHOD.matcher(line);
      if (method.matches()) {
        docs.put(method.group(1), pending);
        pending = "";
      } else if (!line.startsWith("  @")) {
        pending = "";
      }
    }
    return docs;
  }

  /** Stable ordering: items the file already declares keep its order; new items follow. */
  static <T> List<T> order(Map<String, ?> existing, List<T> items, Function<T, String> key) {
    List<T> ordered = new ArrayList<>(items);
    if (existing.isEmpty()) return ordered;
    List<String> names = new ArrayList<>(existing.keySet());
    ordered.sort(
        Comparator.comparingInt(
            item -> {
              int index = names.indexOf(key.apply(item));
              return index < 0 ? Integer.MAX_VALUE : index;
            }));
    return ordered;
  }

  private java.util.Optional<String> read(Path file) {
    if (!exists(file)) return java.util.Optional.empty();
    try {
      return java.util.Optional.of(Files.readString(sourceRoot.resolve(file)));
    } catch (IOException e) {
      throw new UncheckedIOException("Cannot read existing source " + file, e);
    }
  }

  /**
   * Whether the existing file declares {@code text}; {@code fallback} when the class is new, so
   * new classes follow the generator's default and existing ones keep what they have.
   */
  boolean declares(Path file, String text, boolean fallback) {
    return exists(file) ? contains(file, text) : fallback;
  }

  private static String clean(String javadoc) {
    StringBuilder text = new StringBuilder();
    for (String line : javadoc.split("\\R")) {
      String stripped = line.replaceFirst("^\\s*\\*?\\s?", "").trim();
      if (!stripped.isEmpty()) {
        if (text.length() > 0) text.append(' ');
        text.append(stripped);
      }
    }
    return text.toString();
  }
}
