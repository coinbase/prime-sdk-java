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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Emits mutable, SDK-style per-operation request DTOs. */
public final class RequestPhase {
  private RequestPhase() {}

  public static Map<Path, String> render(SpecModels.Document document, List<OperationBinding> bindings,
      JavaTypeResolver types, NamingResolver names) {
    Map<String, SpecModels.Operation> operations = byId(document);
    Map<Path, String> sources = new LinkedHashMap<>();
    for (OperationBinding binding : bindings) {
      if (binding.omitRequest()) continue;
      SpecModels.Operation operation = operations.get(binding.operationId());
      String className = binding.sdkMethod() + "Request";
      sources.put(path(binding, className), renderOne(operation, binding, className, types, names));
    }
    return sources;
  }

  private static String renderOne(SpecModels.Operation operation, OperationBinding binding, String className,
      JavaTypeResolver types, NamingResolver names) {
    List<Field> fields = fields(operation, binding, types, names);
    Set<String> imports = new LinkedHashSet<>();
    imports.add("com.fasterxml.jackson.annotation.JsonProperty");
    boolean hasPath = fields.stream().anyMatch(field -> field.path);
    if (hasPath) imports.add("com.fasterxml.jackson.annotation.JsonIgnore");
    boolean validates = fields.stream().anyMatch(field -> field.path && field.required && field.type.name().equals("String"));
    if (validates) { imports.add("com.coinbase.core.errors.CoinbaseClientException"); imports.add("static com.coinbase.core.utils.Utils.isNullOrEmpty"); }
    if (binding.paginated()) {
      imports.add("com.coinbase.prime.common.Pagination");
      imports.add("com.coinbase.prime.common.PrimeListRequest");
      imports.add("com.coinbase.prime.model.enums.SortDirection");
    }
    for (Field field : fields) imports.addAll(field.type.imports());
    StringBuilder source = new StringBuilder(SourceTemplates.header())
        .append("package com.coinbase.prime.").append(binding.serviceFolder()).append(";\n\n");
    SourceTemplates.imports(source, imports);
    SourceTemplates.javadoc(source, operation.summary());
    source.append("public class ").append(className);
    if (binding.paginated()) source.append(" extends PrimeListRequest");
    source.append(" {\n");
    for (Field field : fields) {
      SourceTemplates.javadoc(source, field.description);
      source.append("  @JsonProperty(");
      if (field.required) source.append("required = true, ");
      source.append("value = \"").append(field.wireName).append("\")\n");
      if (field.path) source.append("  @JsonIgnore\n");
      source.append("  private ").append(field.type.name()).append(" ").append(field.name).append(";\n\n");
    }
    source.append("  public ").append(className).append("() {}\n\n");
    source.append("  public ").append(className).append("(Builder builder) {\n");
    if (binding.paginated()) {
      source.append("    super(builder.cursor, builder.sortDirection, builder.limit);\n");
    }
    for (Field field : fields) source.append("    this.").append(field.name).append(" = builder.").append(field.name).append(";\n");
    source.append("  }\n\n");
    for (Field field : fields) {
      String cap = SourceTemplates.cap(field.name);
      source.append("  public ").append(field.type.name()).append(" get").append(cap).append("() {\n    return ").append(field.name).append(";\n  }\n\n")
          .append("  public void set").append(cap).append("(").append(field.type.name()).append(" ").append(field.name).append(") {\n    this.").append(field.name).append(" = ").append(field.name).append(";\n  }\n\n");
    }
    source.append("  public static class Builder {\n");
    if (binding.paginated()) {
      source.append("    private String cursor;\n")
          .append("    private SortDirection sortDirection;\n")
          .append("    private Integer limit;\n");
    }
    for (Field field : fields) source.append("    private ").append(field.type.name()).append(" ").append(field.name).append(";\n");
    source.append("\n    public Builder() {}\n\n");
    if (binding.paginated()) {
      source.append("    public Builder cursor(String cursor) {\n      this.cursor = cursor;\n      return this;\n    }\n\n")
          .append("    public Builder sortDirection(SortDirection sortDirection) {\n      this.sortDirection = sortDirection;\n      return this;\n    }\n\n")
          .append("    public Builder limit(Integer limit) {\n      this.limit = limit;\n      return this;\n    }\n\n")
          .append("    public Builder pagination(Pagination pagination) {\n      this.cursor = pagination.getNextCursor();\n      this.sortDirection = pagination.getSortDirection();\n      return this;\n    }\n\n");
    }
    for (Field field : fields) source.append("    public Builder ").append(field.name).append("(").append(field.type.name()).append(" ").append(field.name).append(") {\n      this.").append(field.name).append(" = ").append(field.name).append(";\n      return this;\n    }\n\n");
    source.append("    public ").append(className).append(" build()");
    if (validates) source.append(" throws CoinbaseClientException");
    source.append(" {\n");
    if (validates) source.append("      validate();\n");
    source.append("      return new ").append(className).append("(this);\n    }\n");
    if (validates) {
      source.append("\n    private void validate() throws CoinbaseClientException {\n");
      for (Field field : fields) if (field.path && field.required && field.type.name().equals("String"))
        source.append("      if (isNullOrEmpty(this.").append(field.name).append(")) {\n        throw new CoinbaseClientException(\"").append(SourceTemplates.cap(field.name)).append(" is required\");\n      }\n");
      source.append("    }\n");
    }
    return source.append("  }\n}\n").toString();
  }

