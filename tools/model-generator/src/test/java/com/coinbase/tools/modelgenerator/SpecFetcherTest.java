/*
 * Copyright 2026-present Coinbase Global, Inc.
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.sun.net.httpserver.HttpServer;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;

class SpecFetcherTest {
  private static final String COMMITTED = "openapi: 3.0.0\npaths: {}\n";

  @Test
  void failedFetchLeavesCommittedSpecUntouched() throws Exception {
    assertFetchFails(500, "upstream error");
  }

  @Test
  void fetchWithoutOperationsLeavesCommittedSpecUntouched() throws Exception {
    assertFetchFails(200, "<html>maintenance</html>");
  }

  private static void assertFetchFails(int status, String body) throws Exception {
    Path root = Files.createTempDirectory("spec-fetcher");
    Path spec = root.resolve("apiSpec/prime-public-spec.yaml");
    Files.createDirectories(spec.getParent());
    Files.writeString(spec, COMMITTED);
    HttpServer server = HttpServer.create(new InetSocketAddress("127.0.0.1", 0), 0);
    server.createContext(
        "/openapi.yaml",
        exchange -> {
          byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
          exchange.sendResponseHeaders(status, bytes.length);
          try (OutputStream output = exchange.getResponseBody()) {
            output.write(bytes);
          }
        });
    server.start();
    try {
      String url = "http://127.0.0.1:" + server.getAddress().getPort() + "/openapi.yaml";
      assertThrows(
          Exception.class, () -> SpecFetcher.fetch(root, url, "apiSpec/prime-public-spec.yaml"));
      assertEquals(COMMITTED, Files.readString(spec));
      try (Stream<Path> files = Files.list(spec.getParent())) {
        assertEquals(1, files.count(), "temporary download must be removed");
      }
    } finally {
      server.stop(0);
    }
  }
}
