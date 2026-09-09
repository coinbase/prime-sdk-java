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
import java.util.List;
import java.util.Map;

/** CLI entry point: committed-spec generation by default, with non-mutating check and live-diff modes. */
public final class Main {
  private Main() {}
  public static void main(String[] args) {
    try {
      GeneratorPaths paths=GeneratorPaths.fromWorkingDirectory(); GeneratorConfiguration configuration=GeneratorConfiguration.load(paths);
      if (has(args, "--fetch-spec")) {
        SpecFetcher.fetch(paths.root(), configuration.specUrl());
        return;
      }
      boolean check=has(args,"--check"); boolean liveDiff=has(args,"--live-diff"); boolean skipModels=has(args,"--skip-models");
      Path spec=paths.root().resolve(configuration.committedSpecPath());
      if (liveDiff) { spec=SpecFetcher.fetchToTemporary(paths.root(), configuration.specUrl()); check=true; skipModels=true; }
      if (!skipModels && !check) { new OpenApiGenerator(spec.toString(), paths.rawRoot()).generateModels(); new PostProcessor(paths.rawRoot(),paths.modelRoot(),paths.enumRoot(),spec).processModels(); }
      SpecModels.Document document=SpecParser.load(spec); NamingResolver names=new NamingResolver(configuration.nameReplacements());
      List<OperationBinding> bindings=OperationBindingGenerator.deriveAll(document,configuration); JavaTypeResolver types=new JavaTypeResolver(document,names);
      Map<Path,String> sources=new LinkedHashMap<>(); sources.putAll(RequestPhase.render(document,bindings,types,names)); sources.putAll(ResponsePhase.render(document,bindings,types,names)); sources.putAll(ServicePhase.render(document,bindings,configuration,names)); sources.putAll(FactoryPhase.render(bindings));
      List<String> changes=GeneratedSourceReconciler.diff(paths.sourceRoot(),sources,configuration.protectedFiles(),paths.manifest());
      if (check) { for(String change:changes) System.out.println(change); if(!changes.isEmpty()) throw new IllegalStateException("Generated source is out of date ("+changes.size()+" changes)"); }
      else { GeneratedSourceReconciler.write(paths.sourceRoot(),sources,configuration.protectedFiles(),paths.manifest()); System.out.println("Generated "+sources.size()+" client-surface files"); }
    } catch (Exception exception) { exception.printStackTrace(System.err); System.exit(1); }
  }
  private static boolean has(String[] args,String value) { for(String arg:args) if(value.equals(arg)) return true; return false; }
}
