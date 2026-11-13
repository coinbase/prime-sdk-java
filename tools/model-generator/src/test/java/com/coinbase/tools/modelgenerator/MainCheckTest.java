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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.sun.net.httpserver.HttpServer;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.MessageDigest;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Test;

class MainCheckTest {
  @Test
  void checkCliLeavesFixtureSourcesAndManifestStateByteForByteUnchanged() throws Exception {
    Path root = Files.createTempDirectory("isolated-generator-check");
    try {
      writeFixture(root);
      Map<String, String> before = snapshot(root);

      IllegalStateException exception =
          assertThrows(
              IllegalStateException.class,
              () -> {
                Main.run(new String[] {"--check"}, GeneratorPaths.forRoot(root));
              });

      assertTrue(exception.getMessage().contains("out of date"), exception.getMessage());
      assertEquals(before, snapshot(root));
      assertFalse(Files.exists(root.resolve("generated")));
      assertFalse(Files.exists(root.resolve("tools/model-generator/generated-files.json")));
      assertFalse(Files.exists(root.resolve("tools/model-generator/generated-model-files.json")));
      assertFalse(Files.exists(root.resolve("src/main/java/com/coinbase/prime/things/ListThingsResponse.java")));
      assertFalse(Files.exists(root.resolve("src/main/java/com/coinbase/prime/model/Thing.java")));
    } finally {
      FileUtils.deleteDirectory(root.toFile());
    }
  }

  @Test
  void checkReportsProtectedCompatibilityDriftWithoutChangingProjectFiles() throws Exception {
    Path root = Files.createTempDirectory("protected-generator-check");
    Path protectedRelative = Path.of("com/coinbase/prime/things/ListThingsResponse.java");
    Path protectedFile = root.resolve("src/main/java").resolve(protectedRelative);
    try {
      writeFixture(root);
      Files.writeString(
          root.resolve("tools/model-generator/config/generator-config.json"),
          "{\"specUrl\":\"unused\",\"committedSpecPath\":\"apiSpec/openapi.yaml\","
              + "\"protectedCompatibilityFiles\":[\""
              + protectedRelative
              + "\"]}\n");
      Files.createDirectories(protectedFile.getParent());
      Files.writeString(
          protectedFile,
          "package com.coinbase.prime.things;\npublic class ListThingsResponse {}\n");
      Main.run(new String[0], GeneratorPaths.forRoot(root));
      Main.formatStagedSources(root);
      Files.writeString(
          protectedFile,
          "package com.coinbase.prime.things;\n// Compatibility implementation.\n"
              + "public class ListThingsResponse {}\n");
      Map<String, String> before = snapshot(root);

      ByteArrayOutputStream output = new ByteArrayOutputStream();
      PrintStream originalOut = System.out;
      try {
        System.setOut(new PrintStream(output, true, StandardCharsets.UTF_8));
        IllegalStateException exception =
            assertThrows(
                IllegalStateException.class,
                () -> Main.run(new String[] {"--check"}, GeneratorPaths.forRoot(root)));
        assertTrue(exception.getMessage().contains("out of date"), exception.getMessage());
      } finally {
        System.setOut(originalOut);
      }

      assertTrue(
          output.toString(StandardCharsets.UTF_8).contains("SKIP " + protectedRelative),
          output.toString(StandardCharsets.UTF_8));
      assertEquals(before, snapshot(root));
      assertFalse(Files.exists(root.resolve("generated")));
    } finally {
      FileUtils.deleteDirectory(root.toFile());
    }
  }

  @Test
  void liveDiffUsesFormattedIsolatedOutputAndDeletesDownloadedSpec() throws Exception {
    Path root = Files.createTempDirectory("live-generator-diff");
    HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
    try {
      writeFixture(root);
      byte[] spec = Files.readAllBytes(root.resolve("apiSpec/openapi.yaml"));
      server.createContext(
          "/openapi.yaml",
          exchange -> {
            exchange.sendResponseHeaders(200, spec.length);
            exchange.getResponseBody().write(spec);
            exchange.close();
          });
      server.start();
      Files.writeString(
          root.resolve("tools/model-generator/config/generator-config.json"),
          "{\"specUrl\":\"http://127.0.0.1:"
              + server.getAddress().getPort()
              + "/openapi.yaml\",\"committedSpecPath\":\"apiSpec/openapi.yaml\"}\n");

      Main.run(new String[0], GeneratorPaths.forRoot(root));
      Main.formatStagedSources(root);
      Map<String, String> beforeLiveDiff = snapshot(root);
      Main.run(new String[] {"--live-diff"}, GeneratorPaths.forRoot(root));

      assertEquals(beforeLiveDiff, snapshot(root));
      assertFalse(Files.exists(root.resolve("generated")));
    } finally {
      server.stop(0);
      FileUtils.deleteDirectory(root.toFile());
    }
  }

