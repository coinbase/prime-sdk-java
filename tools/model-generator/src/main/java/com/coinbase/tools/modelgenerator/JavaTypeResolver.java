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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/** Resolves OpenAPI schemas to the post-processed Java model and enum type names. */
public final class JavaTypeResolver {
  private final Map<String, Object> schemas;
  private final NamingResolver names;
  private final Map<String, String> sharedModelMappings;

  public JavaTypeResolver(SpecModels.Document document, NamingResolver names) {
    this(document, names, Collections.emptyMap());
  }

  @SuppressWarnings("unchecked")
  public JavaTypeResolver(
      SpecModels.Document document, NamingResolver names, Map<String, String> sharedModelMappings) {
    this.schemas =
        SpecParser.map(
            SpecParser.map(SpecParser.map(document.root().get("components")).get("schemas")));
    this.names = names;
    this.sharedModelMappings = sharedModelMappings;
  }

  public Type resolve(Map<String, Object> schema) {
    if (schema == null || schema.isEmpty()) return new Type("Object", Collections.emptySet());
    String ref = string(schema.get("$ref"));
    if (!ref.isEmpty()) {
      String raw = ref.substring(ref.lastIndexOf('/') + 1);
      Map<String, Object> target = schemas.get(raw) instanceof Map ? SpecParser.map(schemas.get(raw)) : Collections.emptyMap();
      String typeName = names.typeName(schemaTypeName(raw));
      String sharedType = sharedModelMappings.get(raw);
      if (sharedType == null) {
        sharedType = sharedModelMappings.get(typeName);
      }
      if (sharedType != null) {
        return external(sharedType);
      }
      String packageName =
          target.containsKey("enum")
              ? GeneratedEnumKind.packageFor(typeName) + "."
              : "com.coinbase.prime.model.";
      return external(packageName + typeName);
    }
    String type = string(schema.get("type"));
    if ("array".equals(type)) return generic("List", resolve(SpecParser.map(schema.get("items"))), "java.util.List");
    if ("object".equals(type) && schema.containsKey("additionalProperties")) {
      return generic("Map", resolve(SpecParser.map(schema.get("additionalProperties"))), "java.util.Map");
    }
    if (schema.containsKey("enum")) return new Type("String", Collections.emptySet());
    switch (type) {
      case "integer": return new Type("Integer", Collections.emptySet());
      case "number": return new Type("Double", Collections.emptySet());
      case "boolean": return new Type("Boolean", Collections.emptySet());
      case "string": return new Type("String", Collections.emptySet());
      default: return new Type("Object", Collections.emptySet());
    }
  }

  public Map<String, Object> dereference(Map<String, Object> schema) {
    String ref = schema == null ? "" : string(schema.get("$ref"));
    return ref.isEmpty() ? (schema == null ? Collections.emptyMap() : schema)
        : SpecParser.map(schemas.get(ref.substring(ref.lastIndexOf('/') + 1)));
  }
  private Type external(String qualifiedName) {
    Set<String> imports = new LinkedHashSet<>(); imports.add(qualifiedName);
    return new Type(qualifiedName.substring(qualifiedName.lastIndexOf('.') + 1), imports);
  }
  private Type generic(String raw, Type item, String rawImport) {
    Set<String> imports = new LinkedHashSet<>(item.imports()); imports.add(rawImport);
    return new Type(raw + "<" + item.name() + ">", imports);
  }
  private static String schemaTypeName(String schemaName) {
    StringBuilder result = new StringBuilder();
    for (String part : schemaName.split("[._]")) {
      if (!part.isEmpty()) {
        result.append(Character.toUpperCase(part.charAt(0))).append(part.substring(1));
      }
    }
    return result.toString();
  }

  private static String string(Object value) { return value == null ? "" : String.valueOf(value); }

  public static final class Type {
    private final String name; private final Set<String> imports;
    Type(String name, Set<String> imports) { this.name = name; this.imports = Collections.unmodifiableSet(imports); }
    public String name() { return name; } public Set<String> imports() { return imports; }
  }
}
