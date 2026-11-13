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
import java.util.LinkedHashMap;
import java.util.Map;

/** Shared schema-to-Java naming policy used by models and client-surface emitters. */
public final class NamingResolver {
  private final Map<String, String> modelTypeMappings;

  public NamingResolver() {
    this(Collections.emptyMap());
  }

  public NamingResolver(Map<String, String> modelTypeMappings) {
    this.modelTypeMappings = new LinkedHashMap<>(modelTypeMappings);
  }

  public String typeName(String schemaName) {
    // PostProcessor owns acronym and prefix normalization, so type resolution starts from the raw
    // schema name and only then applies explicit model type mappings.
    String result = SharedTransforms.modelType(schemaName == null ? "Object" : schemaName);
    return modelTypeMappings.getOrDefault(result, result);
  }

  public String propertyName(String wireName) {
    StringBuilder result = new StringBuilder();
    boolean upper = false;
    for (char character : wireName.toCharArray()) {
      if (!Character.isLetterOrDigit(character)) {
        upper = true;
        continue;
      }
      if (result.length() == 0) result.append(Character.toLowerCase(character));
      else result.append(upper ? Character.toUpperCase(character) : character);
      upper = false;
    }
    return result.length() == 0 ? "value" : result.toString();
  }

  public String methodName(String sdkMethod) {
    return Character.toLowerCase(sdkMethod.charAt(0)) + sdkMethod.substring(1);
  }
}
