/*
 * Copyright 2026-present Coinbase Global, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 */
package com.coinbase.tools.modelgenerator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.SafeConstructor;

/** Parses the committed OpenAPI document into a stable, generator-neutral operation inventory. */
public final class SpecParser {
  private static final List<String> HTTP_METHODS =
      Arrays.asList("get", "post", "put", "patch", "delete", "head", "options");

  private SpecParser() {}

  public static SpecModels.Document load(Path path) throws IOException {
    Object value = new Yaml(new SafeConstructor(new LoaderOptions())).load(Files.newBufferedReader(path));
    Map<String, Object> root = map(value);
    List<SpecModels.Operation> operations = new ArrayList<>();
    for (Map.Entry<String, Object> pathEntry : sortedEntries(map(root.get("paths")))) {
      Map<String, Object> pathItem = map(pathEntry.getValue());
      List<Map<String, Object>> pathParameters = parameterMaps(pathItem.get("parameters"));
      for (String method : HTTP_METHODS) {
        if (!pathItem.containsKey(method)) continue;
        Map<String, Object> operation = map(pathItem.get(method));
        String operationId = string(operation.get("operationId"));
        if (operationId.isEmpty()) continue;
        List<Map<String, Object>> parameterMaps = new ArrayList<>(pathParameters);
        parameterMaps.addAll(parameterMaps(operation.get("parameters")));
        List<SpecModels.Parameter> parameters = parameterMaps.stream()
            .map(SpecParser::parameter).collect(Collectors.toList());
        Map<String, Object> response = firstSuccessResponse(operation);
        operations.add(new SpecModels.Operation(
            operationId, method.toUpperCase(), pathEntry.getKey(), strings(operation.get("tags")), parameters,
            requestSchema(operation), schema(response), successCodes(operation), string(operation.get("summary")),
            string(operation.get("x-sdk-method-name"))));
      }
    }
    operations.sort(Comparator.comparing(SpecModels.Operation::operationId));
    return new SpecModels.Document(root, operations);
  }

  private static SpecModels.Parameter parameter(Map<String, Object> parameter) {
    return new SpecModels.Parameter(string(parameter.get("name")), string(parameter.get("in")),
        Boolean.TRUE.equals(parameter.get("required")), map(parameter.get("schema")));
  }

  private static Map<String, Object> requestSchema(Map<String, Object> operation) {
    return schema(map(map(map(operation.get("requestBody")).get("content")).get("application/json")));
  }

  private static Map<String, Object> firstSuccessResponse(Map<String, Object> operation) {
    for (Map.Entry<String, Object> response : sortedEntries(map(operation.get("responses")))) {
      if (response.getKey().matches("2[0-9]{2}")) {
        Map<String, Object> json = map(map(map(response.getValue()).get("content")).get("application/json"));
        if (!schema(json).isEmpty()) return json;
      }
    }
    return Collections.emptyMap();
  }

  private static Map<String, Object> schema(Map<String, Object> contentOrMediaType) {
    return map(contentOrMediaType.get("schema"));
  }

  private static List<Integer> successCodes(Map<String, Object> operation) {
    return sortedEntries(map(operation.get("responses"))).stream()
        .map(Map.Entry::getKey).filter(k -> k.matches("2[0-9]{2}"))
        .map(Integer::valueOf).collect(Collectors.toList());
  }

  private static List<Map<String, Object>> parameterMaps(Object object) {
    if (!(object instanceof List)) return Collections.emptyList();
    List<Map<String, Object>> result = new ArrayList<>();
    for (Object value : (List<?>) object) result.add(map(value));
    return result;
  }

  private static List<String> strings(Object object) {
    if (!(object instanceof List)) return Collections.emptyList();
    return ((List<?>) object).stream().map(SpecParser::string).collect(Collectors.toList());
  }

  @SuppressWarnings("unchecked")
  static Map<String, Object> map(Object object) {
    return object instanceof Map ? new LinkedHashMap<>((Map<String, Object>) object) : Collections.emptyMap();
  }

  private static String string(Object object) { return object == null ? "" : String.valueOf(object); }

  private static List<Map.Entry<String, Object>> sortedEntries(Map<String, Object> map) {
    return map.entrySet().stream().sorted(Map.Entry.comparingByKey()).collect(Collectors.toList());
  }
}
