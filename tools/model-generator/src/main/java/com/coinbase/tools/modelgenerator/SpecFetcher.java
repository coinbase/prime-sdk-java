/*
 * Copyright 2025-present Coinbase Global, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.coinbase.tools.modelgenerator;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Downloads a configured OpenAPI specification to a committed or isolated destination. */
public final class SpecFetcher {
  private static final Logger logger = LoggerFactory.getLogger(SpecFetcher.class);

  static final String DEFAULT_SPEC_URL = "https://api.prime.coinbase.com/v1/openapi.yaml";
  static final String SPEC_RELATIVE_PATH = "apiSpec/prime-public-spec.yaml";

  private SpecFetcher() {}

  public static Path fetch(Path projectRoot) throws IOException, InterruptedException {
    return fetch(projectRoot, DEFAULT_SPEC_URL, SPEC_RELATIVE_PATH);
  }

  static Path fetch(Path projectRoot, String specUrl) throws IOException, InterruptedException {
    return fetch(projectRoot, specUrl, SPEC_RELATIVE_PATH);
  }

  static Path fetch(Path projectRoot, String specUrl, String committedSpecPath)
      throws IOException, InterruptedException {
    return fetchTo(destination(projectRoot, committedSpecPath), specUrl);
  }

  static Path destination(Path projectRoot, String committedSpecPath) {
    Path root = projectRoot.toAbsolutePath().normalize();
    Path destination = root.resolve(committedSpecPath).normalize();
    if (!destination.startsWith(root)) {
      throw new IllegalArgumentException("Committed spec path must remain within the repository");
    }
    return destination;
  }

  /** Downloads a live spec to a temporary file without changing committed input or generated output. */
  public static Path fetchToTemporary(Path projectRoot, String specUrl)
      throws IOException, InterruptedException {
    Path temporaryDirectory = projectRoot.resolve("generated");
    Files.createDirectories(temporaryDirectory);
    Path specPath = Files.createTempFile(temporaryDirectory, "prime-public-spec-", ".yaml");
    return fetchTo(specPath, specUrl);
  }

  private static Path fetchTo(Path specPath, String specUrl) throws IOException, InterruptedException {
    Files.createDirectories(specPath.getParent());

    logger.info("Fetching OpenAPI spec from: {}", specUrl);
    logger.info("Writing spec to: {}", specPath);

    HttpClient client =
        HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(30))
            .build();

    HttpRequest request =
        HttpRequest.newBuilder()
            .uri(URI.create(specUrl))
            .timeout(Duration.ofMinutes(2))
            .GET()
            .build();

    HttpResponse<Path> response = client.send(request, HttpResponse.BodyHandlers.ofFile(specPath));
    if (response.statusCode() < 200 || response.statusCode() >= 300) {
      throw new IOException(
          "Failed to fetch OpenAPI spec: HTTP " + response.statusCode() + " from " + specUrl);
    }

    logger.info("OpenAPI spec fetched successfully ({} bytes)", Files.size(specPath));
    return specPath;
  }
}
