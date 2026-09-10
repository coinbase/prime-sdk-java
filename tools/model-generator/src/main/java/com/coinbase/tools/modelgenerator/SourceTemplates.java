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

import java.util.Set;
import java.util.TreeSet;

/** Small formatting primitives shared by all Java source emitters. */
final class SourceTemplates {
  private SourceTemplates() {}
  static String header() { return "/*\n * Copyright 2026-present Coinbase Global, Inc.\n *\n * Licensed under the Apache License, Version 2.0 (the \"License\");\n * you may not use this file except in compliance with the License.\n * You may obtain a copy of the License at\n *\n * http://www.apache.org/licenses/LICENSE-2.0\n *\n * Unless required by applicable law or agreed to in writing, software\n * distributed under the License is distributed on an \"AS IS\" BASIS,\n * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.\n * See the License for the specific language governing permissions and\n * limitations under the License.\n */\n"; }
  static String importBlock(Set<String> imports) { StringBuilder value = new StringBuilder(); for (String name : new TreeSet<>(imports)) { if (name.startsWith("static ")) value.append("import static ").append(name.substring(7)).append(";\n"); else value.append("import ").append(name).append(";\n"); } return value.length() == 0 ? "" : value.append("\n").toString(); }
  static void imports(StringBuilder source, Set<String> imports) { source.append(importBlock(imports)); }
  static void javadoc(StringBuilder source, String text) {
    if (text != null && !text.trim().isEmpty()) {
      source.append("/** ").append(text.replace("*/", "* /").replace("\n", " ")).append(" */\n");
    }
  }

  static String documentation(String summary, String description) {
    String concise = summary == null ? "" : summary.trim();
    String detail = description == null ? "" : description.trim();
    if (concise.isEmpty()) return detail;
    if (detail.isEmpty() || concise.equals(detail)) return concise;
    return concise + ". " + detail;
  }
  static String cap(String value) { return Character.toUpperCase(value.charAt(0)) + value.substring(1); }
}
