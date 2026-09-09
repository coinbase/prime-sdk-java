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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/** Loads deterministic generation configuration and validates sparse operation overrides. */
public final class GeneratorConfiguration {
  private static final ObjectMapper JSON = new ObjectMapper();
  private final String specUrl;
  private final String committedSpecPath;
  private final Map<String, String> tagFolders;
  private final Map<String, String> nameReplacements;
  private final Map<String, String> sharedModelMappings;
  private final Set<String> protectedFiles;
  private final Map<String, Override> overrides;

  private GeneratorConfiguration(
      String specUrl,
      String committedSpecPath,
      Map<String, String> tagFolders,
      Map<String, String> nameReplacements,
      Map<String, String> sharedModelMappings,
      Set<String> protectedFiles,
      Map<String, Override> overrides) {
    this.specUrl = specUrl;
    this.committedSpecPath = committedSpecPath;
    this.tagFolders = Collections.unmodifiableMap(tagFolders);
    this.nameReplacements = Collections.unmodifiableMap(nameReplacements);
    this.sharedModelMappings = Collections.unmodifiableMap(sharedModelMappings);
    this.protectedFiles = Collections.unmodifiableSet(protectedFiles);
    this.overrides = Collections.unmodifiableMap(overrides);
  }

  public static GeneratorConfiguration load(GeneratorPaths paths) throws IOException {
    return load(paths.configuration(), paths.operationOverrides());
  }

  static GeneratorConfiguration loadForTests(Path configurationPath, Path overridesPath)
      throws IOException {
    return load(configurationPath, overridesPath);
  }

  private static GeneratorConfiguration load(Path configurationPath, Path overridesPath)
      throws IOException {
    JsonNode config = JSON.readTree(configurationPath.toFile());
    String specUrl = required(config, "specUrl");
    String committedSpecPath = required(config, "committedSpecPath");
    validateRelativePath(committedSpecPath, "committedSpecPath");
    Map<String, String> tagFolders = strings(config.path("tagToFolderOverrides"));
    for (Map.Entry<String, String> entry : tagFolders.entrySet()) {
      validatePackageFolder(entry.getValue(), "tagToFolderOverrides." + entry.getKey());
    }
    Map<String, String> replacements = strings(config.path("nameReplacements"));
    for (Map.Entry<String, String> entry : replacements.entrySet()) {
      validateJavaIdentifier(entry.getValue(), "nameReplacements." + entry.getKey());
    }
    Map<String, String> sharedMappings = strings(config.path("sharedModelMappings"));
    for (Map.Entry<String, String> entry : sharedMappings.entrySet()) {
      validateJavaIdentifier(entry.getKey(), "sharedModelMappings key");
      validateQualifiedType(entry.getValue(), "sharedModelMappings." + entry.getKey());
    }
    Set<String> protectedFiles = new LinkedHashSet<>();
    for (JsonNode node : config.path("protectedCompatibilityFiles")) {
      String protectedFile = node.asText();
      validateRelativePath(protectedFile, "protectedCompatibilityFiles");
      protectedFiles.add(protectedFile);
    }

    Map<String, Override> overrides = new LinkedHashMap<>();
    JsonNode overrideNodes = JSON.readTree(overridesPath.toFile());
    if (!overrideNodes.isArray()) {
      throw new IllegalArgumentException("operations-overrides.json must be an array");
    }
    for (JsonNode node : overrideNodes) {
      String operationId = required(node, "operationId");
      if (overrides.put(operationId, Override.from(node)) != null) {
        throw new IllegalArgumentException("Duplicate operation override: " + operationId);
      }
    }
    return new GeneratorConfiguration(
        specUrl,
        committedSpecPath,
        tagFolders,
        replacements,
        sharedMappings,
        protectedFiles,
        overrides);
  }

  private static String required(JsonNode node, String field) {
    String value = node.path(field).asText();
    if (value.isEmpty()) {
      throw new IllegalArgumentException("Configuration is missing " + field);
    }
    return value;
  }

  private static Map<String, String> strings(JsonNode node) {
    Map<String, String> values = new LinkedHashMap<>();
    node.fields().forEachRemaining(entry -> values.put(entry.getKey(), entry.getValue().asText()));
    return values;
  }

