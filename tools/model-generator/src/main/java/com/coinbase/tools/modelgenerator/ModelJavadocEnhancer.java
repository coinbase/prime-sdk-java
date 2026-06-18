/*
 * Copyright 2025-present Coinbase Global, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.coinbase.tools.modelgenerator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Applies model class- and field-level Javadoc from OpenAPI schema {@code description} and
 * {@code title} fields. The Prime public spec often uses {@code title} where {@code description}
 * is absent; the stock Mustache template only emits property docs for {@code description}.
 */
public final class ModelJavadocEnhancer {
    private static final Logger logger = LoggerFactory.getLogger(ModelJavadocEnhancer.class);

    private static final Pattern JSON_PROPERTY =
        Pattern.compile("@JsonProperty\\(\"([^\"]+)\"\\)");
    private static final Pattern PRIVATE_FIELD =
        Pattern.compile("private [\\w.<>,\\[\\]]+ (\\w+);");

    private final Map<String, ModelDoc> docsByJavaName;

    private ModelJavadocEnhancer(Map<String, ModelDoc> docsByJavaName) {
        this.docsByJavaName = docsByJavaName;
    }

    public static ModelJavadocEnhancer load(Path specPath) throws IOException {
        Map<String, ModelDoc> docs = new LinkedHashMap<>();

        try (InputStream in = Files.newInputStream(specPath)) {
            Yaml yaml = new Yaml();
            @SuppressWarnings("unchecked")
            Map<String, Object> root = yaml.load(in);
            @SuppressWarnings("unchecked")
            Map<String, Object> components = (Map<String, Object>) root.get("components");
            if (components == null) {
                return new ModelJavadocEnhancer(docs);
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> schemas = (Map<String, Object>) components.get("schemas");
            if (schemas == null) {
                return new ModelJavadocEnhancer(docs);
            }

            for (Map.Entry<String, Object> entry : schemas.entrySet()) {
                if (!(entry.getValue() instanceof Map)) {
                    continue;
                }
                @SuppressWarnings("unchecked")
                Map<String, Object> schema = (Map<String, Object>) entry.getValue();
                if (schema.containsKey("enum")) {
                    continue;
                }
                @SuppressWarnings("unchecked")
                Map<String, Object> properties = (Map<String, Object>) schema.get("properties");
                if (properties == null || properties.isEmpty()) {
                    continue;
                }

                String shortName = schemaShortKey(entry.getKey());
                String javaName = PostProcessor.deriveJavaModelName(shortName);
                String classDescription = firstNonBlank(
                    stringOrNull(schema.get("description")),
                    stringOrNull(schema.get("title")));

                Map<String, String> fieldDocs = new LinkedHashMap<>();
                for (Map.Entry<String, Object> prop : properties.entrySet()) {
                    if (!(prop.getValue() instanceof Map)) {
                        continue;
                    }
                    @SuppressWarnings("unchecked")
                    Map<String, Object> propSchema = (Map<String, Object>) prop.getValue();
                    String fieldDoc = resolvePropertyDoc(propSchema, schemas);
                    if (fieldDoc != null && !fieldDoc.isBlank()) {
                        fieldDocs.put(prop.getKey(), fieldDoc.strip());
                    }
                }

                if ((classDescription == null || classDescription.isBlank()) && fieldDocs.isEmpty()) {
                    continue;
                }

                ModelDoc modelDoc = new ModelDoc(classDescription, fieldDocs);
                docs.put(javaName, modelDoc);
                if (!shortName.equals(javaName)) {
                    docs.putIfAbsent(shortName, modelDoc);
                }
            }
        }

        logger.info("Loaded model Javadoc for {} schemas", docs.size());
        return new ModelJavadocEnhancer(docs);
    }

    public String apply(String content, String className) {
        ModelDoc doc = docsByJavaName.get(className);
        if (doc == null) {
            return content;
        }
        content = replaceClassJavadoc(content, className, doc.getClassDescription());
        return insertFieldJavadocs(content, doc.getFieldDocsByWireName());
    }

    private String replaceClassJavadoc(String content, String className, String description) {
        if (description == null || description.isBlank()) {
            return content;
        }

        Pattern decl = Pattern.compile("public class " + Pattern.quote(className) + "\\s*\\{");
        Matcher matcher = decl.matcher(content);
        if (!matcher.find()) {
            return content;
        }

        int declStart = matcher.start();
        String before = content.substring(0, declStart).replaceFirst("(?s)\\s*/\\*\\*.*?\\*/\\s*$", "\n");
        return before + formatClassJavadoc(description) + content.substring(declStart);
    }

    private String insertFieldJavadocs(String content, Map<String, String> fieldDocsByWireName) {
        if (fieldDocsByWireName.isEmpty()) {
            return content;
        }

        String[] lines = content.split("\n", -1);
        StringBuilder out = new StringBuilder();

        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            String stripped = line.strip();
            String indent = leadingIndent(line);

            // Builder fields use 8-space indent in pojo.mustache; skip them.
            if (indent.length() >= 8) {
                out.append(line);
                if (i < lines.length - 1) {
                    out.append('\n');
                }
                continue;
            }

            if (!hasJavadocOnPreviousLine(lines, i)) {
                Matcher jsonProp = JSON_PROPERTY.matcher(stripped);
                if (jsonProp.matches()) {
                    String doc = fieldDocsByWireName.get(jsonProp.group(1));
                    if (doc != null) {
                        out.append(formatFieldJavadoc(doc, indent));
                    }
                } else {
                    Matcher field = PRIVATE_FIELD.matcher(stripped);
                    if (field.matches()) {
                        String prev = previousNonBlankLine(lines, i);
                        if (prev == null || !JSON_PROPERTY.matcher(prev).matches()) {
                            String javaField = field.group(1);
                            String doc = fieldDocsByWireName.get(javaField);
                            if (doc == null) {
                                doc = fieldDocsByWireName.get(snakeCase(javaField));
                            }
                            if (doc != null) {
                                out.append(formatFieldJavadoc(doc, indent));
                            }
                        }
                    }
                }
            }

            out.append(line);
            if (i < lines.length - 1) {
                out.append('\n');
            }
        }

        return out.toString();
    }

