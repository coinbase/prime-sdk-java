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

  private static final Pattern CLASS_DOC =
      Pattern.compile(
          "/\\*\\*((?:(?!\\*/).)*)\\*/\\s*(?:@\\w+(?:\\([^)]*\\))?\\s*)*public\\s+(?:final\\s+)?(?:class|interface)\\s+\\w+",
          Pattern.DOTALL);
  private static final Pattern FIELD =
      Pattern.compile(
          "((?:/\\*\\*(?:(?!\\*/).)*\\*/\\s*)*)((?:@\\w+(?:\\([^)]*\\))?\\s*)*)private\\s+[\\w.<>\\[\\], ?]+?\\s+(\\w+)\\s*;",
          Pattern.DOTALL);
  private static final Pattern SERVICE_METHOD =
      Pattern.compile("(?m)^  \\w+Response (\\w+)\\(");

  private static final Pattern SERVICE_DOC =
      Pattern.compile(
          "(?m)^(  /\\*\\*(?:(?!\\*/).)*?\\*/)\\s*(?:@\\w+\\s*)*\\w+Response (\\w+)\\(",
          Pattern.DOTALL);

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

  /** The existing class Javadoc, an empty string if documented as none, or {@code fallback} if new. */
  String classDocumentation(Path file, String fallback) {
    java.util.Optional<String> source = read(file);
    if (!source.isPresent()) return fallback;
    Matcher matcher = CLASS_DOC.matcher(source.get());
    return matcher.find() ? clean(matcher.group(1)) : "";
  }

  /** Fields declared before the nested builder, in declaration order. */
  Map<String, Member> fields(Path file) {
    java.util.Optional<String> source = read(file);
    if (!source.isPresent()) return Collections.emptyMap();
    String text = source.get();
    int builder = text.indexOf("public static class Builder");
    if (builder >= 0) text = text.substring(0, builder);
    Map<String, Member> fields = new LinkedHashMap<>();
    Matcher matcher = FIELD.matcher(text);
    while (matcher.find()) {
      fields.put(
          matcher.group(3),
          new Member(documentation(matcher.group(1)), matcher.group(2).contains("@JsonProperty")));
    }
    return fields;
  }

  /** Service interface method names in declaration order. */
  List<String> serviceMethods(Path file) {
    List<String> names = new ArrayList<>();
    java.util.Optional<String> source = read(file);
    if (!source.isPresent()) return names;
    Matcher matcher = SERVICE_METHOD.matcher(source.get());
    while (matcher.find()) names.add(matcher.group(1));
    return names;
  }

  /** Existing service-interface Javadoc blocks (verbatim, with indentation) keyed by method name. */
  Map<String, String> serviceMethodDocs(Path file) {
    Map<String, String> docs = new LinkedHashMap<>();
    java.util.Optional<String> source = read(file);
    if (!source.isPresent()) return docs;
    Matcher matcher = SERVICE_DOC.matcher(source.get());
    while (matcher.find()) docs.put(matcher.group(2), matcher.group(1));
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

  private static String documentation(String blocks) {
    List<String> parts = new ArrayList<>();
    Matcher matcher = Pattern.compile("/\\*\\*((?:(?!\\*/).)*)\\*/", Pattern.DOTALL).matcher(blocks);
    while (matcher.find()) parts.add(clean(matcher.group(1)));
    return String.join(SourceTemplates.DOC_SEPARATOR, parts);
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