  static void validateJavaIdentifier(String value, String field) {
    if (value == null
        || value.isEmpty()
        || !Character.isJavaIdentifierStart(value.charAt(0))) {
      throw new IllegalArgumentException(field + " must be a Java identifier: " + value);
    }
    for (int index = 1; index < value.length(); index++) {
      if (!Character.isJavaIdentifierPart(value.charAt(index))) {
        throw new IllegalArgumentException(field + " must be a Java identifier: " + value);
      }
    }
  }

  static void validatePackageFolder(String value, String field) {
    if (value == null || value.isEmpty()) {
      throw new IllegalArgumentException(field + " must be a Java package folder");
    }
    for (String segment : value.split("\\.")) {
      validateJavaIdentifier(segment, field);
    }
  }

  private static void validateQualifiedType(String value, String field) {
    if (value == null || value.isEmpty()) {
      throw new IllegalArgumentException(field + " must be a qualified Java type");
    }
    for (String segment : value.split("\\.")) {
      validateJavaIdentifier(segment, field);
    }
  }

  private static void validateRelativePath(String value, String field) {
    Path path = Path.of(value);
    if (path.isAbsolute() || path.normalize().startsWith("..")) {
      throw new IllegalArgumentException(field + " must remain within the repository: " + value);
    }
  }

  public String specUrl() {
    return specUrl;
  }

  public String committedSpecPath() {
    return committedSpecPath;
  }

  public Map<String, String> tagFolders() {
    return tagFolders;
  }

  public Map<String, String> nameReplacements() {
    return nameReplacements;
  }

  public Map<String, String> sharedModelMappings() {
    return sharedModelMappings;
  }

  public Set<String> protectedFiles() {
    return protectedFiles;
  }

  public Map<String, Override> overrides() {
    return overrides;
  }

  public static final class Override {
    private final String sdkMethod;
    private final String serviceFolder;
    private final Boolean omitRequest;
    private final Boolean paginated;
    private final Map<String, String> parameterTypes;
    private final List<Integer> statuses;

    private Override(
        String sdkMethod,
        String serviceFolder,
        Boolean omitRequest,
        Boolean paginated,
        Map<String, String> parameterTypes,
        List<Integer> statuses) {
      this.sdkMethod = sdkMethod;
      this.serviceFolder = serviceFolder;
      this.omitRequest = omitRequest;
      this.paginated = paginated;
      this.parameterTypes = parameterTypes;
      this.statuses = statuses;
    }

    static Override from(JsonNode node) {
      String sdkMethod = node.path("sdkMethod").asText(null);
      if (sdkMethod != null) {
        validateJavaIdentifier(sdkMethod, "sdkMethod");
      }
      String serviceFolder = node.path("serviceFolder").asText(null);
      if (serviceFolder != null) {
        validatePackageFolder(serviceFolder, "serviceFolder");
      }
      Map<String, String> parameterTypes = strings(node.path("parameterTypeOverrides"));
      for (Map.Entry<String, String> entry : parameterTypes.entrySet()) {
        validateJavaIdentifier(entry.getKey(), "parameterTypeOverrides key");
        validateQualifiedType(entry.getValue(), "parameterTypeOverrides." + entry.getKey());
      }
      TreeSet<Integer> normalizedStatuses = new TreeSet<>();
      for (JsonNode status : node.path("statusCodes")) {
        int code = status.asInt();
        if (code < 200 || code >= 300) {
          throw new IllegalArgumentException("Override status must be a 2xx code");
        }
        normalizedStatuses.add(code);
      }
      return new Override(
          sdkMethod,
          serviceFolder,
          node.has("omitRequest") ? node.get("omitRequest").asBoolean() : null,
          node.has("paginated") ? node.get("paginated").asBoolean() : null,
          Collections.unmodifiableMap(new LinkedHashMap<>(parameterTypes)),
          Collections.unmodifiableList(new ArrayList<>(normalizedStatuses)));
    }

    public String sdkMethod() {
      return sdkMethod;
    }

    public String serviceFolder() {
      return serviceFolder;
    }

    public Boolean omitRequest() {
      return omitRequest;
    }

    public Boolean paginated() {
      return paginated;
    }

    public Map<String, String> parameterTypes() {
      return parameterTypes;
    }

    public List<Integer> statuses() {
      return statuses;
    }
  }
}
