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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

/**
 * Locks the cross-SDK naming decisions recorded in {@code config/naming-parity.json} against the
 * generator's actual operation bindings for the committed OpenAPI spec.
 *
 * <p>Java converges on canonical names that match the prevailing name across the .NET, TypeScript,
 * Python, and Go Prime SDKs (or an explicit Java-language convention), while every previously
 * public Java name is preserved as a declarative, generated deprecated alias. This fixture is the
 * regression net for that decision: it does not clone or otherwise depend on the other SDK
 * repositories, it only asserts that the Java generator continues to emit the names recorded here
 * from {@code config/operations-overrides.json}.
 */
class NamingParityTest {
  private static final ObjectMapper JSON = new ObjectMapper();

  @Test
  void generatorBindingsMatchTheRecordedCrossSdkNamingParityFixture() throws Exception {
    Path root = Path.of(System.getProperty("user.dir")).toAbsolutePath().getParent().getParent();
    SpecModels.Document document = SpecParser.load(root.resolve("apiSpec/prime-public-spec.yaml"));
    GeneratorConfiguration configuration = GeneratorConfiguration.load(GeneratorPaths.forRoot(root));
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document, configuration);
    NamingResolver names =
        new NamingResolver(configuration.nameReplacements(), configuration.modelTypeMappings());

    JsonNode fixture = JSON.readTree(root.resolve("tools/model-generator/config/naming-parity.json").toFile());
    assertTrue(fixture.isArray() && fixture.size() > 0, "naming-parity.json must be a non-empty array");

    List<String> failures = new ArrayList<>();
    for (JsonNode entry : fixture) {
      String operationId = entry.path("operationId").asText();
      String expectedCanonical = entry.path("canonicalJavaMethod").asText();
      String expectedFolder = entry.path("serviceFolder").asText();
      List<String> expectedAliases = new ArrayList<>();
      for (JsonNode alias : entry.path("legacyJavaAliases")) expectedAliases.add(alias.asText());

      OperationBinding binding =
          bindings.stream()
              .filter(candidate -> candidate.operationId().equals(operationId))
              .findFirst()
              .orElse(null);
      if (binding == null) {
        failures.add(operationId + ": no binding found (has the spec operation been removed or renamed?)");
        continue;
      }

      String actualCanonical = names.methodName(binding.sdkMethod());
      if (!expectedCanonical.equals(actualCanonical)) {
        failures.add(
            operationId
                + ": expected canonical method '"
                + expectedCanonical
                + "' but generator produced '"
                + actualCanonical
                + "'");
      }
      if (!expectedFolder.equals(binding.serviceFolder())) {
        failures.add(
            operationId
                + ": expected service folder '"
                + expectedFolder
                + "' but generator produced '"
                + binding.serviceFolder()
                + "'");
      }
      List<String> actualAliases =
          binding.deprecatedAliases().stream().map(alias -> names.methodName(alias.sdkMethod())).collect(Collectors.toList());
      if (!expectedAliases.equals(actualAliases)) {
        failures.add(
            operationId
                + ": expected deprecated aliases "
                + expectedAliases
                + " but generator produced "
                + actualAliases);
      }
    }

    if (!failures.isEmpty()) {
      fail("Naming parity fixture drift:\n  " + String.join("\n  ", failures));
    }
  }

  @Test
  void everyFixtureEntryWithAliasesHasGeneratedRequestResponseSubclassesAndForwardingMethods()
      throws Exception {
    Path root = Path.of(System.getProperty("user.dir")).toAbsolutePath().getParent().getParent();
    SpecModels.Document document = SpecParser.load(root.resolve("apiSpec/prime-public-spec.yaml"));
    GeneratorConfiguration configuration = GeneratorConfiguration.load(GeneratorPaths.forRoot(root));
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document, configuration);
    NamingResolver names =
        new NamingResolver(configuration.nameReplacements(), configuration.modelTypeMappings());
    JavaTypeResolver types =
        new JavaTypeResolver(document, names, configuration.sharedModelMappings());

    var requestSources = RequestPhase.render(document, bindings, types, names);
    var responseSources = ResponsePhase.render(document, bindings, types, names);
    var serviceSources = ServicePhase.render(document, bindings, configuration, names);

    JsonNode fixture = JSON.readTree(root.resolve("tools/model-generator/config/naming-parity.json").toFile());
    for (JsonNode entry : fixture) {
      if (!"requestResponseSubclass".equals(entry.path("aliasBehavior").asText())) {
        continue;
      }
      String operationId = entry.path("operationId").asText();
      String folder = entry.path("serviceFolder").asText();
      OperationBinding binding =
          bindings.stream().filter(candidate -> candidate.operationId().equals(operationId)).findFirst().orElseThrow();
      assertEquals(1, binding.deprecatedAliases().size(), operationId);
      DeprecatedAlias alias = binding.deprecatedAliases().get(0);

      String aliasRequestPath = "com/coinbase/prime/" + folder + "/" + alias.sdkMethod() + "Request.java";
      String aliasRequest = requestSources.get(Path.of(aliasRequestPath));
      assertTrue(aliasRequest != null, "missing generated alias request: " + aliasRequestPath);
      assertTrue(aliasRequest.contains("@Deprecated"), aliasRequest);
      assertTrue(
          aliasRequest.contains("extends " + binding.sdkMethod() + "Request"),
          "alias request must subclass the canonical request: " + aliasRequestPath);

      String aliasResponsePath = "com/coinbase/prime/" + folder + "/" + alias.sdkMethod() + "Response.java";
      String aliasResponse = responseSources.get(Path.of(aliasResponsePath));
      assertTrue(aliasResponse != null, "missing generated alias response: " + aliasResponsePath);
      assertTrue(aliasResponse.contains("@Deprecated"), aliasResponse);
      assertTrue(
          aliasResponse.contains("extends " + binding.sdkMethod() + "Response"),
          "alias response must subclass the canonical response: " + aliasResponsePath);

      String serviceInterface = serviceSources.get(Path.of("com/coinbase/prime/" + folder + "/" + binding.serviceName() + ".java"));
      assertTrue(serviceInterface != null, "missing generated service interface for folder: " + folder);
      String aliasMethodName = names.methodName(alias.sdkMethod());
      assertTrue(
          serviceInterface.contains(alias.sdkMethod() + "Response " + aliasMethodName + "("),
          "service interface must declare the deprecated forwarding method: " + aliasMethodName);
      assertTrue(serviceInterface.contains("@deprecated " + alias.message()), serviceInterface);

      String serviceImpl =
          serviceSources.get(Path.of("com/coinbase/prime/" + folder + "/" + binding.serviceName() + "Impl.java"));
      assertTrue(serviceImpl != null, "missing generated service implementation for folder: " + folder);
      assertTrue(
          serviceImpl.contains("public " + alias.sdkMethod() + "Response " + aliasMethodName + "("),
          "service implementation must implement the deprecated forwarding method: " + aliasMethodName);
    }
  }
}
