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
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
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
    Path temporaryLiveSpec = null;
    try {
      if (liveDiff) {
        temporaryLiveSpec = SpecFetcher.fetchToTemporary(paths.root(), configuration.specUrl());
        spec = temporaryLiveSpec;
        check = true;
      }

      if (check) {
        // Live diffs use the identical isolated rendering and Spotless normalization as --check.
        List<String> changes = checkGeneratedInIsolation(paths, spec, configuration);
        reportChanges(changes);
        return;
      }

      if (!skipModels) {
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

      Map<Path, String> sources = renderClientSources(spec, configuration, paths.sourceRoot());
      GeneratedSourceReconciler.write(
          paths.sourceRoot(), sources, configuration.protectedFiles(), paths.manifest());
      System.out.println("Generated " + sources.size() + " client-surface files");
    } finally {
      if (temporaryLiveSpec != null) {
        Files.deleteIfExists(temporaryLiveSpec);
        try {
          Files.deleteIfExists(temporaryLiveSpec.getParent());
        } catch (java.nio.file.DirectoryNotEmptyException ignored) {
          // The generator output directory pre-existed or has unrelated content.
        }
      }
    }
  }

  /**
   * Runs the complete write-mode pipeline against a disposable copy of the source tree.
   *
   * <p>The staged project receives the same Spotless normalization as {@code make generate}; only its
   * rendered, manifest-owned files are compared with the repository. The repository itself is never
   * used as an output directory in check mode.
   */
  static List<String> checkGeneratedInIsolation(
      GeneratorPaths paths, Path spec, GeneratorConfiguration configuration) throws Exception {
    Path stagingRoot = Files.createTempDirectory("prime-sdk-java-generator-check-");
    try {
      Path stagedSourceRoot = stagingRoot.resolve("src/main/java");
      Path stagedModelRoot = stagedSourceRoot.resolve("com/coinbase/prime/model");
      Path stagedModelManifest = stagingRoot.resolve("tools/model-generator/generated-model-files.json");
      Path stagedClientManifest = stagingRoot.resolve("tools/model-generator/generated-files.json");

      copyProjectInputs(paths, stagingRoot, stagedSourceRoot, stagedModelManifest, stagedClientManifest);
      Path stagedRawRoot = stagingRoot.resolve("generated");
      new OpenApiGenerator(spec.toString(), stagedRawRoot, paths.root()).generateModels();
      new PostProcessor(
              stagedRawRoot,
              stagedSourceRoot,
              stagedModelRoot,
              stagedModelRoot.resolve("enums"),
              stagedModelRoot.resolve("errors"),
              spec,
              stagedModelManifest)
          .processModels();
      GeneratedSourceReconciler.write(
          stagedSourceRoot,
          renderClientSources(spec, configuration, stagedSourceRoot),
          configuration.protectedFiles(),
          stagedClientManifest);
      formatStagedSources(stagingRoot);

      List<String> changes = new ArrayList<>();
      changes.addAll(
          GeneratedSourceReconciler.diff(
              paths.sourceRoot(),
              GeneratedSourceReconciler.readOwnedSources(stagedSourceRoot, stagedModelManifest),
              Collections.emptySet(),
              paths.modelManifest()));
      changes.addAll(
          GeneratedSourceReconciler.diff(
              paths.sourceRoot(),
              GeneratedSourceReconciler.readOwnedSources(stagedSourceRoot, stagedClientManifest),
              configuration.protectedFiles(),
              paths.manifest()));
      addManifestChangeIfPresent(changes, paths.modelManifest(), stagedModelManifest);
      addManifestChangeIfPresent(changes, paths.manifest(), stagedClientManifest);
      Collections.sort(changes);
      return Collections.unmodifiableList(changes);
    } finally {
      FileUtils.deleteDirectory(stagingRoot.toFile());
    }
  }

  private static void copyProjectInputs(
      GeneratorPaths paths,
      Path stagingRoot,
      Path stagedSourceRoot,
      Path stagedModelManifest,
      Path stagedClientManifest)
      throws IOException {
    Files.copy(paths.root().resolve("pom.xml"), stagingRoot.resolve("pom.xml"));
    FileUtils.copyDirectory(paths.sourceRoot().toFile(), stagedSourceRoot.toFile());
    copyIfPresent(paths.modelManifest(), stagedModelManifest);
    copyIfPresent(paths.manifest(), stagedClientManifest);
  }

  private static void copyIfPresent(Path source, Path target) throws IOException {
    if (Files.exists(source)) {
      Files.createDirectories(target.getParent());
      Files.copy(source, target);
    }
  }

  static void formatStagedSources(Path stagingRoot) throws IOException, InterruptedException {
    Process process =
        new ProcessBuilder(
                "mvn", "-B", "-f", stagingRoot.resolve("pom.xml").toString(), "spotless:apply")
            .redirectErrorStream(true)
            .start();
    String output = new String(process.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
    if (process.waitFor() != 0) {
      throw new IOException("Could not format staged generated sources:\n" + output);
    }
  }

  private static void addManifestChangeIfPresent(
      List<String> changes, Path committedManifest, Path stagedManifest) throws IOException {
    if (Files.exists(committedManifest)
        && !Files.readString(committedManifest).equals(Files.readString(stagedManifest))) {
      changes.add("CHANGE " + committedManifest.getFileName());
    }
  }

  private static Map<Path, String> renderClientSources(
      Path spec, GeneratorConfiguration configuration, Path sourceRoot) throws IOException {
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
    return preserveClientSourceHeaders(sources, sourceRoot);
  }

  static Map<Path, String> preserveClientSourceHeaders(Map<Path, String> sources, Path sourceRoot)
      throws IOException {
    Map<Path, String> headedSources = new LinkedHashMap<>();
    for (Map.Entry<Path, String> source : sources.entrySet()) {
      headedSources.put(
          source.getKey(),
          GeneratedFileHeader.applyStartYear(
              source.getValue(), GeneratedFileHeader.resolveStartYear(sourceRoot.resolve(source.getKey()))));
    }
    return headedSources;
  }

  private static void reportChanges(List<String> changes) {
    for (String change : changes) {
      System.out.println(change);
    }
    if (!changes.isEmpty()) {
      throw new IllegalStateException("Generated source is out of date (" + changes.size() + " changes)");
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
