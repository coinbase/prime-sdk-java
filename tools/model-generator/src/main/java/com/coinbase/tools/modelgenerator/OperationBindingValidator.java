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

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/** Fails early when an operation cannot safely own one Java SDK surface. */
public final class OperationBindingValidator {
  private static final Pattern PATH_PARAMETER = Pattern.compile("\\{([^}]+)}");

  private OperationBindingValidator() {}

  public static void validate(SpecModels.Document document, List<OperationBinding> bindings) {
    if (document.operations().size() != bindings.size()) {
      throw new IllegalArgumentException("Every OpenAPI operation must have exactly one binding");
    }
    Set<String> operationIds = new HashSet<>();
    Set<String> serviceMethods = new HashSet<>();
    for (int index = 0; index < document.operations().size(); index++) {
      SpecModels.Operation operation = document.operations().get(index);
      OperationBinding binding = bindings.get(index);
      if (!operationIds.add(binding.operationId())) throw new IllegalArgumentException("Duplicate operation binding: " + binding.operationId());
      if (!operation.operationId().equals(binding.operationId())) throw new IllegalArgumentException("Bindings must remain operation-ID sorted");
      if (!serviceMethods.add(binding.serviceFolder() + ":" + binding.sdkMethod())) {
        throw new IllegalArgumentException("Duplicate Java service method: " + binding.serviceFolder() + ":" + binding.sdkMethod());
      }
      Set<String> parameterNames = new HashSet<>();
      for (SpecModels.Parameter parameter : operation.parameters()) parameterNames.add(parameter.name());
      Matcher matcher = PATH_PARAMETER.matcher(operation.path());
      while (matcher.find()) {
        if (!parameterNames.contains(matcher.group(1))) {
          throw new IllegalArgumentException(operation.operationId() + " is missing path parameter " + matcher.group(1));
        }
      }
    }
  }
}
