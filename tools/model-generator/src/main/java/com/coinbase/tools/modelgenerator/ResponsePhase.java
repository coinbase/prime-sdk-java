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
  public static Map<Path, String> render(SpecModels.Document document, List<OperationBinding> bindings,
      JavaTypeResolver types, NamingResolver names) {
    Map<String, SpecModels.Operation> operations = new LinkedHashMap<>();
    for (SpecModels.Operation operation : document.operations()) operations.put(operation.operationId(), operation);
    Map<Path, String> sources = new LinkedHashMap<>();
    for (OperationBinding binding : bindings) {
      String name = binding.sdkMethod() + "Response";
      sources.put(Path.of("com/coinbase/prime/" + binding.serviceFolder() + "/" + name + ".java"), renderOne(operations.get(binding.operationId()), name, binding, types, names));
    }
    return sources;
  }
  private static String renderOne(SpecModels.Operation operation, String className, OperationBinding binding,
      JavaTypeResolver types, NamingResolver names) {
    Map<String, Object> schema = types.dereference(operation.successResponseSchema());
    Map<String, Object> properties = SpecParser.map(schema.get("properties"));
    Set<String> imports = new LinkedHashSet<>(); imports.add("com.fasterxml.jackson.annotation.JsonProperty");
    StringBuilder source = new StringBuilder(SourceTemplates.header()).append("package com.coinbase.prime.").append(binding.serviceFolder()).append(";\n\n");
    for (Object property : properties.values()) imports.addAll(types.resolve(SpecParser.map(property)).imports());
    if (!properties.isEmpty()) SourceTemplates.imports(source, imports);
    SourceTemplates.javadoc(source, operation.summary());
    source.append("public class ").append(className).append(" {\n");
    if (properties.isEmpty() && !schema.isEmpty()) {
      JavaTypeResolver.Type type = types.resolve(operation.successResponseSchema());
      imports.addAll(type.imports());
      // Non-object JSON payloads are represented without losing the decoded value.
      source.insert(source.indexOf("/**"), SourceTemplates.importBlock(imports));
      appendField(source, "value", names.propertyName("value"), type);
    } else for (Map.Entry<String, Object> property : properties.entrySet()) {
      appendField(source, property.getKey(), names.propertyName(property.getKey()), types.resolve(SpecParser.map(property.getValue())));
    }
    source.append("  public ").append(className).append("() {}\n\n");
    for (Map.Entry<String, Object> property : properties.entrySet()) appendAccessors(source, names.propertyName(property.getKey()), types.resolve(SpecParser.map(property.getValue())));
    if (properties.isEmpty() && !schema.isEmpty()) appendAccessors(source, "value", types.resolve(operation.successResponseSchema()));
    return source.append("}\n").toString();
  }
  private static void appendField(StringBuilder source, String wire, String name, JavaTypeResolver.Type type) { source.append("  @JsonProperty(\"").append(wire).append("\")\n  private ").append(type.name()).append(" ").append(name).append(";\n\n"); }
  private static void appendAccessors(StringBuilder source, String name, JavaTypeResolver.Type type) { String cap=SourceTemplates.cap(name); source.append("  public ").append(type.name()).append(" get").append(cap).append("() {\n    return ").append(name).append(";\n  }\n\n  public void set").append(cap).append("(").append(type.name()).append(" ").append(name).append(") {\n    this.").append(name).append(" = ").append(name).append(";\n  }\n\n"); }
}