  static List<Field> fields(SpecModels.Operation operation, OperationBinding binding, JavaTypeResolver types, NamingResolver names) {
    Map<String, Field> fields = new LinkedHashMap<>();
    for (SpecModels.Parameter parameter : operation.parameters()) {
      if (binding.paginated() && (parameter.name().equals("cursor") || parameter.name().equals("limit") || parameter.name().equals("sort_direction"))) continue;
      JavaTypeResolver.Type type = binding.parameterTypeOverrides().containsKey(parameter.name())
          ? new JavaTypeResolver.Type(binding.parameterTypeOverrides().get(parameter.name()), Collections.emptySet()) : types.resolve(parameter.schema());
      fields.put(parameter.name(), new Field(parameter.name(), names.propertyName(parameter.name()), type,
          parameter.required(), "path".equals(parameter.location()), ""));
    }
    Map<String, Object> body = types.dereference(operation.requestBodySchema());
    Map<String, Object> required = new LinkedHashMap<>();
    Object requiredValue = body.get("required");
    if (requiredValue instanceof List) for (Object value : (List<?>) requiredValue) required.put(String.valueOf(value), Boolean.TRUE);
    for (Map.Entry<String, Object> property : SpecParser.map(body.get("properties")).entrySet()) {
      if (!fields.containsKey(property.getKey())) fields.put(property.getKey(), new Field(property.getKey(),
          names.propertyName(property.getKey()), types.resolve(SpecParser.map(property.getValue())), required.containsKey(property.getKey()), false, ""));
    }
    return new ArrayList<>(fields.values());
  }
  private static Path path(OperationBinding binding, String className) { return Path.of("com/coinbase/prime/" + binding.serviceFolder() + "/" + className + ".java"); }
  private static Map<String, SpecModels.Operation> byId(SpecModels.Document document) { Map<String, SpecModels.Operation> result = new LinkedHashMap<>(); for (SpecModels.Operation operation : document.operations()) result.put(operation.operationId(), operation); return result; }
  static final class Field { final String wireName, name, description; final JavaTypeResolver.Type type; final boolean required, path; Field(String wireName, String name, JavaTypeResolver.Type type, boolean required, boolean path, String description) { this.wireName=wireName; this.name=name; this.type=type; this.required=required; this.path=path; this.description=description; } }
}
