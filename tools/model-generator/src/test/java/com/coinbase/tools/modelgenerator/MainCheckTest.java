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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class MainCheckTest {
  @Test
  void checkRendersModelsInIsolationAndReportsDriftWithoutWritingTheSdkTree() throws Exception {
    Path root = Files.createTempDirectory("isolated-model-check");
    Path sourceRoot = root.resolve("src/main/java");
    Path spec = root.resolve("apiSpec/openapi.yaml");
    Files.createDirectories(sourceRoot.resolve("com/coinbase/prime"));
    Files.createDirectories(spec.getParent());
    Files.writeString(root.resolve("pom.xml"), "<project/>\n");
    Files.writeString(spec, String.join("\n",
        "openapi: 3.0.0",
        "info: { title: test, version: 1.0.0 }",
        "paths: {}",
        "components:",
        "  schemas:",
        "    Thing:",
        "      type: object",
        "      properties:",
        "        id: { type: string }",
        ""));

    List<String> changes = Main.checkModelsInIsolation(GeneratorPaths.forRoot(root), spec);

    assertTrue(changes.stream().anyMatch(change -> change.endsWith("com/coinbase/prime/model/Thing.java")));
    assertFalse(Files.exists(sourceRoot.resolve("com/coinbase/prime/model/Thing.java")));
  }
}
