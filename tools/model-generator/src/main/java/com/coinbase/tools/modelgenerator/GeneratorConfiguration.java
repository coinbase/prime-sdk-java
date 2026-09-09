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

/** Loads deterministic generation configuration and validates sparse operation overrides. */
public final class GeneratorConfiguration {
  private static final ObjectMapper JSON = new ObjectMapper();
  private final String specUrl;
  private final String committedSpecPath;
  private final Map<String, String> tagFolders;
  private final Map<String, String> nameReplacements;
  private final Set<String> protectedFiles;
  private final Map<String, Override> overrides;

  private GeneratorConfiguration(String specUrl, String committedSpecPath, Map<String, String> tagFolders,
      Map<String, String> nameReplacements, Set<String> protectedFiles, Map<String, Override> overrides) {
    this.specUrl = specUrl;
    this.committedSpecPath = committedSpecPath;
    this.tagFolders = Collections.unmodifiableMap(tagFolders);
    this.nameReplacements = Collections.unmodifiableMap(nameReplacements);
    this.protectedFiles = Collections.unmodifiableSet(protectedFiles);
    this.overrides = Collections.unmodifiableMap(overrides);
  }

  public static GeneratorConfiguration load(GeneratorPaths paths) throws IOException {
    return load(paths.configuration(), paths.operationOverrides());
  }

  static GeneratorConfiguration loadForTests(Path configurationPath, Path overridesPath) throws IOException {
    return load(configurationPath, overridesPath);
  }

  private static GeneratorConfiguration load(Path configurationPath, Path overridesPath) throws IOException {
    JsonNode config = JSON.readTree(configurationPath.toFile());
    Map<String, String> tagFolders = strings(config.path("tagToFolderOverrides"));
    Map<String, String> replacements = strings(config.path("nameReplacements"));
    Set<String> protectedFiles = new LinkedHashSet<>();
    for (JsonNode node : config.path("protectedCompatibilityFiles")) protectedFiles.add(node.asText());
    Map<String, Override> overrides = new LinkedHashMap<>();
    JsonNode overrideNodes = JSON.readTree(overridesPath.toFile());
    if (!overrideNodes.isArray()) throw new IllegalArgumentException("operations-overrides.json must be an array");
    for (JsonNode node : overrideNodes) {
      String operationId = required(node, "operationId");
      if (overrides.put(operationId, Override.from(node)) != null) {
        throw new IllegalArgumentException("Duplicate operation override: " + operationId);
      }
    }
    String specUrl = required(config, "specUrl");
    String committedSpecPath = required(config, "committedSpecPath");
    return new GeneratorConfiguration(specUrl, committedSpecPath, tagFolders, replacements, protectedFiles, overrides);
  }

  private static String required(JsonNode node, String field) {
    String value = node.path(field).asText();
    if (value.isEmpty()) throw new IllegalArgumentException("Operation override is missing " + field);
    return value;
  }
  private static Map<String, String> strings(JsonNode node) {
    Map<String, String> values = new LinkedHashMap<>();
    node.fields().forEachRemaining(entry -> values.put(entry.getKey(), entry.getValue().asText()));
    return values;
  }
  public String specUrl() { return specUrl; }
  public String committedSpecPath() { return committedSpecPath; }
  public Map<String, String> tagFolders() { return tagFolders; }
  public Map<String, String> nameReplacements() { return nameReplacements; }
  public Set<String> protectedFiles() { return protectedFiles; }
  public Map<String, Override> overrides() { return overrides; }

  public static final class Override {
    private final String sdkMethod; private final String serviceFolder; private final Boolean omitRequest;
    private final Boolean paginated; private final Map<String, String> parameterTypes; private final List<Integer> statuses;
    private Override(String sdkMethod, String serviceFolder, Boolean omitRequest, Boolean paginated,
        Map<String, String> parameterTypes, List<Integer> statuses) {
      this.sdkMethod = sdkMethod; this.serviceFolder = serviceFolder; this.omitRequest = omitRequest;
      this.paginated = paginated; this.parameterTypes = parameterTypes; this.statuses = statuses;
    }
    static Override from(JsonNode node) {
      List<Integer> statuses = new ArrayList<>();
      for (JsonNode status : node.path("statusCodes")) {
        int code = status.asInt();
        if (code < 200 || code >= 300) throw new IllegalArgumentException("Override status must be a 2xx code");
        statuses.add(code);
      }
      return new Override(node.path("sdkMethod").asText(null), node.path("serviceFolder").asText(null),
          node.has("omitRequest") ? node.get("omitRequest").asBoolean() : null,
          node.has("paginated") ? node.get("paginated").asBoolean() : null,
          strings(node.path("parameterTypeOverrides")), statuses);
    }
    public String sdkMethod() { return sdkMethod; } public String serviceFolder() { return serviceFolder; }
    public Boolean omitRequest() { return omitRequest; } public Boolean paginated() { return paginated; }
    public Map<String, String> parameterTypes() { return parameterTypes; } public List<Integer> statuses() { return statuses; }
  }
}
