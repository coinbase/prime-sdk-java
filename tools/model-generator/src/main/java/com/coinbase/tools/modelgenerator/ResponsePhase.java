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
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Emits mutable response wrapper DTOs from the operation's deterministic successful schema. */
public final class ResponsePhase {
  private ResponsePhase() {}

  public static Map<Path, String> render(
      SpecModels.Document document,
      List<OperationBinding> bindings,
      JavaTypeResolver types,
      NamingResolver names) {
    Map<String, SpecModels.Operation> operations = new LinkedHashMap<>();
    for (SpecModels.Operation operation : document.operations()) {
      operations.put(operation.operationId(), operation);
    }
    Map<Path, String> sources = new LinkedHashMap<>();
    for (OperationBinding binding : bindings) {
      String name = binding.sdkMethod() + "Response";
      sources.put(
          Path.of("com/coinbase/prime/" + binding.serviceFolder() + "/" + name + ".java"),
          renderOne(operations.get(binding.operationId()), name, binding, types, names));
    }
    return sources;
  }

  private static String renderOne(
      SpecModels.Operation operation,
      String className,
      OperationBinding binding,
      JavaTypeResolver types,
      NamingResolver names) {
    Map<String, Object> schema = types.dereference(operation.successResponseSchema());
    Map<String, Object> properties = SpecParser.map(schema.get("properties"));
    Map<String, Field> fields = new LinkedHashMap<>();
    for (Map.Entry<String, Object> property : properties.entrySet()) {
      Map<String, Object> propertySchema = SpecParser.map(property.getValue());
      fields.put(
          property.getKey(),
          new Field(
              property.getKey(),
              names.propertyName(property.getKey()),
              type(binding, property.getKey(), propertySchema, types),
              description(propertySchema)));
    }
    if (fields.isEmpty() && !schema.isEmpty()) {
      fields.put("value", new Field("value", "value", types.resolve(operation.successResponseSchema()), ""));
    }

    Set<String> imports = new LinkedHashSet<>();
    if (!fields.isEmpty()) {
      imports.add("com.fasterxml.jackson.annotation.JsonProperty");
    }
    for (Field field : fields.values()) {
      imports.addAll(field.type.imports());
    }
    StringBuilder source =
        new StringBuilder(SourceTemplates.header())
            .append("package com.coinbase.prime.")
            .append(binding.serviceFolder())
            .append(";\n\n");
    SourceTemplates.imports(source, imports);
    SourceTemplates.javadoc(
        source, SourceTemplates.documentation(operation.summary(), operation.description()));
    source.append("public class ").append(className).append(" {\n");
    for (Field field : fields.values()) {
      SourceTemplates.javadoc(source, field.description);
      appendField(source, field);
    }
    source.append("  public ").append(className).append("() {}\n\n");
    for (Field field : fields.values()) {
      appendAccessors(source, field);
    }
    return source.append("}\n").toString();
  }

  private static JavaTypeResolver.Type type(
      OperationBinding binding,
      String wireName,
      Map<String, Object> schema,
      JavaTypeResolver types) {
    return binding.responseTypeOverrides().containsKey(wireName)
        ? types.configured(binding.responseTypeOverrides().get(wireName))
        : types.resolve(schema);
  }

  private static String description(Map<String, Object> schema) {
    Object value = schema.containsKey("description") ? schema.get("description") : schema.get("title");
    return value == null ? "" : String.valueOf(value);
  }

  private static void appendField(StringBuilder source, Field field) {
    source
        .append("  @JsonProperty(\"")
        .append(field.wireName)
        .append("\")\n  private ")
        .append(field.type.name())
        .append(" ")
        .append(field.name)
        .append(";\n\n");
  }

  private static void appendAccessors(StringBuilder source, Field field) {
    String cap = SourceTemplates.cap(field.name);
    source
        .append("  public ")
        .append(field.type.name())
        .append(" get")
        .append(cap)
        .append("() {\n    return ")
        .append(field.name)
        .append(";\n  }\n\n  public void set")
        .append(cap)
        .append("(")
        .append(field.type.name())
        .append(" ")
        .append(field.name)
        .append(") {\n    this.")
        .append(field.name)
        .append(" = ")
        .append(field.name)
        .append(";\n  }\n\n");
  }

  private static final class Field {
    private final String wireName;
    private final String name;
    private final JavaTypeResolver.Type type;
    private final String description;

    Field(String wireName, String name, JavaTypeResolver.Type type, String description) {
      this.wireName = wireName;
      this.name = name;
      this.type = type;
      this.description = description;
    }
  }
}
