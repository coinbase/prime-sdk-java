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

package com.coinbase.prime.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

import com.coinbase.core.client.CoinbaseNetHttpClient;
import com.coinbase.prime.credentials.CoinbasePrimeCredentials;
import java.lang.reflect.Field;
import java.net.http.HttpClient;
import org.junit.jupiter.api.Test;

class CoinbasePrimeClientTest {
  private static final CoinbasePrimeCredentials CREDENTIALS =
      new CoinbasePrimeCredentials("test-access-key", "test-passphrase", "test-signing-key");

  @Test
  void withBaseUrlReusesInjectedHttpClient() throws Exception {
    HttpClient injected = HttpClient.newHttpClient();
    CoinbasePrimeClient client =
        new CoinbasePrimeClient(CREDENTIALS, "https://example.test/v1", injected);

    CoinbasePrimeClient v2 = client.withBaseUrl("https://example.test/v2");

    assertEquals("https://example.test/v2", v2.getBaseUrl());
    assertSame(injected, transport(v2));
  }

  @Test
  void withBaseUrlKeepsDefaultTransportWhenNoneInjected() {
    CoinbasePrimeClient v2 =
        new CoinbasePrimeClient(CREDENTIALS).withBaseUrl("https://example.test/v2");

    assertEquals("https://example.test/v2", v2.getBaseUrl());
  }

  private static HttpClient transport(CoinbasePrimeClient client) throws Exception {
    Field field = CoinbaseNetHttpClient.class.getDeclaredField("client");
    field.setAccessible(true);
    return (HttpClient) field.get(client);
  }
}
