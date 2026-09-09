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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class ClientSurfaceGenerationTest {
  @Test
  void rendersRequestsResponsesServicesAndFactoryFromFixture() throws Exception {
    SpecModels.Document document = fixture();
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document);
    NamingResolver names = new NamingResolver(Collections.singletonMap("Web3", "Onchain"));
    JavaTypeResolver types = new JavaTypeResolver(document, names);
    Map<Path, String> sources = new LinkedHashMap<>();
    sources.putAll(RequestPhase.render(document, bindings, types, names));
    sources.putAll(ResponsePhase.render(document, bindings, types, names));
    sources.putAll(ServicePhase.render(document, bindings, configuration(), names));
    sources.putAll(FactoryPhase.render(bindings));
    String listRequest = sources.get(Path.of("com/coinbase/prime/orders/ListThingsRequest.java"));
    assertTrue(listRequest.contains("extends PrimeListRequest"), listRequest);
    String request = sources.get(Path.of("com/coinbase/prime/orders/CreateThingRequest.java"));
    assertTrue(request.contains("@JsonIgnore"));
    assertTrue(request.contains("private List<Thing> things"));
    assertTrue(request.contains("PortfolioId is required"));
    String response = sources.get(Path.of("com/coinbase/prime/orders/CreateThingResponse.java"));
    assertTrue(response.contains("private OnchainThing thing"));
    String implementation = sources.get(Path.of("com/coinbase/prime/orders/OrdersServiceImpl.java"));
    assertTrue(implementation.contains("String.format(\"/portfolios/%s/things\", request.getPortfolioId())"));
    assertTrue(implementation.contains("List.of(201, 200)"));
    assertTrue(implementation.indexOf("listThings") < implementation.indexOf("createThing"));
    assertTrue(sources.get(Path.of("com/coinbase/prime/factory/PrimeServiceFactory.java")).contains("createOrdersService"));
  }

  @Test
  void resolvesEnumsArraysMapsAndVersionedPaths() throws Exception {
    JavaTypeResolver types = new JavaTypeResolver(fixture(), new NamingResolver(Collections.emptyMap()));
    assertEquals("ThingState", types.resolve(Collections.singletonMap("$ref", "#/components/schemas/ThingState")).name());
    JavaTypeResolver.Type subcode = types.resolve(Collections.singletonMap("$ref", "#/components/schemas/ThingProblemSubcode"));
    assertEquals("ThingProblemSubcode", subcode.name());
    assertEquals(Collections.singleton("com.coinbase.prime.model.errors.ThingProblemSubcode"), subcode.imports());
    assertEquals("Map<String>", types.resolve(map("type", "object", "additionalProperties", map("type", "string"))).name());
    assertEquals("v2", ServicePhase.version("/v2/things"));
    assertThrows(IllegalArgumentException.class, () -> ServicePhase.version("/v3/things"));
  }

  @Test
  void reconcilesOnlyManifestOwnedFiles() throws Exception {
    Path root = Files.createTempDirectory("generator-reconcile");
    Path manifest = root.resolve("manifest.json");
    Files.writeString(root.resolve("owned.java"), "old", StandardCharsets.UTF_8);
    Files.writeString(root.resolve("hand-written.java"), "keep", StandardCharsets.UTF_8);
    Files.writeString(manifest, "[\n  \"owned.java\",\n  \"gone.java\"\n]\n", StandardCharsets.UTF_8);
    Map<Path, String> generated = Collections.singletonMap(Path.of("owned.java"), "new");
    GeneratedSourceReconciler.write(root, generated, Collections.emptySet(), manifest);
    assertEquals("new", Files.readString(root.resolve("owned.java")));
    assertEquals("keep", Files.readString(root.resolve("hand-written.java")));
    assertFalse(Files.exists(root.resolve("gone.java")));
  }

  private static GeneratorConfiguration configuration() throws Exception {
    Path directory = Files.createTempDirectory("generator-config");
    Path config = directory.resolve("generator.json");
    Path overrides = directory.resolve("overrides.json");
    Files.writeString(config, "{\"specUrl\":\"x\",\"committedSpecPath\":\"x\"}");
    Files.writeString(overrides, "[]");
    return GeneratorConfiguration.loadForTests(config, overrides);
  }

  private static SpecModels.Document fixture() throws Exception {
    Path spec = Files.createTempFile("prime-generator-fixture", ".yaml");
    Files.writeString(spec, String.join("\n",
        "openapi: 3.0.0", "paths:", "  /v1/portfolios/{portfolio_id}/things:", "    parameters:",
        "      - name: portfolio_id", "        in: path", "        required: true", "        schema: { type: string }",
        "    get:", "      operationId: PrimeRESTAPI_GetThings", "      tags: [Orders]", "      summary: List Things",
        "      parameters:", "        - name: cursor", "          in: query", "          schema: { type: string }",
        "      responses:", "        '200':", "          content:", "            application/json:", "              schema:",
        "                type: object", "                properties:", "                  items:", "                    type: array",
        "                    items: { $ref: '#/components/schemas/Web3Thing' }",
        "    post:", "      operationId: PrimeRESTAPI_CreateThing", "      tags: [Orders]", "      summary: Create Thing",
        "      requestBody:", "        content:", "          application/json:", "            schema:", "              type: object",
        "              properties:", "                things:", "                  type: array", "                  items: { $ref: '#/components/schemas/Thing' }",
        "      responses:", "        '200':", "          content:", "            application/json:", "              schema:",
        "                type: object", "                properties:", "                  thing: { $ref: '#/components/schemas/Web3Thing' }",
        "components:", "  schemas:", "    Thing: { type: object }", "    Web3Thing: { type: object }", "    ThingState: { type: string, enum: [OPEN] }",
        "    ThingProblemSubcode: { type: string, enum: [INVALID] }", ""));
    return SpecParser.load(spec);
  }

  private static Map<String, Object> map(Object... entries) {
    Map<String, Object> value = new LinkedHashMap<>();
    for (int index = 0; index < entries.length; index += 2) value.put((String) entries[index], entries[index + 1]);
    return value;
  }
}
