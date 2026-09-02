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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

/** Derives deterministic Java SDK names from parsed OpenAPI operations. */
public final class OperationBindingGenerator {
  private static final String OPERATION_ID_PREFIX = "PrimeRESTAPI_";
  private static final Map<String, String> METHOD_RENAMES = new HashMap<>();

  static {
    METHOD_RENAMES.put("CancelFuturesSweep", "CancelEntityFuturesSweep");
    METHOD_RENAMES.put("CreateOnchainAddressGroup", "CreateOnchainAddressBookEntry");
    METHOD_RENAMES.put("CreatePortfolioAddressBookEntry", "CreateAddressBookEntry");
    METHOD_RENAMES.put("CreateQuoteRequest", "CreateQuote");
    METHOD_RENAMES.put("GetAllocationsByClientNettingId", "ListAllocationsByNettingId");
    METHOD_RENAMES.put("GetEntityAssets", "ListAssets");
    METHOD_RENAMES.put("GetEntityPaymentMethodDetails", "GetPaymentMethodDetails");
    METHOD_RENAMES.put("GetEntityUsers", "ListEntityUsers");
    METHOD_RENAMES.put("GetFuturesSweeps", "ListEntityFuturesSweeps");
    METHOD_RENAMES.put("GetLocateAvailabilities", "GetEntityLocateAvailabilities");
    METHOD_RENAMES.put("GetMarginSummaries", "ListMarginCallSummaries");
    METHOD_RENAMES.put("GetPortfolioAddressBook", "ListAddressBook");
    METHOD_RENAMES.put("GetPortfolioInterestAccruals", "ListInterestAccrualsForPortfolio");
    METHOD_RENAMES.put("GetPostTradeCredit", "GetPortfolioCreditInformation");
    METHOD_RENAMES.put("GetTFTieredPricingFees", "GetTradeFinanceTieredPricingFees");
    METHOD_RENAMES.put("ListTFObligations", "ListTradeFinanceObligations");
    METHOD_RENAMES.put("OrderPreview", "GetOrderPreview");
    METHOD_RENAMES.put("ScheduleFuturesSweep", "ScheduleEntityFuturesSweep");
    METHOD_RENAMES.put("UpdateOnchainAddressGroup", "UpdateOnchainAddressBookEntry");
  }

  private OperationBindingGenerator() {}

  public static List<OperationBinding> deriveAll(SpecModels.Document document) {
    List<OperationBinding> bindings = new ArrayList<>();
    for (SpecModels.Operation operation : document.operations()) bindings.add(derive(operation));
    bindings.sort(Comparator.comparing(OperationBinding::operationId));
    OperationBindingValidator.validate(document, bindings);
    return Collections.unmodifiableList(bindings);
  }

  static OperationBinding derive(SpecModels.Operation operation) {
    String tag = operation.tags().isEmpty() ? "Misc" : operation.tags().get(0);
    String folder = "Travel Rule".equals(tag) ? "transactions" : tag.replaceAll("[^A-Za-z0-9]", "").replace(" ", "").toLowerCase(Locale.ROOT);
    String serviceName = pascal(tag) + "Service";
    String raw = operation.sdkMethodName().isEmpty() ? operation.operationId().replaceFirst("^" + OPERATION_ID_PREFIX, "") : operation.sdkMethodName();
    String method = METHOD_RENAMES.getOrDefault(raw, raw);
    if (operation.httpMethod().equals("GET") && method.startsWith("Get") && operation.summary().startsWith("List ")) method = "List" + method.substring(3);
    boolean omitRequest = operation.parameters().isEmpty() && operation.requestBodySchema().isEmpty();
    boolean paginated = operation.parameters().stream().anyMatch(p -> p.name().equals("cursor") || p.name().equals("sort_direction"));
    return new OperationBinding(operation.operationId(), folder, serviceName, method, omitRequest, paginated, new LinkedHashMap<>());
  }

  private static String pascal(String value) {
    StringBuilder result = new StringBuilder();
    for (String part : Arrays.asList(value.replaceAll("[^A-Za-z0-9]+", " ").split(" +"))) {
      if (!part.isEmpty()) result.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
    }
    return result.toString();
  }
}
