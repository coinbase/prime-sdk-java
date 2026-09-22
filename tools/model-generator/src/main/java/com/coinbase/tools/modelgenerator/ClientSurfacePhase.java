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

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Groups validated bindings in the deterministic order consumed by source emitters. */
public final class ClientSurfacePhase {
  private ClientSurfacePhase() {}

  public static Map<String, List<OperationBinding>> groupByService(List<OperationBinding> bindings) {
    Map<String, List<OperationBinding>> services = new LinkedHashMap<>();
    List<OperationBinding> ordered = new ArrayList<>(bindings);
    ordered.sort(Comparator.comparing(OperationBinding::serviceFolder).thenComparing(OperationBinding::sdkMethod));
    for (OperationBinding binding : ordered) {
      services.computeIfAbsent(binding.serviceFolder(), ignored -> new ArrayList<>()).add(binding);
    }
    return services;
  }
}