    private static String resolvePropertyDoc(Map<String, Object> propSchema, Map<String, Object> schemas) {
        String direct = firstNonBlank(
            stringOrNull(propSchema.get("description")),
            stringOrNull(propSchema.get("title")));
        if (direct != null) {
            return direct;
        }
        String ref = stringOrNull(propSchema.get("$ref"));
        if (ref == null) {
            return null;
        }
        Map<String, Object> resolved = resolveRef(schemas, ref);
        if (resolved == null) {
            return null;
        }
        return firstNonBlank(
            stringOrNull(resolved.get("description")),
            stringOrNull(resolved.get("title")));
    }

    @SuppressWarnings("unchecked")
    private static Map<String, Object> resolveRef(Map<String, Object> schemas, String ref) {
        if (!ref.startsWith("#/components/schemas/")) {
            return null;
        }
        String key = ref.substring("#/components/schemas/".length());
        Object schema = schemas.get(key);
        return schema instanceof Map ? (Map<String, Object>) schema : null;
    }

    private static boolean hasJavadocOnPreviousLine(String[] lines, int index) {
        for (int j = index - 1; j >= 0; j--) {
            String trimmed = lines[j].strip();
            if (trimmed.isEmpty()) {
                continue;
            }
            if (trimmed.startsWith("/**") || trimmed.startsWith("*") || trimmed.endsWith("*/")) {
                return true;
            }
            return false;
        }
        return false;
    }

    private static String previousNonBlankLine(String[] lines, int index) {
        for (int j = index - 1; j >= 0; j--) {
            String trimmed = lines[j].strip();
            if (!trimmed.isEmpty()) {
                return trimmed;
            }
        }
        return null;
    }

    private static String leadingIndent(String line) {
        int i = 0;
        while (i < line.length() && line.charAt(i) == ' ') {
            i++;
        }
        return line.substring(0, i);
    }

    private static String snakeCase(String javaField) {
        return javaField.replaceAll("([a-z0-9])([A-Z])", "$1_$2").toLowerCase();
    }

    private static String schemaShortKey(String fullName) {
        int dot = fullName.lastIndexOf('.');
        return dot >= 0 ? fullName.substring(dot + 1) : fullName;
    }

    private static String stringOrNull(Object value) {
        return value instanceof String ? (String) value : null;
    }

    private static String firstNonBlank(String... values) {
        for (String value : values) {
            if (value != null && !value.isBlank()) {
                return value.strip();
            }
        }
        return null;
    }

    private static String formatClassJavadoc(String description) {
        String stripped = description.strip();
        if (!stripped.contains("\n")) {
            return "/** " + normalizeJavadocLine(stripped) + " */\n";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("/**\n");
        for (String line : stripped.split("\n")) {
            String trimmed = line.strip();
            if (!trimmed.isEmpty()) {
                sb.append(" * ").append(normalizeJavadocLine(trimmed)).append('\n');
            }
        }
        sb.append(" */\n");
        return sb.toString();
    }

    private static String formatFieldJavadoc(String description, String indent) {
        String stripped = description.strip();
        if (!stripped.contains("\n")) {
            return indent + "/** " + normalizeJavadocLine(stripped) + " */\n";
        }
        StringBuilder sb = new StringBuilder();
        sb.append(indent).append("/**\n");
        for (String line : stripped.split("\n")) {
            String trimmed = line.strip();
            if (!trimmed.isEmpty()) {
                sb.append(indent).append(" * ").append(normalizeJavadocLine(trimmed)).append('\n');
            }
        }
        sb.append(indent).append(" */\n");
        return sb.toString();
    }

    private static String normalizeJavadocLine(String text) {
        return PostProcessor.decodeHtmlEntities(text.strip());
    }

    static final class ModelDoc {
        private final String classDescription;
        private final Map<String, String> fieldDocsByWireName;

        ModelDoc(String classDescription, Map<String, String> fieldDocsByWireName) {
            this.classDescription = classDescription;
            this.fieldDocsByWireName = fieldDocsByWireName;
        }

        String getClassDescription() {
            return classDescription;
        }

        Map<String, String> getFieldDocsByWireName() {
            return fieldDocsByWireName;
        }
    }
}
