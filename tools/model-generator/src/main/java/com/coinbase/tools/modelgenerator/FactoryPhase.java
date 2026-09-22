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
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Emits the static factory surface for all generated Prime services. */
public final class FactoryPhase {
  private FactoryPhase() {}
  public static Map<Path, String> render(List<OperationBinding> bindings) {
    Map<String, OperationBinding> services = new LinkedHashMap<>();
    for (OperationBinding binding : bindings) services.putIfAbsent(binding.serviceName(), binding);
    List<OperationBinding> ordered = new java.util.ArrayList<>(services.values()); ordered.sort(Comparator.comparing(OperationBinding::serviceName));
    Set<String> imports = new LinkedHashSet<>(); imports.add("com.coinbase.prime.client.CoinbasePrimeClient");
    for (OperationBinding binding : ordered) { String prefix="com.coinbase.prime." + binding.serviceFolder() + "."; imports.add(prefix + binding.serviceName()); imports.add(prefix + binding.serviceName() + "Impl"); }
    StringBuilder source=new StringBuilder(SourceTemplates.header()).append("package com.coinbase.prime.factory;\n\n"); SourceTemplates.imports(source, imports); source.append("public class PrimeServiceFactory {\n");
    for (OperationBinding binding : ordered) source.append("  public static ").append(binding.serviceName()).append(" create").append(binding.serviceName()).append("(CoinbasePrimeClient client) {\n    return new ").append(binding.serviceName()).append("Impl(client);\n  }\n\n");
    source.append("}\n"); Map<Path,String> result=new LinkedHashMap<>(); result.put(Path.of("com/coinbase/prime/factory/PrimeServiceFactory.java"),source.toString()); return result;
  }
}
