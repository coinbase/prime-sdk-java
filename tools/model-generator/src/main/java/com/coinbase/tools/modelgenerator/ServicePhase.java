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

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Emits Prime synchronous service interfaces and transport-backed implementations. */
public final class ServicePhase {
  private ServicePhase() {}
  public static Map<Path, String> render(SpecModels.Document document, List<OperationBinding> bindings,
      GeneratorConfiguration configuration, NamingResolver names) {
    Map<String, SpecModels.Operation> operations = new LinkedHashMap<>();
    for (SpecModels.Operation operation : document.operations()) operations.put(operation.operationId(), operation);
    Map<String, List<OperationBinding>> groups = new LinkedHashMap<>();
    for (OperationBinding binding : bindings) groups.computeIfAbsent(binding.serviceFolder(), ignored -> new ArrayList<>()).add(binding);
    Map<Path, String> sources = new LinkedHashMap<>();
    for (List<OperationBinding> group : groups.values()) {
      group.sort(order(operations));
      String service = group.get(0).serviceName();
      String folder = group.get(0).serviceFolder();
      sources.put(Path.of("com/coinbase/prime/" + folder + "/" + service + ".java"), interfaceSource(service, folder, group, operations, names));
      sources.put(Path.of("com/coinbase/prime/" + folder + "/" + service + "Impl.java"), implementationSource(service, folder, group, operations, configuration, names));
    }
    return sources;
  }
  static Comparator<OperationBinding> order(Map<String, SpecModels.Operation> operations) {
    return Comparator.comparingInt((OperationBinding binding) -> verbOrder(operations.get(binding.operationId()).httpMethod()))
        .thenComparingInt(binding -> pathDepth(operations.get(binding.operationId()).path()))
        .thenComparing(binding -> operations.get(binding.operationId()).path()).thenComparing(OperationBinding::sdkMethod);
  }
  private static String interfaceSource(String service, String folder, List<OperationBinding> bindings,
      Map<String, SpecModels.Operation> operations, NamingResolver names) {
    StringBuilder source = new StringBuilder(SourceTemplates.header()).append("package com.coinbase.prime.").append(folder).append(";\n\n")
        .append("import com.coinbase.core.errors.CoinbaseClientException;\nimport com.coinbase.prime.errors.CoinbasePrimeException;\n\npublic interface ").append(service).append(" {\n");
    for (OperationBinding binding : bindings) {
      SpecModels.Operation operation = operations.get(binding.operationId());
      appendMethodJavadoc(source, operation, binding, true);
      source.append("  ").append(binding.sdkMethod()).append("Response ").append(names.methodName(binding.sdkMethod())).append("(");
      if (!binding.omitRequest()) source.append(binding.sdkMethod()).append("Request request");
      source.append(") throws CoinbaseClientException, CoinbasePrimeException;\n\n");
    }
    return source.append("}\n").toString();
  }
  private static String implementationSource(String service, String folder, List<OperationBinding> bindings,
      Map<String, SpecModels.Operation> operations, GeneratorConfiguration configuration, NamingResolver names) {
    boolean v2 = bindings.stream().anyMatch(binding -> version(operations.get(binding.operationId()).path()).equals("v2"));
    StringBuilder source = new StringBuilder(SourceTemplates.header()).append("package com.coinbase.prime.").append(folder).append(";\n\n")
        .append("import com.coinbase.core.common.HttpMethod;\nimport com.coinbase.core.service.CoinbaseServiceImpl;\nimport com.coinbase.prime.client.CoinbasePrimeClient;\nimport com.coinbase.prime.errors.CoinbasePrimeException;\nimport com.fasterxml.jackson.core.type.TypeReference;\nimport java.util.List;\n");
    if (v2) source.append("import com.coinbase.prime.utils.Constants;\n");
    source.append("\npublic class ").append(service).append("Impl extends CoinbaseServiceImpl implements ").append(service).append(" {\n");
    if (v2) source.append("  private final CoinbasePrimeClient primeClient;\n\n");
    source.append("  public ").append(service).append("Impl(CoinbasePrimeClient client) {\n    super(client);\n");
    if (v2) source.append("    this.primeClient = client;\n");
    source.append("  }\n");
    for (OperationBinding binding : bindings) {
      SpecModels.Operation operation = operations.get(binding.operationId());
      String method = names.methodName(binding.sdkMethod()); String version = version(operation.path());
      source.append("\n");
      appendMethodJavadoc(source, operation, binding, false);
      source.append("  @Override\n  public ").append(binding.sdkMethod()).append("Response ").append(method).append("(");
      if (!binding.omitRequest()) source.append(binding.sdkMethod()).append("Request request");
      source.append(") throws CoinbasePrimeException {\n    return ");
      if (version.equals("v2")) {
        source.append("this.primeClient.withBaseUrl(Constants.versionedBaseUrl(this.primeClient.getBaseUrl(), \"v2\")).sendRequest(");
        source.append("HttpMethod.").append(operation.httpMethod()).append(",\n        ")
            .append(pathExpression(operation.path(), binding, names)).append(",\n        ")
            .append(statuses(operation, configuration)).append(",\n        ")
            .append(binding.omitRequest() ? "null" : "request").append(",\n        new TypeReference<")
            .append(binding.sdkMethod()).append("Response>() {});\n  }\n");
      } else {
        source.append("this.request(");
        source.append("HttpMethod.").append(operation.httpMethod()).append(",\n        ")
            .append(pathExpression(operation.path(), binding, names)).append(",\n        ")
            .append(binding.omitRequest() ? "null" : "request").append(",\n        ")
            .append(statuses(operation, configuration)).append(",\n        new TypeReference<")
            .append(binding.sdkMethod()).append("Response>() {});\n  }\n");
      }
    }
    return source.append("}\n").toString();
  }
  private static void appendMethodJavadoc(
      StringBuilder source,
      SpecModels.Operation operation,
      OperationBinding binding,
      boolean interfaceMethod) {
    String documentation = SourceTemplates.documentation(operation.summary(), operation.description());
    source.append("  /**\n");
    if (!documentation.isEmpty()) {
      source.append("   * ").append(documentation.replace("*/", "* /").replace("\n", " ")).append("\n");
    }
    if (!binding.omitRequest()) {
      source.append("   * @param request request parameters and body for this operation\n");
    }
    source.append("   * @return the decoded ").append(binding.sdkMethod()).append(" response\n");
    if (interfaceMethod) {
      source.append("   * @throws CoinbaseClientException if the request cannot be sent\n");
    }
    source.append("   * @throws CoinbasePrimeException if the Prime API rejects the request\n");
    source.append("   */\n");
  }

