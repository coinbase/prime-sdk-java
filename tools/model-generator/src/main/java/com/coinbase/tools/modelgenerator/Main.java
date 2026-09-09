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

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.io.FileUtils;

/** CLI entry point for committed-spec generation, isolated checks, and live client-surface diffs. */
public final class Main {
  private Main() {}

  public static void main(String[] args) {
    try {
      run(args, GeneratorPaths.fromWorkingDirectory());
    } catch (Exception exception) {
      exception.printStackTrace(System.err);
      System.exit(1);
    }
  }

  static void run(String[] args, GeneratorPaths paths) throws Exception {
    GeneratorConfiguration configuration = GeneratorConfiguration.load(paths);
    if (has(args, "--fetch-spec")) {
      SpecFetcher.fetch(paths.root(), configuration.specUrl(), configuration.committedSpecPath());
      return;
    }

    boolean check = has(args, "--check");
    boolean liveDiff = has(args, "--live-diff");
    boolean skipModels = has(args, "--skip-models");
    Path spec = paths.root().resolve(configuration.committedSpecPath());
    if (liveDiff) {
      spec = SpecFetcher.fetchToTemporary(paths.root(), configuration.specUrl());
      check = true;
      skipModels = true;
    }

    List<String> changes = new ArrayList<>();
    if (check && !liveDiff) {
      changes.addAll(checkModelsInIsolation(paths, spec));
    } else if (!skipModels && !check) {
      new OpenApiGenerator(spec.toString(), paths.rawRoot()).generateModels();
      new PostProcessor(
              paths.rawRoot(),
              paths.sourceRoot(),
              paths.modelRoot(),
              paths.enumRoot(),
              paths.errorRoot(),
              spec,
              paths.modelManifest())
          .processModels();
    }

    SpecModels.Document document = SpecParser.load(spec);
    NamingResolver names =
        new NamingResolver(configuration.nameReplacements(), configuration.modelTypeMappings());
    List<OperationBinding> bindings = OperationBindingGenerator.deriveAll(document, configuration);
    JavaTypeResolver types = new JavaTypeResolver(document, names, configuration.sharedModelMappings());
    Map<Path, String> sources = new LinkedHashMap<>();
    sources.putAll(RequestPhase.render(document, bindings, types, names));
    sources.putAll(ResponsePhase.render(document, bindings, types, names));
    sources.putAll(ServicePhase.render(document, bindings, configuration, names));
    sources.putAll(FactoryPhase.render(bindings));
    changes.addAll(
        GeneratedSourceReconciler.diff(
            paths.sourceRoot(), sources, configuration.protectedFiles(), paths.manifest()));

    if (check) {
      for (String change : changes) {
        System.out.println(change);
      }
      if (!changes.isEmpty()) {
        throw new IllegalStateException("Generated source is out of date (" + changes.size() + " changes)");
      }
    } else {
      GeneratedSourceReconciler.write(
          paths.sourceRoot(), sources, configuration.protectedFiles(), paths.manifest());
      System.out.println("Generated " + sources.size() + " client-surface files");
    }
  }

  /** Renders models into a temporary source tree so checks never touch committed SDK files. */
  static List<String> checkModelsInIsolation(GeneratorPaths paths, Path spec) throws Exception {
    Files.createDirectories(paths.rawRoot());
    Path stagingRoot = Files.createTempDirectory(paths.rawRoot(), "check-models-");
    try {
      Path rawGenerationRoot = stagingRoot.resolve("raw-generation");
      Path stagedSourceRoot = stagingRoot.resolve("src/main/java");
      Path stagedModelRoot = stagedSourceRoot.resolve("com/coinbase/prime/model");
      Path stagedManifest = stagingRoot.resolve("generated-model-files.json");
      new OpenApiGenerator(spec.toString(), rawGenerationRoot).generateModels();
      new PostProcessor(
              rawGenerationRoot,
              stagedSourceRoot,
              stagedModelRoot,
              stagedModelRoot.resolve("enums"),
              stagedModelRoot.resolve("errors"),
              spec,
              stagedManifest)
          .processModels();
      return GeneratedSourceReconciler.diff(
          paths.sourceRoot(),
          GeneratedSourceReconciler.readOwnedSources(stagedSourceRoot, stagedManifest),
          java.util.Collections.emptySet(),
          paths.modelManifest());
    } finally {
      FileUtils.deleteDirectory(stagingRoot.toFile());
    }
  }

  private static boolean has(String[] args, String value) {
    for (String arg : args) {
      if (value.equals(arg)) {
        return true;
      }
    }
    return false;
  }
}
