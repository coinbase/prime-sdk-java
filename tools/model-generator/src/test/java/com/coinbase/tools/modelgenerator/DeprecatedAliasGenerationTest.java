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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

/**
 * Verifies that declarative {@code deprecatedAliases} configuration produces canonical Java SDK
 * names plus source-compatible deprecated request/response subclasses and forwarding service
 * methods, entirely driven from {@code operations-overrides.json}.
 */
class DeprecatedAliasGenerationTest {

  @Test
  void deriveAllAssignsDeprecatedAliasesFromOverrides() throws Exception {
    GeneratorConfiguration configuration =
        configuration(
            "[{\"operationId\":\"PrimeRESTAPI_CreateWidget\",\"sdkMethod\":\"CreateWidget\","
                + "\"convenienceConstructorParameters\":[\"display_name\"],"
                + "\"deprecatedAliases\":[{\"sdkMethod\":\"CreateLegacyWidget\",\"message\":\"Use createWidget instead.\"}]},"
                + "{\"operationId\":\"PrimeRESTAPI_ListWidgets\",\"sdkMethod\":\"ListWidgets\","
                + "\"deprecatedAliases\":[{\"sdkMethod\":\"ListLegacyWidgets\",\"message\":\"Use listWidgets instead.\"}]}]");
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(fixture(), configuration);

    OperationBinding createWidget = binding(bindings, "PrimeRESTAPI_CreateWidget");
    assertEquals("CreateWidget", createWidget.sdkMethod());
    assertEquals(1, createWidget.deprecatedAliases().size());
    assertEquals("CreateLegacyWidget", createWidget.deprecatedAliases().get(0).sdkMethod());
    assertEquals("Use createWidget instead.", createWidget.deprecatedAliases().get(0).message());

    OperationBinding listWidgets = binding(bindings, "PrimeRESTAPI_ListWidgets");
    assertEquals("ListLegacyWidgets", listWidgets.deprecatedAliases().get(0).sdkMethod());
    assertTrue(listWidgets.paginated());
  }

  @Test
  void rejectsAliasThatMatchesItsOwnCanonicalNameAtConfigParseTime() throws Exception {
    assertThrows(
        IllegalArgumentException.class,
        () ->
            configuration(
                "[{\"operationId\":\"PrimeRESTAPI_CreateWidget\",\"sdkMethod\":\"CreateWidget\","
                    + "\"deprecatedAliases\":[{\"sdkMethod\":\"CreateWidget\",\"message\":\"noop\"}]}]"));
  }

  @Test
  void rejectsAliasThatMatchesTheDefaultDerivedCanonicalName() throws Exception {
    GeneratorConfiguration configuration =
        configuration(
            "[{\"operationId\":\"PrimeRESTAPI_CreateWidget\","
                + "\"deprecatedAliases\":[{\"sdkMethod\":\"CreateWidget\",\"message\":\"noop\"}]}]");
    SpecModels.Document document = fixture();
    assertThrows(
        IllegalArgumentException.class,
        () -> OperationBindingGenerator.deriveAll(document, configuration));
  }

  @Test
  void rejectsAliasThatCollidesWithAnotherServiceMethod() throws Exception {
    GeneratorConfiguration configuration =
        configuration(
            "[{\"operationId\":\"PrimeRESTAPI_CreateWidget\",\"sdkMethod\":\"CreateWidget\","
                + "\"deprecatedAliases\":[{\"sdkMethod\":\"ListWidgets\",\"message\":\"noop\"}]}]");
    SpecModels.Document document = fixture();
    assertThrows(
        IllegalArgumentException.class,
        () -> OperationBindingGenerator.deriveAll(document, configuration));
  }

  @Test
  void requestPhaseEmitsSourceCompatibleDeprecatedSubclassWithCovariantBuilder() throws Exception {
    SpecModels.Document document = fixture();
    GeneratorConfiguration configuration =
        configuration(
            "[{\"operationId\":\"PrimeRESTAPI_CreateWidget\",\"sdkMethod\":\"CreateWidget\","
                + "\"convenienceConstructorParameters\":[\"display_name\"],"
                + "\"deprecatedAliases\":[{\"sdkMethod\":\"CreateLegacyWidget\",\"message\":\"Use createWidget instead.\"}]}]");
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document, configuration);
    NamingResolver names = new NamingResolver(Collections.emptyMap());
    JavaTypeResolver types = new JavaTypeResolver(document, names);

