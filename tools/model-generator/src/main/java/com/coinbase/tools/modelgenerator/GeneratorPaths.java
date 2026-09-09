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

import java.nio.file.Path;
import java.nio.file.Paths;

/** Canonical generator input and output locations relative to the SDK repository. */
public final class GeneratorPaths {
  private final Path root;

  private GeneratorPaths(Path root) { this.root = root.toAbsolutePath().normalize(); }

  public static GeneratorPaths fromWorkingDirectory() {
    Path current = Paths.get(System.getProperty("user.dir")).toAbsolutePath();
    while (current != null) {
      if (current.resolve("pom.xml").toFile().exists()
          && current.resolve("src/main/java/com/coinbase/prime").toFile().exists()) {
        return new GeneratorPaths(current);
      }
      current = current.getParent();
    }
    throw new IllegalStateException("Could not locate the Coinbase Prime SDK repository root");
  }

  public Path root() { return root; }
  public Path configuration() { return root.resolve("tools/model-generator/config/generator-config.json"); }
  public Path operationOverrides() { return root.resolve("tools/model-generator/config/operations-overrides.json"); }
  public Path sourceRoot() { return root.resolve("src/main/java"); }
  public Path modelRoot() { return sourceRoot().resolve("com/coinbase/prime/model"); }
  public Path enumRoot() { return modelRoot().resolve("enums"); }
  public Path rawRoot() { return root.resolve("generated"); }
  public Path manifest() { return root.resolve("tools/model-generator/generated-files.json"); }
}
