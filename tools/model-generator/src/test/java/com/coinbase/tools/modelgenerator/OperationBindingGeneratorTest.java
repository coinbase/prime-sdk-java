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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationBindingGeneratorTest {
  @Test
  void derivesStableBindingsForTheCommittedSpec() throws Exception {
    Path root = Path.of(System.getProperty("user.dir")).toAbsolutePath().getParent().getParent();
    SpecModels.Document document = SpecParser.load(root.resolve("apiSpec/prime-public-spec.yaml"));
    GeneratorConfiguration configuration = GeneratorConfiguration.load(GeneratorPaths.forRoot(root));
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document, configuration);

    assertEquals(document.operations().size(), bindings.size());
    OperationBinding createOrder = bindings.stream()
        .filter(binding -> binding.operationId().equals("PrimeRESTAPI_CreateOrder"))
        .findFirst().orElseThrow();
    assertEquals("orders", createOrder.serviceFolder());
    assertEquals("OrdersService", createOrder.serviceName());
    assertEquals("CreateOrder", createOrder.sdkMethod());
    assertTrue(!createOrder.omitRequest());

    OperationBinding travelRule = bindings.stream()
        .filter(binding -> binding.operationId().equals("PrimeRESTAPI_SubmitDepositTravelRuleData"))
        .findFirst().orElseThrow();
    assertEquals("transactions", travelRule.serviceFolder());
    assertEquals("TransactionsService", travelRule.serviceName());
  }

  @Test
  void preservesConfiguredCompatibilityNamesAndRequestShapes() throws Exception {
    Path root = Path.of(System.getProperty("user.dir")).toAbsolutePath().getParent().getParent();
    GeneratorPaths paths = GeneratorPaths.forRoot(root);
    SpecModels.Document document = SpecParser.load(root.resolve("apiSpec/prime-public-spec.yaml"));
    GeneratorConfiguration configuration = GeneratorConfiguration.load(paths);
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document, configuration);

    assertEquals("GetEntityFcmBalance", binding(bindings, "PrimeRESTAPI_GetFcmBalance").sdkMethod());
    assertEquals("ListPortfolioOrders", binding(bindings, "PrimeRESTAPI_GetOrders").sdkMethod());
    assertEquals("GetOrderByOrderId", binding(bindings, "PrimeRESTAPI_GetOrder").sdkMethod());
    assertEquals("ClaimRewards", binding(bindings, "PrimeRESTAPI_StakingClaimRewards").sdkMethod());
    assertEquals("GetCrossMarginLiquidation", binding(bindings, "PrimeRESTAPI_GetXMLiquidation").sdkMethod());
    assertEquals("GetDerivativePositions", binding(bindings, "PrimeRESTAPI_GetDerivativePositions").sdkMethod());
    assertEquals("ListOnchainWalletBalances", binding(bindings, "PrimeRESTAPI_ListWeb3WalletBalances").sdkMethod());
    assertEquals("apikey", binding(bindings, "PrimeRESTAPI_RotateAPIKey").serviceFolder());
    assertEquals("ApiKeyService", binding(bindings, "PrimeRESTAPI_RotateAPIKey").serviceName());

    NamingResolver names =
        new NamingResolver(configuration.nameReplacements(), configuration.modelTypeMappings());
    JavaTypeResolver types =
        new JavaTypeResolver(document, names, configuration.sharedModelMappings());
    String orders =
        RequestPhase.render(document, bindings, types, names)
            .get(Path.of("com/coinbase/prime/orders/ListPortfolioOrdersRequest.java"));
    assertTrue(orders.contains("private OrderStatus[] orderStatuses;"), orders);
    assertTrue(orders.contains("private String[] productIds;"), orders);
    String wallet =
        RequestPhase.render(document, bindings, types, names)
            .get(Path.of("com/coinbase/prime/wallets/CreateWalletRequest.java"));
    assertTrue(wallet.contains("private WalletType type;"), wallet);
    assertTrue(wallet.contains("Builder type(WalletType type)"), wallet);
    assertEquals(
        "PrimeXMControlStatus",
        names.typeName("CoinbasePublicRestApiXMControlStatus"));
    assertEquals(
        "PrimeXMMarginLevel", names.typeName("CoinbasePublicRestApiXMMarginLevel"));
  }

  @Test
  void resolvesEveryCommittedInlineRequestEnumToAPublicNamedEnum() throws Exception {
    Path root = Path.of(System.getProperty("user.dir")).toAbsolutePath().getParent().getParent();
    GeneratorConfiguration configuration = GeneratorConfiguration.load(GeneratorPaths.forRoot(root));
    SpecModels.Document document = SpecParser.load(root.resolve("apiSpec/prime-public-spec.yaml"));
    JavaTypeResolver types =
        new JavaTypeResolver(
            document,
            new NamingResolver(configuration.nameReplacements(), configuration.modelTypeMappings()),
            configuration.sharedModelMappings());
    int inlineEnumCount = 0;
    for (SpecModels.Operation operation : document.operations()) {
      for (SpecModels.Parameter parameter : operation.parameters()) {
        if (parameter.schema().containsKey("enum")) {
          assertFalse(types.resolve(parameter.schema()).name().equals("String"));
          inlineEnumCount++;
        }
      }
      Map<String, Object> body = types.dereference(operation.requestBodySchema());
      for (Object property : SpecParser.map(body.get("properties")).values()) {
        Map<String, Object> propertySchema = SpecParser.map(property);
        if (propertySchema.containsKey("enum")) {
          assertFalse(types.resolve(propertySchema).name().equals("String"));
          inlineEnumCount++;
        }
      }
    }
    assertTrue(inlineEnumCount > 0);
  }

  private static OperationBinding binding(List<OperationBinding> bindings, String operationId) {
    return bindings.stream()
        .filter(binding -> binding.operationId().equals(operationId))
        .findFirst()
        .orElseThrow();
  }
}
