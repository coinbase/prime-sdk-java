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

import java.io.IOException;
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
    NamingResolver names = new NamingResolver();
    JavaTypeResolver types = new JavaTypeResolver(document, names);
    Map<Path, String> sources = new LinkedHashMap<>();
    sources.putAll(RequestPhase.render(document, bindings, types, names));
    sources.putAll(ResponsePhase.render(document, bindings, types, names));
    sources.putAll(ServicePhase.render(document, bindings, configuration(), names));
    sources.putAll(FactoryPhase.render(bindings));
    String listRequest = sources.get(Path.of("com/coinbase/prime/orders/ListThingsRequest.java"));
    assertTrue(listRequest.contains("extends PrimeListRequest"), listRequest);
    assertTrue(listRequest.contains("super(builder.cursor, builder.sortDirection, builder.limit);"), listRequest);
    // New list requests expose limit() and pagination() like the vended ones, not cursor()/sortDirection().
    assertFalse(listRequest.contains("Builder cursor(String cursor)"), listRequest);
    assertFalse(listRequest.contains("Builder sortDirection(SortDirection sortDirection)"), listRequest);
    assertTrue(listRequest.contains("Builder limit(Integer limit)"), listRequest);
    assertTrue(listRequest.contains("Builder pagination(Pagination pagination)"), listRequest);
    assertTrue(listRequest.contains("private ThingState state;"), listRequest);
    assertTrue(listRequest.contains("List state"), listRequest);
    assertFalse(listRequest.contains("request_id"), listRequest);
    assertFalse(listRequest.contains("session_id"), listRequest);
    String request = sources.get(Path.of("com/coinbase/prime/orders/CreateThingRequest.java"));
    assertTrue(request.contains("@JsonIgnore"));
    assertTrue(request.contains("private Thing[] things"));
    assertTrue(request.contains("PortfolioId is required"));
    // Optional booleans stay boxed like the vended DTOs so unset values are omitted.
    assertTrue(request.contains("private Boolean isBuyExact;"), request);
    assertTrue(request.contains("public Boolean getIsBuyExact()"), request);
    assertTrue(request.contains("public void setIsBuyExact(Boolean isBuyExact)"), request);
    assertTrue(request.contains("Buy exact flag"), request);
    String response = sources.get(Path.of("com/coinbase/prime/orders/CreateThingResponse.java"));
    assertTrue(response.contains("private OnchainThing thing"));
    assertTrue(response.contains("Created thing"), response);
    String implementation = sources.get(Path.of("com/coinbase/prime/orders/OrdersServiceImpl.java"));
    assertTrue(implementation.contains("String.format(\"/portfolios/%s/things\", request.getPortfolioId())"));
    assertTrue(implementation.contains("List.of(201, 200)"));
    assertOrdered(implementation, "listThings", "createThing");
    String service = sources.get(Path.of("com/coinbase/prime/orders/OrdersService.java"));
    assertTrue(service.contains("   * List Things.\n   *\n   * <p>Includes query filtering\n"), service);
    assertTrue(service.contains("@param request the request parameters for this operation"), service);
    assertTrue(service.contains("@return the response payload for this operation"), service);
    assertTrue(service.contains("@throws CoinbaseClientException if the request fails client-side validation"), service);
    assertTrue(sources.get(Path.of("com/coinbase/prime/factory/PrimeServiceFactory.java")).contains("createOrdersService"));
  }

  @Test
  void rendersVendedClassConventions() throws Exception {
    SpecModels.Document document = fixture();
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document);
    NamingResolver names = new NamingResolver();
    JavaTypeResolver types = new JavaTypeResolver(document, names);
    String request =
        RequestPhase.render(document, bindings, types, names)
            .get(Path.of("com/coinbase/prime/orders/CreateThingRequest.java"));

    assertTrue(request.startsWith("/*\n * Copyright 2026-present Coinbase Global, Inc.\n *\n *  Licensed"), request);
    assertTrue(request.contains(" */\n\npackage com.coinbase.prime.orders;"), request);
    // Only path parameters are marked required; body and query fields use the short form.
    assertTrue(request.contains("@JsonProperty(required = true, value = \"portfolio_id\")"), request);
    assertTrue(request.contains("@JsonProperty(\"things\")"), request);
    assertFalse(request.contains("value = \"things\""), request);
    assertTrue(request.contains("public CreateThingRequest build() throws CoinbaseClientException {"), request);
    assertTrue(request.contains("private void validate() throws CoinbaseClientException {"), request);
  }

  @Test
  void keepsMemberOrderAndDocumentationOfExistingSources() throws Exception {
    SpecModels.Document document = fixture();
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document);
    NamingResolver names = new NamingResolver();
    JavaTypeResolver types = new JavaTypeResolver(document, names);
    Path sourceRoot = Files.createTempDirectory("vended-layout");
    Path orders = Files.createDirectories(sourceRoot.resolve("com/coinbase/prime/orders"));
    Files.writeString(
        orders.resolve("CreateThingRequest.java"),
        "/** Vended request doc */\npublic class CreateThingRequest {\n"
            + "  @JsonProperty(\"is_buy_exact\")\n  private Boolean isBuyExact;\n\n"
            + "  /** Vended things doc */\n  @JsonProperty(\"things\")\n  private Thing[] things;\n\n"
            + "  @JsonProperty(required = true, value = \"portfolio_id\")\n  @JsonIgnore\n  private String portfolioId;\n\n"
            + "  public static class Builder {}\n}\n");
    Files.writeString(
        orders.resolve("OrdersService.java"),
        "public interface OrdersService {\n"
            + "  /** Vended create doc */\n  CreateThingResponse createThing(CreateThingRequest request)\n      throws X;\n\n"
            + "  ListThingsResponse listThings(ListThingsRequest request) throws X;\n}\n");
    SourceLayout layout = SourceLayout.of(sourceRoot);

    String request =
        RequestPhase.render(document, bindings, types, names, layout)
            .get(Path.of("com/coinbase/prime/orders/CreateThingRequest.java"));
    assertTrue(request.contains("/** Vended request doc */\npublic class"), request);
    assertOrdered(request, "private Boolean isBuyExact;", "private Thing[] things;");
    assertOrdered(request, "private Thing[] things;", "private String portfolioId;");
    assertTrue(request.contains("/** Vended things doc */\n  @JsonProperty(\"things\")"), request);
    // A field the vended class documented as none stays undocumented rather than taking the spec text.
    assertFalse(request.contains("Buy exact flag"), request);

    String service =
        ServicePhase.render(document, bindings, configuration(), names, layout)
            .get(Path.of("com/coinbase/prime/orders/OrdersService.java"));
    assertOrdered(service, "createThing(", "listThings(");
    assertTrue(service.contains("/** Vended create doc */"), service);
  }

  @Test
  void expectedChangesMustMatchExactly() {
    Main.verifyExpectedChanges(
        List.of("ADD a/B.java", "CHANGE a/C.java"), List.of("CHANGE a/C.java", "ADD a/B.java"));
    IllegalStateException drift =
        org.junit.jupiter.api.Assertions.assertThrows(
            IllegalStateException.class,
            () -> Main.verifyExpectedChanges(List.of("CHANGE a/C.java", "CHANGE a/D.java"), List.of("CHANGE a/C.java", "ADD a/B.java")));
    assertTrue(drift.getMessage().contains("unexpected CHANGE a/D.java"), drift.getMessage());
    assertTrue(drift.getMessage().contains("no longer changes ADD a/B.java"), drift.getMessage());
  }

  @Test
  void rendersV2TransportWithSendRequestStatusBeforeBody() throws Exception {
    SpecModels.Document document = fixture();
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document);
    NamingResolver names = new NamingResolver();
    String implementation = ServicePhase.render(document, bindings, configuration(), names)
        .get(Path.of("com/coinbase/prime/financing/FinancingServiceImpl.java"));

    assertTrue(implementation.contains(".sendRequest(HttpMethod.GET,"), implementation);
    assertTrue(implementation.contains("String.format(\"/entities/%s/cross_margin/prime\", request.getEntityId())"), implementation);
    assertOrdered(implementation, "List.of(200),", "request,");
  }

  @Test
  void rendersConfiguredStatusCodes() throws Exception {
    SpecModels.Document document = fixture();
    GeneratorConfiguration configuration =
        configuration(
            "{\"specUrl\":\"x\",\"committedSpecPath\":\"x\"}",
            "[{\"operationId\":\"PrimeRESTAPI_CreateThing\",\"statusCodes\":[202]}]");
    String implementation =
        ServicePhase.render(
                document,
                OperationBindingGenerator.deriveAll(document, configuration),
                configuration,
                new NamingResolver())
            .get(Path.of("com/coinbase/prime/orders/OrdersServiceImpl.java"));

    assertTrue(implementation.contains("List.of(202)"), implementation);
  }

  @Test
  void rejectsOneServiceNameBoundToTwoFolders() throws Exception {
    SpecModels.Document document = fixture();
    GeneratorConfiguration configuration =
        configuration(
            "{\"specUrl\":\"x\",\"committedSpecPath\":\"x\"}",
            "[{\"operationId\":\"PrimeRESTAPI_GetCrossMarginPrimeOverview\",\"serviceName\":\"OrdersService\"}]");

    IllegalArgumentException error =
        assertThrows(
            IllegalArgumentException.class,
            () -> OperationBindingGenerator.deriveAll(document, configuration));
    assertTrue(error.getMessage().contains("OrdersService"), error.getMessage());
  }

  @Test
  void routesTravelRuleToTheCanonicalTransactionsService() throws Exception {
    SpecModels.Document document = fixture();
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document, routingConfiguration());
    NamingResolver names = new NamingResolver();
    Map<Path, String> services = ServicePhase.render(document, bindings, routingConfiguration(), names);
    Map<Path, String> factory = FactoryPhase.render(bindings);

    assertTrue(services.containsKey(Path.of("com/coinbase/prime/transactions/TransactionsService.java")));
    assertFalse(services.containsKey(Path.of("com/coinbase/prime/transactions/TravelRuleService.java")));
    assertTrue(factory.get(Path.of("com/coinbase/prime/factory/PrimeServiceFactory.java"))
        .contains("createTransactionsService"));
    assertFalse(factory.get(Path.of("com/coinbase/prime/factory/PrimeServiceFactory.java"))
        .contains("TravelRuleService"));
  }

  @Test
  void resolvesPostProcessorModelNamesWithoutPreNormalizationReplacement() throws Exception {
    NamingResolver names =
        new NamingResolver(
            Map.of(
                "CreateOnchainTransactionRequestEVMParams", "EvmParams",
                "FcmFuturesSweep", "FuturesSweep"));
    JavaTypeResolver types = new JavaTypeResolver(fixture(), names);

    assertEquals(
        "EvmParams",
        types
            .resolve(
                Collections.singletonMap(
                    "$ref",
                    "#/components/schemas/CoinbasePublicRestApiCreateOnchainTransactionRequestEVMParams"))
            .name());
    assertEquals(
        "FuturesSweep",
        types
            .resolve(
                Collections.singletonMap(
                    "$ref", "#/components/schemas/FcmFuturesSweep"))
            .name());
  }

  @Test
  void rejectsCaseInsensitiveGeneratedFilenameCollisions() throws Exception {
    Path root = Files.createTempDirectory("generator-case-collision");
    Files.writeString(root.resolve("RotateApiKeyRequest.java"), "existing", StandardCharsets.UTF_8);
    Map<Path, String> generated =
        Collections.singletonMap(Path.of("RotateAPIKeyRequest.java"), "public class RotateAPIKeyRequest {}\n");

    IOException exception =
        assertThrows(
            IOException.class,
            () -> GeneratedSourceReconciler.write(root, generated, Collections.emptySet(), null));

    assertTrue(exception.getMessage().contains("case-insensitively"), exception.getMessage());
  }

  @Test
  void resolvesConfiguredSharedModelsWithoutGeneratingModelCopies() throws Exception {
    JavaTypeResolver types = new JavaTypeResolver(
        fixture(), new NamingResolver(),
        Collections.singletonMap("PaginatedResponse", "com.coinbase.prime.common.Pagination"));
    JavaTypeResolver.Type pagination = types.resolve(
        Collections.singletonMap("$ref", "#/components/schemas/coinbase.public_rest_api.PaginatedResponse"));

    assertEquals("Pagination", pagination.name());
    assertEquals(Collections.singleton("com.coinbase.prime.common.Pagination"), pagination.imports());
  }

  @Test
  void resolvesEnumsArraysMapsAndVersionedPaths() throws Exception {
    JavaTypeResolver types = new JavaTypeResolver(fixture(), new NamingResolver());
    assertEquals("ThingState", types.resolve(Collections.singletonMap("$ref", "#/components/schemas/ThingState")).name());
    JavaTypeResolver.Type subcode = types.resolve(Collections.singletonMap("$ref", "#/components/schemas/ThingProblemSubcode"));
    assertEquals("ThingProblemSubcode", subcode.name());
    assertEquals(Collections.singleton("com.coinbase.prime.model.errors.ThingProblemSubcode"), subcode.imports());
    assertEquals("Map<String, String>", types.resolve(map("type", "object", "additionalProperties", map("type", "string"))).name());
    assertEquals("v2", ServicePhase.version("/v2/things"));
    assertThrows(IllegalArgumentException.class, () -> ServicePhase.version("/v3/things"));
  }

  @Test
  void failsFastForInlineEnumWithoutANamedPublicType() throws Exception {
    JavaTypeResolver types = new JavaTypeResolver(fixture(), new NamingResolver());
    IllegalArgumentException exception =
        assertThrows(
            IllegalArgumentException.class,
            () -> types.resolve(map("type", "string", "enum", List.of("NOT_A_PUBLIC_ENUM"))));
    assertTrue(exception.getMessage().contains("Inline enum cannot be safely resolved"));
  }

  @Test
  void emitsNonObjectResponsesWithoutSummaryAndWithImports() throws Exception {
    Path spec = Files.createTempFile("non-object-response", ".yaml");
    Files.writeString(
        spec,
        "openapi: 3.0.0\n"
            + "paths:\n"
            + "  /v1/value:\n"
            + "    get:\n"
            + "      operationId: PrimeRESTAPI_GetValue\n"
            + "      tags: [Values]\n"
            + "      responses:\n"
            + "        '200':\n"
            + "          content:\n"
            + "            application/json:\n"
            + "              schema: { type: array, items: { type: string } }\n");
    SpecModels.Document document = SpecParser.load(spec);
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document);
    String response =
        ResponsePhase.render(document, bindings, new JavaTypeResolver(document, new NamingResolver()), new NamingResolver())
            .get(Path.of("com/coinbase/prime/values/GetValueResponse.java"));
    assertTrue(response.contains("import com.fasterxml.jackson.annotation.JsonProperty;"), response);
    assertTrue(response.contains("private String[] value;"), response);
  }

  @Test
  void preservesExistingClientSourceHeaderAndSignalsProtectedDrift() throws Exception {
    Path root = Files.createTempDirectory("client-source-header");
    Path relative = Path.of("com/coinbase/prime/orders/Thing.java");
    Path existing = root.resolve(relative);
    Files.createDirectories(existing.getParent());
    Files.writeString(
        existing,
        "/*\n * Copyright 2021-present Coinbase Global, Inc.\n */\nclass Thing {}\n",
        StandardCharsets.UTF_8);
    Map<Path, String> sources = Collections.singletonMap(relative, SourceTemplates.header() + "class Thing {}\n");
    Map<Path, String> headed = Main.preserveClientSourceHeaders(sources, root);
    assertTrue(headed.get(relative).contains("Copyright 2021-present"));
    assertTrue(
        GeneratedSourceReconciler.diff(root, headed, Collections.singleton(relative.toString()), null)
            .contains("SKIP " + relative));
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

  private static void assertOrdered(String source, String first, String second) {
    int firstIndex = source.indexOf(first);
    int secondIndex = source.indexOf(second);
    assertTrue(firstIndex >= 0 && secondIndex >= 0, source);
    assertTrue(firstIndex < secondIndex, source);
  }

  private static GeneratorConfiguration configuration() throws Exception {
    return configuration("{\"specUrl\":\"x\",\"committedSpecPath\":\"x\"}", "[]");
  }

  private static GeneratorConfiguration routingConfiguration() throws Exception {
    return configuration(
        "{\"specUrl\":\"x\",\"committedSpecPath\":\"x\",\"tagToFolderOverrides\":{\"Travel Rule\":\"transactions\"}}",
        "[]");
  }

  private static GeneratorConfiguration configuration(String configContent, String overridesContent)
      throws Exception {
    Path directory = Files.createTempDirectory("generator-config");
    Path config = directory.resolve("generator.json");
    Path overrides = directory.resolve("overrides.json");
    Files.writeString(config, configContent);
    Files.writeString(overrides, overridesContent);
    return GeneratorConfiguration.loadForTests(config, overrides);
  }

  private static SpecModels.Document fixture() throws Exception {
    Path spec = Files.createTempFile("prime-generator-fixture", ".yaml");
    Files.writeString(spec, String.join("\n",
        "openapi: 3.0.0", "paths:", "  /v1/portfolios/{portfolio_id}/things:", "    parameters:",
        "      - name: portfolio_id", "        in: path", "        required: true", "        schema: { type: string }",
        "    get:", "      operationId: PrimeRESTAPI_GetThings", "      tags: [Orders]", "      summary: List Things", "      description: Includes query filtering.",
        "      parameters:", "        - name: cursor", "          in: query", "          schema: { type: string }",
        "        - name: state", "          in: query", "          description: List state", "          schema: { type: string, enum: [OPEN] }",
        "        - name: request_id", "          in: header", "          schema: { type: string }",
        "        - name: session_id", "          in: cookie", "          schema: { type: string }",
        "      responses:", "        '200':", "          content:", "            application/json:", "              schema:",
        "                type: object", "                properties:", "                  items:", "                    type: array",
        "                    items: { $ref: '#/components/schemas/Web3Thing' }",
        "    post:", "      operationId: PrimeRESTAPI_CreateThing", "      tags: [Orders]", "      summary: Create Thing",
        "      requestBody:", "        content:", "          application/json:", "            schema:", "              type: object",
        "              properties:", "                things:", "                  type: array", "                  items: { $ref: '#/components/schemas/Thing' }",
        "                is_buy_exact: { type: boolean, description: Buy exact flag }",
        "      responses:", "        '200':", "          content:", "            application/json:", "              schema:",
        "                type: object", "                properties:", "                  thing: { $ref: '#/components/schemas/Web3Thing', description: Created thing }",
        "  /v2/entities/{entity_id}/cross_margin/prime:", "    get:", "      operationId: PrimeRESTAPI_GetCrossMarginPrimeOverview", "      tags: [Financing]", "      summary: Get Cross Margin Prime Overview",
        "      parameters:", "        - name: entity_id", "          in: path", "          required: true", "          schema: { type: string }",
        "      responses:", "        '200':", "          content:", "            application/json:", "              schema: { type: object }",
        "  /v1/portfolios/{portfolio_id}/transactions/{transaction_id}/travel_rule:", "    get:", "      operationId: PrimeRESTAPI_GetTransactionTravelRuleData", "      tags: [Travel Rule]", "      summary: Get Transaction Travel Rule Data",
        "      parameters:", "        - name: portfolio_id", "          in: path", "          required: true", "          schema: { type: string }",
        "        - name: transaction_id", "          in: path", "          required: true", "          schema: { type: string }",
        "      responses:", "        '200':", "          content:", "            application/json:", "              schema: { type: object }",
        "components:", "  schemas:", "    Thing: { type: object }", "    Web3Thing: { type: object }", "    PaginatedResponse: { type: object }", "    ThingState: { type: string, enum: [OPEN] }",
        "    ThingProblemSubcode: { type: string, enum: [INVALID] }", ""));
    return SpecParser.load(spec);
  }

  private static Map<String, Object> map(Object... entries) {
    Map<String, Object> value = new LinkedHashMap<>();
    for (int index = 0; index < entries.length; index += 2) value.put((String) entries[index], entries[index + 1]);
    return value;
  }
}
