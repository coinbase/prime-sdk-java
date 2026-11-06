/*
 * Copyright 2026-present Coinbase Global, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.coinbase.tools.modelgenerator;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.Test;

/**
 * Locks the vended Java surface for every known operation.
 *
 * <p>Spec updates may add operations freely, but a spec or derivation change that renames, moves, or
 * drops an existing SDK method fails here. Add an override to {@code operations-overrides.json} to
 * keep the vended name, or update {@code operation-inventory.json} deliberately.
 */
class OperationInventoryTest {
  private static final ObjectMapper JSON = new ObjectMapper();

  @Test
  void existingOperationsKeepTheirVendedJavaSurface() throws Exception {
    Path root = Path.of(System.getProperty("user.dir")).toAbsolutePath().getParent().getParent();
    SpecModels.Document document = SpecParser.load(root.resolve("apiSpec/prime-public-spec.yaml"));
    GeneratorConfiguration configuration = GeneratorConfiguration.load(GeneratorPaths.forRoot(root));
    Map<String, String> actual = inventory(OperationBindingGenerator.deriveAll(document, configuration));
    @SuppressWarnings("unchecked")
    Map<String, String> expected =
        JSON.readValue(
            root.resolve("tools/model-generator/src/test/resources/operation-inventory.json").toFile(),
            TreeMap.class);

    List<String> failures = new ArrayList<>();
    for (Map.Entry<String, String> entry : expected.entrySet()) {
      String surface = actual.get(entry.getKey());
      if (!entry.getValue().equals(surface)) {
        failures.add(entry.getKey() + ": expected " + entry.getValue() + " but was " + surface);
      }
    }
    assertTrue(failures.isEmpty(), "Vended Java surface changed:\n" + String.join("\n", failures));
  }

  static Map<String, String> inventory(List<OperationBinding> bindings) {
    Map<String, String> inventory = new TreeMap<>();
    for (OperationBinding binding : bindings) {
      inventory.put(
          binding.operationId(),
          binding.serviceFolder() + "/" + binding.serviceName() + "#" + binding.sdkMethod());
    }
    return inventory;
  }
}
