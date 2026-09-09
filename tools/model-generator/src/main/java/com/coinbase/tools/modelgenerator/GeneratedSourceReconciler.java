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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Locale;
import java.util.TreeMap;

/** Reconciles only manifest-owned generated files; hand-authored SDK files are never glob-deleted. */
public final class GeneratedSourceReconciler {
  private static final ObjectMapper JSON = new ObjectMapper();

  private GeneratedSourceReconciler() {}

  public static List<String> diff(Path outputRoot, Map<Path, String> generated) throws IOException {
    return diff(outputRoot, generated, Collections.emptySet(), null);
  }

  public static List<String> diff(
      Path outputRoot,
      Map<Path, String> generated,
      Set<String> protectedFiles,
      Path manifest)
      throws IOException {
    validateCaseInsensitivePaths(outputRoot, generated.keySet());
    List<String> changes = new ArrayList<>();
    for (Map.Entry<Path, String> entry : new TreeMap<>(generated).entrySet()) {
      String relative = entry.getKey().toString().replace('\\', '/');
      if (protectedFiles.contains(relative)) {
        continue;
      }
      Path target = outputRoot.resolve(entry.getKey());
      String existing = Files.exists(target) ? Files.readString(target) : null;
      if (!entry.getValue().equals(existing)) {
        changes.add((existing == null ? "ADD " : "CHANGE ") + entry.getKey());
      }
    }
    if (manifest != null) {
      for (String stale : manifestEntries(manifest)) {
        if (!generated.containsKey(Path.of(stale))
            && !protectedFiles.contains(stale)
            && Files.exists(outputRoot.resolve(stale))) {
          changes.add("DELETE " + stale);
        }
      }
    }
    Collections.sort(changes);
    return Collections.unmodifiableList(changes);
  }

  public static void write(Path outputRoot, Map<Path, String> generated) throws IOException {
    write(outputRoot, generated, Collections.emptySet(), null);
  }

  public static void write(
      Path outputRoot, Map<Path, String> generated, Set<String> protectedFiles, Path manifest)
      throws IOException {
    validateCaseInsensitivePaths(outputRoot, generated.keySet());
    for (Map.Entry<Path, String> entry : new TreeMap<>(generated).entrySet()) {
      String relative = entry.getKey().toString().replace('\\', '/');
      if (protectedFiles.contains(relative)) {
        continue;
      }
      Path target = outputRoot.resolve(entry.getKey());
      if (Files.exists(target) && entry.getValue().equals(Files.readString(target))) {
        continue;
      }
      Files.createDirectories(target.getParent());
      Files.writeString(target, entry.getValue(), StandardCharsets.UTF_8);
    }
    if (manifest != null) {
      for (String stale : manifestEntries(manifest)) {
        if (!generated.containsKey(Path.of(stale)) && !protectedFiles.contains(stale)) {
          Files.deleteIfExists(outputRoot.resolve(stale));
        }
      }
      writeManifest(manifest, generated.keySet());
    }
  }

  private static void validateCaseInsensitivePaths(Path outputRoot, Set<Path> generated)
      throws IOException {
    Map<String, Path> generatedByFoldedPath = new LinkedHashMap<>();
    for (Path relative : generated) {
      String normalized = relative.toString().replace('\\', '/');
      Path previous = generatedByFoldedPath.putIfAbsent(normalized.toLowerCase(Locale.ROOT), relative);
      if (previous != null && !previous.equals(relative)) {
        throw new IOException(
            "Generated source paths differ only by case: " + previous + " and " + relative);
      }
      Path target = outputRoot.resolve(relative);
      Path parent = target.getParent();
      if (parent == null || !Files.isDirectory(parent)) {
        continue;
      }
      try (java.util.stream.Stream<Path> children = Files.list(parent)) {
        Path collision =
            children
                .filter(child -> !child.getFileName().equals(target.getFileName()))
                .filter(
                    child ->
                        child.getFileName()
                            .toString()
                            .equalsIgnoreCase(target.getFileName().toString()))
                .findFirst()
                .orElse(null);
        if (collision != null) {
          throw new IOException(
              "Generated source path collides case-insensitively with existing file: "
                  + relative
                  + " and "
                  + outputRoot.relativize(collision));
        }
      }
    }
  }

  /** Loads manifest-owned source content for isolated generation/check comparisons. */
  static Map<Path, String> readOwnedSources(Path outputRoot, Path manifest) throws IOException {
    Map<Path, String> sources = new LinkedHashMap<>();
    for (String entry : manifestEntries(manifest)) {
      Path relative = Path.of(entry);
      Path source = outputRoot.resolve(relative);
      if (!Files.isRegularFile(source)) {
        throw new IOException("Generated manifest references missing source: " + entry);
      }
      sources.put(relative, Files.readString(source));
    }
    return sources;
  }

  static Set<String> manifestEntries(Path manifest) throws IOException {
    if (!Files.exists(manifest)) {
      return Collections.emptySet();
    }
    Set<String> entries = new LinkedHashSet<>();
    for (String entry : JSON.readValue(Files.readString(manifest), new TypeReference<List<String>>() {})) {
      Path relative = Path.of(entry).normalize();
      if (relative.isAbsolute() || relative.startsWith("..")) {
        throw new IOException("Generated manifest contains unsafe path: " + entry);
      }
      entries.add(relative.toString().replace('\\', '/'));
    }
    return entries;
  }

  static void writeManifest(Path manifest, Set<Path> paths) throws IOException {
    List<String> entries = new ArrayList<>();
    for (Path path : paths) {
      entries.add(path.toString().replace('\\', '/'));
    }
    Collections.sort(entries);
    Files.createDirectories(manifest.getParent());
    Files.writeString(
        manifest,
        JSON.writerWithDefaultPrettyPrinter().writeValueAsString(entries) + "\n",
        StandardCharsets.UTF_8);
  }
}