    Map<Path, String> sources = RequestPhase.render(document, bindings, types, names);
    String alias = sources.get(Path.of("com/coinbase/prime/orders/CreateLegacyWidgetRequest.java"));
    assertTrue(alias != null, sources.keySet().toString());
    assertTrue(alias.contains("@deprecated Use createWidget instead."), alias);
    assertTrue(alias.contains("@Deprecated"), alias);
    assertTrue(alias.contains("public class CreateLegacyWidgetRequest extends CreateWidgetRequest {"), alias);
    assertTrue(
        alias.contains("public CreateLegacyWidgetRequest(CreateWidgetRequest.Builder builder) {\n    super(builder);\n  }"),
        alias);
    assertTrue(alias.contains("public static class Builder extends CreateWidgetRequest.Builder {"), alias);
    assertTrue(
        alias.contains(
            "@Override\n    public Builder displayName(String displayName) {\n      super.displayName(displayName);\n      return this;\n    }"),
        alias);
    assertTrue(alias.contains("public CreateLegacyWidgetRequest build() throws CoinbaseClientException {\n      super.validate();\n      return new CreateLegacyWidgetRequest(this);\n    }"), alias);
    assertTrue(
        alias.contains("public CreateLegacyWidgetRequest(String displayName) {\n    super();\n    this.setDisplayName(displayName);\n  }"),
        alias);

