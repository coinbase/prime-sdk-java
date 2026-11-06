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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class GeneratorConfigurationTest {
  @Test
  void usesCommittedSpecPathForFetchDestinationAndNormalizesStatusOverrides() throws Exception {
    GeneratorConfiguration configuration = configuration(
        "{\"specUrl\":\"https://example.test/openapi.yaml\",\"committedSpecPath\":\"apiSpec/custom.yaml\"}",
        "[{\"operationId\":\"PrimeRESTAPI_Test\",\"statusCodes\":[204,200,204,201]}]");

    assertEquals(
        Path.of("/tmp/prime-sdk/apiSpec/custom.yaml"),
        SpecFetcher.destination(Path.of("/tmp/prime-sdk"), configuration.committedSpecPath()));
    assertEquals(List.of(200, 201, 204),
        configuration.overrides().get("PrimeRESTAPI_Test").statuses());
  }

  @Test
  void rejectsUnsafeOutputPathsAndInvalidJavaNames() throws Exception {
    assertThrows(IllegalArgumentException.class, () -> configuration(
        "{\"specUrl\":\"x\",\"committedSpecPath\":\"../outside.yaml\"}", "[]"));
    assertThrows(IllegalArgumentException.class, () -> configuration(
        "{\"specUrl\":\"x\",\"committedSpecPath\":\"apiSpec/spec.yaml\",\"tagToFolderOverrides\":{\"Tag\":\"bad-folder\"}}",
        "[]"));
    assertThrows(IllegalArgumentException.class, () -> configuration(
        "{\"specUrl\":\"x\",\"committedSpecPath\":\"apiSpec/spec.yaml\"}",
        "[{\"operationId\":\"PrimeRESTAPI_Test\",\"sdkMethod\":\"not-valid\"}]"));
  }

  private static GeneratorConfiguration configuration(String configContent, String overridesContent)
      throws Exception {
    Path directory = Files.createTempDirectory("generator-configuration");
    Path config = directory.resolve("generator.json");
    Path overrides = directory.resolve("overrides.json");
    Files.writeString(config, configContent);
    Files.writeString(overrides, overridesContent);
    return GeneratorConfiguration.loadForTests(config, overrides);
  }
}
