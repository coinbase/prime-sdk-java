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

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/** Small formatting primitives shared by all Java source emitters. */
final class SourceTemplates {
  private SourceTemplates() {}
  /** The license header used by vended client-surface sources (two-space body, blank line before the package). */
  static String header() { return "/*\n * Copyright 2026-present Coinbase Global, Inc.\n *\n *  Licensed under the Apache License, Version 2.0 (the \"License\");\n *  you may not use this file except in compliance with the License.\n *  You may obtain a copy of the License at\n *\n *  http://www.apache.org/licenses/LICENSE-2.0\n *\n *  Unless required by applicable law or agreed to in writing, software\n *  distributed under the License is distributed on an \"AS IS\" BASIS,\n *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n *  See the License for the specific language governing permissions and\n *  limitations under the License.\n */\n\n"; }
  static final String DOC_SEPARATOR = "\u0001";

  static String importBlock(Set<String> imports) { StringBuilder value = new StringBuilder(); for (String name : new TreeSet<>(imports)) { if (name.startsWith("static ")) value.append("import static ").append(name.substring(7)).append(";\n"); else value.append("import ").append(name).append(";\n"); } return value.length() == 0 ? "" : value.append("\n").toString(); }
  static void imports(StringBuilder source, Set<String> imports) { source.append(importBlock(imports)); }
  static void javadoc(StringBuilder source, String text) {
    if (text == null) return;
    // A field can carry several consecutive Javadoc blocks (vended sources do); they are kept apart.
    for (String part : text.split(DOC_SEPARATOR)) {
      if (!part.trim().isEmpty()) {
        source.append("/** ").append(part.replace("*/", "* /").replace("\n", " ")).append(" */\n");
      }
    }
  }

  static String documentation(String summary, String description) {
    String concise = summary == null ? "" : summary.trim();
    String detail = description == null ? "" : description.trim();
    if (concise.isEmpty()) return detail;
    if (detail.isEmpty() || concise.equals(detail)) return concise;
    return concise + ". " + detail;
  }
  /** Class-level documentation for a request or response DTO: the operation summary. */
  static String classDocumentation(String summary, String description) {
    String concise = summary == null ? "" : summary.trim();
    return concise.isEmpty() ? (description == null ? "" : description.trim()) : concise;
  }

  /** Writes the vended service-interface Javadoc layout: sentence, optional paragraph, then tags. */
  static void methodJavadoc(
      StringBuilder source, String summary, String description, List<String> tags) {
    String concise = summary == null ? "" : summary.trim();
    String detail = description == null ? "" : description.trim().replace("*/", "* /").replace("\n", " ");
    if (detail.endsWith(".")) detail = detail.substring(0, detail.length() - 1);
    source.append("  /**\n");
    if (!concise.isEmpty()) {
      source.append("   * ").append(concise.replace("*/", "* /")).append(concise.endsWith(".") ? "" : ".").append("\n");
    }
    if (!detail.isEmpty() && !detail.equals(concise)) {
      if (!concise.isEmpty()) source.append("   *\n");
      source.append("   * <p>").append(detail).append("\n");
    }
    if (!tags.isEmpty()) source.append("   *\n");
    for (String tag : tags) source.append("   * ").append(tag).append("\n");
    source.append("   */\n");
  }

  static String cap(String value) { return Character.toUpperCase(value.charAt(0)) + value.substring(1); }
}