    String canonical = sources.get(Path.of("com/coinbase/prime/orders/CreateWidgetRequest.java"));
    assertTrue(canonical != null && !canonical.contains("@Deprecated"), canonical);
  }

  @Test
  void requestPhaseCovariantBuilderCoversPaginationMethods() throws Exception {
    SpecModels.Document document = fixture();
    GeneratorConfiguration configuration =
        configuration(
            "[{\"operationId\":\"PrimeRESTAPI_ListWidgets\",\"sdkMethod\":\"ListWidgets\","
                + "\"deprecatedAliases\":[{\"sdkMethod\":\"ListLegacyWidgets\",\"message\":\"Use listWidgets instead.\"}]}]");
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document, configuration);
    NamingResolver names = new NamingResolver(Collections.emptyMap());
    JavaTypeResolver types = new JavaTypeResolver(document, names);

    String alias =
        RequestPhase.render(document, bindings, types, names)
            .get(Path.of("com/coinbase/prime/orders/ListLegacyWidgetsRequest.java"));
    assertTrue(alias != null, alias);
    assertTrue(alias.contains("extends ListWidgetsRequest"), alias);
    assertTrue(
        alias.contains("@Override\n    public Builder cursor(String cursor) {\n      super.cursor(cursor);\n      return this;\n    }"),
        alias);
    assertTrue(
        alias.contains(
            "@Override\n    public Builder pagination(Pagination pagination) {\n      super.pagination(pagination);\n      return this;\n    }"),
        alias);
    assertTrue(
        alias.contains("@Override\n    public Builder status(String status) {\n      super.status(status);\n      return this;\n    }"),
        alias);
  }

  @Test
  void responsePhaseEmitsDeprecatedSubclassWithNoDuplicateFields() throws Exception {
    SpecModels.Document document = fixture();
    GeneratorConfiguration configuration =
        configuration(
            "[{\"operationId\":\"PrimeRESTAPI_CreateWidget\",\"sdkMethod\":\"CreateWidget\","
                + "\"deprecatedAliases\":[{\"sdkMethod\":\"CreateLegacyWidget\",\"message\":\"Use createWidget instead.\"}]}]");
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document, configuration);
    NamingResolver names = new NamingResolver(Collections.emptyMap());
    JavaTypeResolver types = new JavaTypeResolver(document, names);

    String alias =
        ResponsePhase.render(document, bindings, types, names)
            .get(Path.of("com/coinbase/prime/orders/CreateLegacyWidgetResponse.java"));
    assertTrue(alias != null, alias);
    assertTrue(alias.contains("@Deprecated"), alias);
    assertTrue(alias.contains("public class CreateLegacyWidgetResponse extends CreateWidgetResponse {"), alias);
    assertTrue(alias.contains("public CreateLegacyWidgetResponse() {}"), alias);
    assertTrue(!alias.contains("@JsonProperty"), alias);
  }

  @Test
  void servicePhaseEmitsDeprecatedForwardingInterfaceAndImplementationMethods() throws Exception {
    SpecModels.Document document = fixture();
    GeneratorConfiguration configuration =
        configuration(
            "[{\"operationId\":\"PrimeRESTAPI_CreateWidget\",\"sdkMethod\":\"CreateWidget\","
                + "\"deprecatedAliases\":[{\"sdkMethod\":\"CreateLegacyWidget\",\"message\":\"Use createWidget instead.\"}]}]");
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document, configuration);
    NamingResolver names = new NamingResolver(Collections.emptyMap());

    Map<Path, String> services = ServicePhase.render(document, bindings, configuration, names);
    String iface = services.get(Path.of("com/coinbase/prime/orders/OrdersService.java"));
    assertTrue(iface.contains("@deprecated Use createWidget instead."), iface);
    assertTrue(
        iface.contains(
            "@Deprecated\n  CreateLegacyWidgetResponse createLegacyWidget(CreateLegacyWidgetRequest request) throws CoinbaseClientException, CoinbasePrimeException;"),
        iface);
    assertTrue(iface.contains("CreateWidgetResponse createWidget(CreateWidgetRequest request)"), iface);

    String impl = services.get(Path.of("com/coinbase/prime/orders/OrdersServiceImpl.java"));
    assertTrue(
        impl.contains(
            "@Deprecated\n  @Override\n  public CreateLegacyWidgetResponse createLegacyWidget(CreateLegacyWidgetRequest request) throws CoinbasePrimeException {"),
        impl);
    assertTrue(impl.contains("new TypeReference<CreateLegacyWidgetResponse>() {}"), impl);
  }

  private static GeneratorConfiguration configuration(String overridesContent) throws Exception {
    Path directory = Files.createTempDirectory("deprecated-alias-config");
    Path config = directory.resolve("generator.json");
    Path overrides = directory.resolve("overrides.json");
    Files.writeString(config, "{\"specUrl\":\"x\",\"committedSpecPath\":\"x\"}");
    Files.writeString(overrides, overridesContent);
    return GeneratorConfiguration.loadForTests(config, overrides);
  }

  private static OperationBinding binding(List<OperationBinding> bindings, String operationId) {
    return bindings.stream()
        .filter(candidate -> candidate.operationId().equals(operationId))
        .findFirst()
        .orElseThrow();
  }

  private static SpecModels.Document fixture() throws Exception {
    Path spec = Files.createTempFile("deprecated-alias-fixture", ".yaml");
    Files.writeString(
        spec,
        String.join(
            "\n",
            "openapi: 3.0.0",
            "paths:",
            "  /v1/portfolios/{portfolio_id}/widgets:",
            "    parameters:",
            "      - name: portfolio_id",
            "        in: path",
            "        required: true",
            "        schema: { type: string }",
            "    get:",
            "      operationId: PrimeRESTAPI_ListWidgets",
            "      tags: [Orders]",
            "      summary: List Widgets",
            "      parameters:",
            "        - name: cursor",
            "          in: query",
            "          schema: { type: string }",
            "        - name: status",
            "          in: query",
            "          schema: { type: string }",
            "      responses:",
            "        '200':",
            "          content:",
            "            application/json:",
            "              schema:",
            "                type: object",
            "                properties:",
            "                  widgets:",
            "                    type: array",
            "                    items: { $ref: '#/components/schemas/Widget' }",
            "    post:",
            "      operationId: PrimeRESTAPI_CreateWidget",
            "      tags: [Orders]",
            "      summary: Create Widget",
            "      requestBody:",
            "        content:",
            "          application/json:",
            "            schema:",
            "              type: object",
            "              required: [display_name]",
            "              properties:",
            "                display_name: { type: string, description: Display name }",
            "      responses:",
            "        '200':",
            "          content:",
            "            application/json:",
            "              schema:",
            "                type: object",
            "                properties:",
            "                  widget: { $ref: '#/components/schemas/Widget' }",
            "components:",
            "  schemas:",
            "    Widget: { type: object, properties: { id: { type: string } } }",
            ""));
    return SpecParser.load(spec);
  }
}
