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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/** Compares staged generated sources before the generator is allowed to replace SDK files. */
public final class GeneratedSourceReconciler {
  private GeneratedSourceReconciler() {}

  public static List<String> diff(Path outputRoot, Map<Path, String> generated) throws IOException {
    List<String> changes = new ArrayList<>();
    for (Map.Entry<Path, String> entry : new TreeMap<>(generated).entrySet()) {
      Path target = outputRoot.resolve(entry.getKey());
      String existing = Files.exists(target) ? Files.readString(target) : null;
      if (!entry.getValue().equals(existing)) changes.add((existing == null ? "ADD " : "CHANGE ") + entry.getKey());
    }
    return Collections.unmodifiableList(changes);
  }

  /** Writes only paths whose exact normalized source differs. Intended for the future write mode. */
  public static void write(Path outputRoot, Map<Path, String> generated) throws IOException {
    for (Map.Entry<Path, String> entry : new TreeMap<>(generated).entrySet()) {
      Path target = outputRoot.resolve(entry.getKey());
      if (Files.exists(target) && entry.getValue().equals(Files.readString(target))) continue;
      Files.createDirectories(target.getParent());
      Files.writeString(target, entry.getValue(), StandardCharsets.UTF_8);
    }
  }
}