  private static String pathExpression(String rawPath, OperationBinding binding, NamingResolver names) {
    String path = rawPath.replaceFirst("^/v[12]", "");
    if (!path.matches(".*\\{[^}]+}.*")) return "\"" + path + "\"";
    List<String> args = new ArrayList<>();
    java.util.regex.Matcher matcher = java.util.regex.Pattern.compile("\\{([^}]+)}").matcher(path);
    StringBuffer format = new StringBuffer();
    while (matcher.find()) { matcher.appendReplacement(format, "%s"); args.add("request.get" + SourceTemplates.cap(names.propertyName(matcher.group(1))) + "()"); }
    matcher.appendTail(format);
    return "String.format(\"" + format + "\", " + String.join(", ", args) + ")";
  }
  static List<Integer> statusCodes(SpecModels.Operation operation, GeneratorConfiguration configuration) {
    List<Integer> defaults = defaultStatusCodes(operation);
    GeneratorConfiguration.Override override = configuration.overrides().get(operation.operationId());
    if (override != null && !override.statuses().isEmpty()) {
      if (override.statuses().equals(defaults)) {
        System.err.println("WARN redundant status override: " + operation.operationId());
      }
      return override.statuses();
    }
    return defaults;
  }

  private static List<Integer> defaultStatusCodes(SpecModels.Operation operation) {
    List<Integer> values = new ArrayList<>(operation.successStatusCodes());
    if (values.size() == 1
        && values.get(0) == 200
        && (operation.operationId().matches(".*_(Create|Claim|Submit).*")
            || operation.operationId().endsWith("PreviewUnstake"))) {
      values.clear();
      values.add(201);
      values.add(200);
    }
    values.sort(
        (left, right) ->
            left == 201 ? -1 : right == 201 ? 1 : Integer.compare(left, right));
    return values;
  }
  private static String statuses(SpecModels.Operation operation, GeneratorConfiguration config) { StringBuilder result=new StringBuilder("List.of("); for (Integer code:statusCodes(operation, config)) { if (result.length()>8) result.append(", "); result.append(code); } return result.append(")").toString(); }
  static String version(String path) { if (!path.startsWith("/v")) return "v1"; String value=path.substring(1, path.indexOf('/', 1)); if (!value.equals("v1") && !value.equals("v2")) throw new IllegalArgumentException("Unsupported Prime API path version in " + path); return value; }
  private static int verbOrder(String verb) { switch (verb) { case "GET": return 0; case "POST": return 1; case "PUT": return 2; case "PATCH": return 3; case "DELETE": return 4; default: return 5; } }
  private static int pathDepth(String path) { return (int) java.util.Arrays.stream(path.split("/")).filter(value -> !value.isEmpty()).count(); }
}
