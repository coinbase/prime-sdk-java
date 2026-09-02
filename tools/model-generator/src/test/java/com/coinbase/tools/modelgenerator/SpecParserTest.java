/*
 * Copyright 2026-present Coinbase Global, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.coinbase.tools.modelgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;

class SpecParserTest {
  @Test
  void parsesCompleteStableOperationInventory() throws Exception {
    Path root = Path.of(System.getProperty("user.dir")).toAbsolutePath().getParent().getParent();
    SpecModels.Document document = SpecParser.load(root.resolve("apiSpec/prime-public-spec.yaml"));

    assertEquals(103, document.operations().size());
    SpecModels.Operation createOrder = document.operations().stream()
        .filter(operation -> operation.operationId().equals("PrimeRESTAPI_CreateOrder"))
        .findFirst().orElseThrow();
    assertEquals("POST", createOrder.httpMethod());
    assertEquals("/v1/portfolios/{portfolio_id}/order", createOrder.path());
    assertEquals(List.of("Orders"), createOrder.tags());
    assertTrue(createOrder.parameters().stream()
        .anyMatch(parameter -> parameter.name().equals("portfolio_id") && parameter.location().equals("path") && parameter.required()));
    assertEquals(List.of(200), createOrder.successStatusCodes());
    assertTrue(!createOrder.requestBodySchema().isEmpty());
  }
}
