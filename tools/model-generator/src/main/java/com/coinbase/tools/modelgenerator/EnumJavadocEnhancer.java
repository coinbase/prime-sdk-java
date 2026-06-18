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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Applies enum class- and constant-level Javadoc from OpenAPI schema descriptions.
 * Per-value docs are parsed from bullet lists ({@code - VALUE: description}) in the schema
 * {@code description} field (the Prime public spec does not use {@code x-enum-descriptions}).
 */
public final class EnumJavadocEnhancer {
    private static final Logger logger = LoggerFactory.getLogger(EnumJavadocEnhancer.class);

    private static final Pattern ENUM_CONSTANT_LINE =
        Pattern.compile("^(\\s*)([A-Z][A-Z0-9_]*)(\\s*,?\\s*)$");

    private final Map<String, EnumDoc> docsByJavaName;

    private EnumJavadocEnhancer(Map<String, EnumDoc> docsByJavaName) {
        this.docsByJavaName = docsByJavaName;
    }

    public static EnumJavadocEnhancer load(Path specPath) throws IOException {
        Map<String, EnumDoc> docs = new LinkedHashMap<>();

        try (InputStream in = Files.newInputStream(specPath)) {
            Yaml yaml = new Yaml();
            @SuppressWarnings("unchecked")
            Map<String, Object> root = yaml.load(in);
            @SuppressWarnings("unchecked")
            Map<String, Object> components = (Map<String, Object>) root.get("components");
            if (components == null) {
                return new EnumJavadocEnhancer(docs);
            }
            @SuppressWarnings("unchecked")
            Map<String, Object> schemas = (Map<String, Object>) components.get("schemas");
            if (schemas == null) {
                return new EnumJavadocEnhancer(docs);
            }

            for (Map.Entry<String, Object> entry : schemas.entrySet()) {
                if (!(entry.getValue() instanceof Map)) {
                    continue;
                }
                @SuppressWarnings("unchecked")
                Map<String, Object> schema = (Map<String, Object>) entry.getValue();
                if (!schema.containsKey("enum")) {
                    continue;
                }

                String description = stringOrNull(schema.get("description"));
                if (description == null) {
                    description = stringOrNull(schema.get("title"));
                }

                String schemaKey = entry.getKey();
                String shortName = schemaShortKey(schemaKey);
                String javaName = PostProcessor.stripSchemaNameToJavaType(shortName);

                EnumDoc enumDoc = new EnumDoc(description, parseEnumValueDescriptions(description));
                docs.put(javaName, enumDoc);
                if (!shortName.equals(javaName)) {
                    docs.putIfAbsent(shortName, enumDoc);
                }
            }
        }

        logger.info("Loaded enum Javadoc for {} schemas", docs.size());
        return new EnumJavadocEnhancer(docs);
    }

    public String apply(String content, String className) {
        EnumDoc doc = docsByJavaName.get(className);
        if (doc == null) {
            return content;
        }
        content = replaceClassJavadoc(content, className, doc.getClassDescription());
        return insertConstantJavadocs(content, doc.getValueDescriptions());
    }

    static Map<String, String> parseEnumValueDescriptions(String description) {
        if (description == null || description.isBlank()) {
            return Collections.emptyMap();
        }
        Map<String, String> result = new LinkedHashMap<>();
        for (String line : description.split("\n")) {
            String trimmed = line.strip();
            if (!trimmed.startsWith("- ")) {
                continue;
            }
            String body = trimmed.substring(2).strip();
            int colon = body.indexOf(':');
            if (colon <= 0) {
                continue;
            }
            String key = body.substring(0, colon).strip();
            String value = body.substring(colon + 1).strip();
            if (!key.isEmpty() && !value.isEmpty()) {
                result.put(key, value);
            }
        }
        return result;
    }

    private static String schemaShortKey(String fullName) {
        int dot = fullName.lastIndexOf('.');
        return dot >= 0 ? fullName.substring(dot + 1) : fullName;
    }

    private static String stringOrNull(Object value) {
        return value instanceof String ? (String) value : null;
    }

    private String replaceClassJavadoc(String content, String className, String description) {
        if (description == null || description.isBlank()) {
            return content;
        }

        Pattern decl = Pattern.compile("public enum " + Pattern.quote(className) + "\\s*\\{");
        Matcher matcher = decl.matcher(content);
        if (!matcher.find()) {
            return content;
        }

        int declStart = matcher.start();
        String before = content.substring(0, declStart).replaceFirst("(?s)\\s*/\\*\\*.*?\\*/\\s*$", "\n");
        return before + formatClassJavadoc(description) + content.substring(declStart);
    }

    private String insertConstantJavadocs(String content, Map<String, String> valueDescriptions) {
        if (valueDescriptions.isEmpty()) {
            return content;
        }

        String[] lines = content.split("\n", -1);
        StringBuilder out = new StringBuilder();
        for (String line : lines) {
            Matcher m = ENUM_CONSTANT_LINE.matcher(line);
            if (m.matches()) {
                String constant = m.group(2);
                String desc = valueDescriptions.get(constant);
                if (desc != null && !hasJavadocBefore(out.toString())) {
                    out.append(formatConstantJavadoc(desc, m.group(1)));
                }
            }
            out.append(line).append('\n');
        }
        return out.substring(0, out.length() - 1);
    }

    private static boolean hasJavadocBefore(String text) {
        return text.stripTrailing().endsWith("*/");
    }

    private static String formatClassJavadoc(String description) {
        StringBuilder sb = new StringBuilder();
        sb.append("/**\n");
        for (String line : description.strip().split("\n")) {
            String trimmed = line.strip();
            if (!trimmed.isEmpty()) {
                sb.append(" * ").append(normalizeJavadoc(trimmed)).append('\n');
            }
        }
        sb.append(" */\n");
        return sb.toString();
    }

    private static String formatConstantJavadoc(String description, String indent) {
        return indent + "/**\n"
            + indent + " * " + normalizeJavadoc(description) + "\n"
            + indent + " */\n";
    }

    private static String normalizeJavadoc(String text) {
        return PostProcessor.decodeHtmlEntities(text.strip().replaceAll("\\s+", " "));
    }

    static final class EnumDoc {
        private final String classDescription;
        private final Map<String, String> valueDescriptions;

        EnumDoc(String classDescription, Map<String, String> valueDescriptions) {
            this.classDescription = classDescription;
            this.valueDescriptions = valueDescriptions;
        }

        String getClassDescription() {
            return classDescription;
        }

        Map<String, String> getValueDescriptions() {
            return valueDescriptions;
        }
    }
}