  @Test
  void failedLiveFetchDeletesPartialTemporarySpec() throws Exception {
    Path root = Files.createTempDirectory("failed-live-generator-diff");
    try {
      writeFixture(root);
      Files.writeString(
          root.resolve("tools/model-generator/config/generator-config.json"),
          "{\"specUrl\":\"http://127.0.0.1:1/unavailable\",\"committedSpecPath\":\"apiSpec/openapi.yaml\"}\n");
      assertThrows(Exception.class, () -> Main.run(new String[] {"--live-diff"}, GeneratorPaths.forRoot(root)));
      Path generated = root.resolve("generated");
      assertFalse(Files.exists(generated.resolve("prime-public-spec-.yaml")));
      if (Files.exists(generated)) {
        try (Stream<Path> paths = Files.list(generated)) {
          assertFalse(paths.anyMatch(path -> path.getFileName().toString().endsWith(".yaml")));
        }
      }
    } finally {
      FileUtils.deleteDirectory(root.toFile());
    }
  }

  private static void writeFixture(Path root) throws Exception {
    Path sourceRoot = root.resolve("src/main/java/com/coinbase/prime");
    Files.createDirectories(sourceRoot);
    Files.createDirectories(root.resolve("apiSpec"));
    Files.createDirectories(root.resolve("tools/model-generator/config"));
    Files.writeString(root.resolve("pom.xml"), fixturePom());
    Files.writeString(sourceRoot.resolve("Existing.java"), "package com.coinbase.prime;\nclass Existing {}\n");
    Files.writeString(
        root.resolve("tools/model-generator/config/generator-config.json"),
        "{\"specUrl\":\"unused\",\"committedSpecPath\":\"apiSpec/openapi.yaml\"}\n");
    Files.writeString(root.resolve("tools/model-generator/config/operations-overrides.json"), "[]\n");
    Files.writeString(
        root.resolve("apiSpec/openapi.yaml"),
        String.join(
            "\n",
            "openapi: 3.0.0",
            "info: { title: test, version: 1.0.0 }",
            "paths:",
            "  /v1/things:",
            "    get:",
            "      operationId: PrimeRESTAPI_ListThings",
            "      tags: [Things]",
            "      responses:",
            "        '200':",
            "          description: Success",
            "          content:",
            "            application/json:",
            "              schema:",
            "                type: object",
            "                properties:",
            "                  thing: { $ref: '#/components/schemas/Thing' }",
            "components:",
            "  schemas:",
            "    Thing:",
            "      type: object",
            "      properties:",
            "        id: { type: string }",
            ""));
  }

  private static String fixturePom() {
    return String.join(
        "\n",
        "<project xmlns=\"http://maven.apache.org/POM/4.0.0\">",
        "  <modelVersion>4.0.0</modelVersion>",
        "  <groupId>test</groupId>",
        "  <artifactId>fixture</artifactId>",
        "  <version>1.0.0</version>",
        "  <build><plugins><plugin>",
        "    <groupId>com.diffplug.spotless</groupId>",
        "    <artifactId>spotless-maven-plugin</artifactId>",
        "    <version>2.43.0</version>",
        "    <configuration><java><googleJavaFormat>",
        "      <version>1.24.0</version><style>GOOGLE</style>",
        "    </googleJavaFormat></java></configuration>",
        "  </plugin></plugins></build>",
        "</project>",
        "");
  }

  private static Map<String, String> snapshot(Path root) throws Exception {
    Map<String, String> files = new LinkedHashMap<>();
    try (Stream<Path> paths = Files.walk(root)) {
      List<Path> regularFiles = paths.filter(Files::isRegularFile).sorted().collect(Collectors.toList());
      for (Path file : regularFiles) {
        files.put(root.relativize(file).toString(), sha256(Files.readAllBytes(file)));
      }
    }
    return files;
  }

  private static String sha256(byte[] content) throws Exception {
    byte[] digest = MessageDigest.getInstance("SHA-256").digest(content);
    StringBuilder hex = new StringBuilder();
    for (byte value : digest) {
      hex.append(String.format("%02x", value));
    }
    return hex.toString();
  }
}
