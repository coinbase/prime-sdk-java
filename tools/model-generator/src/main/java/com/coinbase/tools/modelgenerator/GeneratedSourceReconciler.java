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
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/** Reconciles only manifest-owned generated files; hand-authored SDK files are never glob-deleted. */
public final class GeneratedSourceReconciler {
  private static final ObjectMapper JSON = new ObjectMapper();
  private GeneratedSourceReconciler() {}
  public static List<String> diff(Path outputRoot, Map<Path, String> generated) throws IOException { return diff(outputRoot, generated, Collections.emptySet(), null); }
  public static List<String> diff(Path outputRoot, Map<Path, String> generated, Set<String> protectedFiles, Path manifest) throws IOException {
    List<String> changes = new ArrayList<>();
    for (Map.Entry<Path, String> entry : new TreeMap<>(generated).entrySet()) { Path target=outputRoot.resolve(entry.getKey()); String existing=Files.exists(target)?Files.readString(target):null; if (!entry.getValue().equals(existing)) changes.add((existing==null?"ADD ":"CHANGE ")+entry.getKey()); }
    if (manifest != null) for (String stale : manifestEntries(manifest)) if (!generated.containsKey(Path.of(stale)) && !protectedFiles.contains(stale) && Files.exists(outputRoot.resolve(stale))) changes.add("DELETE " + stale);
    Collections.sort(changes); return Collections.unmodifiableList(changes);
  }
  public static void write(Path outputRoot, Map<Path, String> generated) throws IOException { write(outputRoot, generated, Collections.emptySet(), null); }
  public static void write(Path outputRoot, Map<Path, String> generated, Set<String> protectedFiles, Path manifest) throws IOException {
    for (Map.Entry<Path, String> entry : new TreeMap<>(generated).entrySet()) { Path target=outputRoot.resolve(entry.getKey()); if (Files.exists(target)&&entry.getValue().equals(Files.readString(target))) continue; Files.createDirectories(target.getParent()); Files.writeString(target,entry.getValue(),StandardCharsets.UTF_8); }
    if (manifest != null) { for (String stale : manifestEntries(manifest)) if (!generated.containsKey(Path.of(stale))&&!protectedFiles.contains(stale)) Files.deleteIfExists(outputRoot.resolve(stale)); writeManifest(manifest, generated.keySet()); }
  }
  private static Set<String> manifestEntries(Path manifest) throws IOException { if (!Files.exists(manifest)) return Collections.emptySet(); return new LinkedHashSet<>(JSON.readValue(Files.readString(manifest),new TypeReference<List<String>>() {})); }
  private static void writeManifest(Path manifest, Set<Path> paths) throws IOException { List<String> entries=new ArrayList<>(); for(Path path:paths) entries.add(path.toString().replace('\\','/')); Collections.sort(entries); Files.createDirectories(manifest.getParent()); Files.writeString(manifest,JSON.writerWithDefaultPrettyPrinter().writeValueAsString(entries)+"\n",StandardCharsets.UTF_8); }
}
