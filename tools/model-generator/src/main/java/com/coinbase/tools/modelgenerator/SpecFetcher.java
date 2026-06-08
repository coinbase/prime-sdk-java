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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;

public final class SpecFetcher {
    private static final Logger logger = LoggerFactory.getLogger(SpecFetcher.class);

    static final String DEFAULT_SPEC_URL = "https://api.prime.coinbase.com/v1/openapi.yaml";
    static final String SPEC_RELATIVE_PATH = "apiSpec/prime-public-spec.yaml";

    private SpecFetcher() {}

    /**
     * Downloads the latest OpenAPI spec into {@code apiSpec/prime-public-spec.yaml} at the SDK root.
     */
    public static Path fetch(Path projectRoot) throws IOException, InterruptedException {
        return fetch(projectRoot, DEFAULT_SPEC_URL);
    }

    static Path fetch(Path projectRoot, String specUrl) throws IOException, InterruptedException {
        Path specPath = projectRoot.resolve(SPEC_RELATIVE_PATH);
        Files.createDirectories(specPath.getParent());

        logger.info("Fetching OpenAPI spec from: {}", specUrl);
        logger.info("Writing spec to: {}", specPath);

        HttpClient client = HttpClient.newBuilder()
            .followRedirects(HttpClient.Redirect.NORMAL)
            .connectTimeout(Duration.ofSeconds(30))
            .build();

        HttpRequest request = HttpRequest.newBuilder()
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
