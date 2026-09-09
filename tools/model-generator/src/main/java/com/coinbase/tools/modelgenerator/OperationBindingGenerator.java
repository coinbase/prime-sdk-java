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
import java.util.Set;
import java.util.HashSet;
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
    return deriveAll(document, null);
  }

  public static List<OperationBinding> deriveAll(
      SpecModels.Document document, GeneratorConfiguration configuration) {
    List<OperationBinding> bindings = new ArrayList<>();
    Set<String> knownOperations = new HashSet<>();
    for (SpecModels.Operation operation : document.operations()) {
      knownOperations.add(operation.operationId());
      OperationBinding derived = derive(operation);
      if (configuration != null && !operation.tags().isEmpty()) {
        String configuredFolder = configuration.tagFolders().get(operation.tags().get(0));
        if (configuredFolder != null && !configuredFolder.equals(derived.serviceFolder())) {
          derived = new OperationBinding(derived.operationId(), configuredFolder,
              canonicalServiceForFolder(document, configuredFolder), derived.sdkMethod(), derived.omitRequest(),
              derived.paginated(), derived.parameterTypeOverrides());
        }
      }
      GeneratorConfiguration.Override override = configuration == null ? null
          : configuration.overrides().get(operation.operationId());
      if (override != null) {
        Map<String, Object> body =
            SpecParser.map(dereference(document, operation.requestBodySchema()).get("properties"));
        validateCompatibilityFields(operation, body, override.parameterTypes().keySet(), "parameter override");
        validateCompatibilityFields(operation, body, override.propertyNames().keySet(), "property name override");
        validateCompatibilityFields(
            operation,
            body,
            new HashSet<>(override.convenienceConstructorParameters()),
            "convenience constructor parameter");
        derived = applyOverride(derived, override);
      }
      bindings.add(derived);
    }
    if (configuration != null) {
      for (String operationId : configuration.overrides().keySet()) {
        if (!knownOperations.contains(operationId)) {
          throw new IllegalArgumentException("Unknown operation override: " + operationId);
        }
      }
    }
    bindings.sort(Comparator.comparing(OperationBinding::operationId));
    OperationBindingValidator.validate(document, bindings);
    return Collections.unmodifiableList(bindings);
  }

  private static OperationBinding applyOverride(
      OperationBinding binding, GeneratorConfiguration.Override override) {
    String folder = override.serviceFolder() == null ? binding.serviceFolder() : override.serviceFolder();
    String service =
        override.serviceName() == null
            ? (folder.equals(binding.serviceFolder()) ? binding.serviceName() : folderToService(folder))
            : override.serviceName();
    String method = override.sdkMethod() == null ? binding.sdkMethod() : override.sdkMethod();
    boolean omit = override.omitRequest() == null ? binding.omitRequest() : override.omitRequest();
    boolean paginated = override.paginated() == null ? binding.paginated() : override.paginated();
    if (folder.equals(binding.serviceFolder())
        && service.equals(binding.serviceName())
        && method.equals(binding.sdkMethod())
        && omit == binding.omitRequest()
        && paginated == binding.paginated()
        && override.parameterTypes().isEmpty()
        && override.propertyNames().isEmpty()
        && override.responseTypes().isEmpty()
        && override.convenienceConstructorParameters().isEmpty()
        && override.statuses().isEmpty()) {
      System.err.println("WARN redundant operation override: " + binding.operationId());
    }
    return new OperationBinding(
        binding.operationId(),
        folder,
        service,
        method,
        omit,
        paginated,
        override.parameterTypes().isEmpty()
            ? binding.parameterTypeOverrides()
            : override.parameterTypes(),
        override.propertyNames().isEmpty() ? binding.propertyNameOverrides() : override.propertyNames(),
        override.responseTypes().isEmpty() ? binding.responseTypeOverrides() : override.responseTypes(),
        override.convenienceConstructorParameters().isEmpty()
            ? binding.convenienceConstructorParameters()
            : override.convenienceConstructorParameters());
  }

  private static Map<String, Object> dereference(
      SpecModels.Document document, Map<String, Object> schema) {
    String ref = String.valueOf(schema.getOrDefault("$ref", ""));
    if (ref.isEmpty()) {
      return schema;
    }
    Map<String, Object> schemas =
        SpecParser.map(SpecParser.map(document.root().get("components")).get("schemas"));
    return SpecParser.map(schemas.get(ref.substring(ref.lastIndexOf('/') + 1)));
  }

  private static void validateCompatibilityFields(
      SpecModels.Operation operation,
      Map<String, Object> body,
      Set<String> fields,
      String label) {
    for (String field : fields) {
      boolean declared = operation.parameters().stream().anyMatch(value -> value.name().equals(field));
      if (!declared && !body.containsKey(field)) {
        throw new IllegalArgumentException(
            "Unknown " + label + " " + field + " for " + operation.operationId());
      }
    }
  }

  private static String folderToService(String folder) {
    StringBuilder name = new StringBuilder();
    for (String part : folder.split("[^A-Za-z0-9]+")) {
      if (!part.isEmpty()) name.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
    }
    return name.append("Service").toString();
  }

  static OperationBinding derive(SpecModels.Operation operation) {
    String tag = operation.tags().isEmpty() ? "Misc" : operation.tags().get(0);
    String folder = "Travel Rule".equals(tag) ? "transactions" : tag.replaceAll("[^A-Za-z0-9]", "").replace(" ", "").toLowerCase(Locale.ROOT);
    String serviceName = "Travel Rule".equals(tag) ? folderToService(folder) : pascal(tag) + "Service";
    String raw =
        operation.sdkMethodName().isEmpty()
            ? operation.operationId()
            : operation.sdkMethodName();
    raw = stripOperationPrefix(raw);
    String method = METHOD_RENAMES.getOrDefault(raw, raw);
    if (operation.httpMethod().equals("GET") && method.startsWith("Get") && operation.summary().startsWith("List ")) method = "List" + method.substring(3);
    boolean omitRequest = operation.parameters().isEmpty() && operation.requestBodySchema().isEmpty();
    boolean paginated = operation.parameters().stream().anyMatch(p -> p.name().equals("cursor") || p.name().equals("sort_direction"));
    return new OperationBinding(operation.operationId(), folder, serviceName, method, omitRequest, paginated, new LinkedHashMap<>());
  }

  private static String stripOperationPrefix(String operationName) {
    return operationName.replaceFirst("^[A-Za-z][A-Za-z0-9]*_", "");
  }

  private static String canonicalServiceForFolder(SpecModels.Document document, String folder) {
    for (SpecModels.Operation candidate : document.operations()) {
      OperationBinding binding = derive(candidate);
      if (folder.equals(binding.serviceFolder()) && !"Travel Rule".equals(firstTag(candidate))) {
        return binding.serviceName();
      }
    }
    return folderToService(folder);
  }

  private static String firstTag(SpecModels.Operation operation) {
    return operation.tags().isEmpty() ? "Misc" : operation.tags().get(0);
  }

  private static String pascal(String value) {
    StringBuilder result = new StringBuilder();
    for (String part : Arrays.asList(value.replaceAll("[^A-Za-z0-9]+", " ").split(" +"))) {
      if (!part.isEmpty()) {
        result.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
      }
    }
    return result.toString();
  }
}
