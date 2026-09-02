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
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class OperationBindingGeneratorTest {
  @Test
  void derivesStableBindingsForTheCommittedSpec() throws Exception {
    Path root = Path.of(System.getProperty("user.dir")).toAbsolutePath().getParent().getParent();
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(
        SpecParser.load(root.resolve("apiSpec/prime-public-spec.yaml")));

    assertEquals(103, bindings.size());
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
  }
}
