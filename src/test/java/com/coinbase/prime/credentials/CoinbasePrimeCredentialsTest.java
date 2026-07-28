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

package com.coinbase.prime.credentials;

import static org.junit.jupiter.api.Assertions.*;

import com.coinbase.core.credentials.Signer;
import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.utils.Constants;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Map;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CoinbasePrimeCredentialsTest {

  private CoinbasePrimeCredentials credentials;

  @BeforeEach
  public void setUp() throws CoinbaseClientException {
    // Create test credentials for testing
    credentials =
        new CoinbasePrimeCredentials("test-access-key", "test-passphrase", "test-signing-key");
  }

  @Test
  public void testUserAgentHeaderContainsCorrectVersion() throws CoinbaseClientException {
    // User-Agent must match Constants.SDK_VERSION (kept in sync with pom.xml).
    URI testUri = URI.create("https://api.prime.coinbase.com/v1/portfolios");
    Map<String, String> headers = credentials.generateAuthHeaders("GET", testUri, "");

    String userAgent = headers.get(Constants.CB_USER_AGENT_HEADER);
    assertNotNull(userAgent, "User-Agent header should be present");
    assertEquals(
        "prime-sdk-java/" + Constants.SDK_VERSION,
        userAgent,
        "User-Agent header should contain correct SDK version");
  }

  @Test
  public void testUserAgentHeaderIsPresent() throws CoinbaseClientException {
    URI testUri = URI.create("https://api.prime.coinbase.com/v1/portfolios");
    Map<String, String> headers = credentials.generateAuthHeaders("GET", testUri, "");

    assertTrue(
        headers.containsKey(Constants.CB_USER_AGENT_HEADER),
        "User-Agent header should be present in auth headers");
  }

  @Test
  public void testUserAgentHeaderFormat() throws CoinbaseClientException {
    URI testUri = URI.create("https://api.prime.coinbase.com/v1/portfolios");
    Map<String, String> headers = credentials.generateAuthHeaders("GET", testUri, "");

    String userAgent = headers.get(Constants.CB_USER_AGENT_HEADER);
    assertNotNull(userAgent);

    // Verify format: "prime-sdk-java/{version}"
    assertTrue(
        userAgent.startsWith("prime-sdk-java/"), "User-Agent should start with 'prime-sdk-java/'");

    String version = userAgent.substring("prime-sdk-java/".length());
    assertFalse(version.isEmpty(), "Version part should not be empty");

    // Should match semantic versioning pattern
    String versionPattern = "^\\d+\\.\\d+\\.\\d+$";
    assertTrue(version.matches(versionPattern), "Version should follow semantic versioning format");
  }

  @Test
  public void testAllRequiredHeadersArePresent() throws CoinbaseClientException {
    URI testUri = URI.create("https://api.prime.coinbase.com/v1/portfolios");
    Map<String, String> headers = credentials.generateAuthHeaders("GET", testUri, "");

    // Verify all required headers are present
    assertTrue(
        headers.containsKey(Constants.CB_ACCESS_KEY_HEADER), "Access key header should be present");
    assertTrue(
        headers.containsKey(Constants.CB_ACCESS_SIGNATURE_HEADER),
        "Signature header should be present");
    assertTrue(
        headers.containsKey(Constants.CB_ACCESS_TIMESTAMP_HEADER),
        "Timestamp header should be present");
    assertTrue(
        headers.containsKey(Constants.CB_ACCESS_PHRASE_HEADER),
        "Passphrase header should be present");
    assertTrue(
        headers.containsKey(Constants.CB_USER_AGENT_HEADER), "User-Agent header should be present");
  }

  @Test
  public void testDefaultSignatureMatchesIndependentlyComputedHmacSha256() throws Exception {
    URI testUri = URI.create("https://api.prime.coinbase.com/v1/portfolios");
    Map<String, String> headers = credentials.generateAuthHeaders("GET", testUri, "");

    // Recompute the expected signature independently of CoinbasePrimeCredentials#sign, using the
    // exact timestamp it produced, to confirm the real HMAC-SHA256 path (not a stubbed Signer)
    // produces a byte-correct signature for a known key/message.
    String timestamp = headers.get(Constants.CB_ACCESS_TIMESTAMP_HEADER);
    String message = timestamp + "GET" + testUri.getPath();

    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec("test-signing-key".getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
    String expectedSignature =
        Base64.getEncoder().encodeToString(mac.doFinal(message.getBytes(StandardCharsets.UTF_8)));

    assertEquals(expectedSignature, headers.get(Constants.CB_ACCESS_SIGNATURE_HEADER));
  }

  private static final String CREDENTIALS_JSON_WITHOUT_SIGNING_KEY =
      "{\"accessKey\":\"test-access-key\",\"passphrase\":\"test-passphrase\"}";

  @Test
  public void testCustomSignerIsUsedForSignature() throws CoinbaseClientException {
    byte[] fixedSignature = "custom-signature".getBytes(StandardCharsets.UTF_8);
    Signer customSigner = message -> fixedSignature;
    CoinbasePrimeCredentials customCredentials =
        new CoinbasePrimeCredentials(CREDENTIALS_JSON_WITHOUT_SIGNING_KEY, customSigner);

    URI testUri = URI.create("https://api.prime.coinbase.com/v1/portfolios");
    Map<String, String> headers = customCredentials.generateAuthHeaders("GET", testUri, "");

    assertEquals(
        Base64.getEncoder().encodeToString(fixedSignature),
        headers.get(Constants.CB_ACCESS_SIGNATURE_HEADER),
        "Signature header should be the Base64 encoding of the custom Signer's output");
  }

  @Test
  public void testCustomSignerReceivesAssembledMessageBytes() throws CoinbaseClientException {
    java.util.concurrent.atomic.AtomicReference<byte[]> capturedMessage =
        new java.util.concurrent.atomic.AtomicReference<>();
    Signer customSigner =
        message -> {
          capturedMessage.set(message);
          return "irrelevant-signature".getBytes(StandardCharsets.UTF_8);
        };
    CoinbasePrimeCredentials customCredentials =
        new CoinbasePrimeCredentials(CREDENTIALS_JSON_WITHOUT_SIGNING_KEY, customSigner);

    URI testUri = URI.create("https://api.prime.coinbase.com/v1/portfolios");
    Map<String, String> headers = customCredentials.generateAuthHeaders("GET", testUri, "");

    String timestamp = headers.get(Constants.CB_ACCESS_TIMESTAMP_HEADER);
    String expectedMessage = timestamp + "GET" + testUri.getPath();
    assertArrayEquals(expectedMessage.getBytes(StandardCharsets.UTF_8), capturedMessage.get());
  }

  @Test
  public void testJsonConstructorRequiresSignerNonNull() {
    assertThrows(
        CoinbaseClientException.class,
        () -> new CoinbasePrimeCredentials(CREDENTIALS_JSON_WITHOUT_SIGNING_KEY, null));
  }

  @Test
  public void testJsonConstructorWithSignerRequiresAccessKey() {
    Signer customSigner = message -> "custom-signature".getBytes(StandardCharsets.UTF_8);
    assertThrows(
        CoinbaseClientException.class,
        () -> new CoinbasePrimeCredentials("{\"passphrase\":\"test-passphrase\"}", customSigner));
  }

  @Test
  public void testBuilderDoesNotRequireSigningKeyWhenSignerIsSet() throws CoinbaseClientException {
    byte[] fixedSignature = "custom-signature".getBytes(StandardCharsets.UTF_8);
    Signer customSigner = message -> fixedSignature;
    CoinbasePrimeCredentials customCredentials =
        (CoinbasePrimeCredentials)
            new CoinbasePrimeCredentials.Builder()
                .accessKey("test-access-key")
                .passphrase("test-passphrase")
                .signer(customSigner)
                .build();

    URI testUri = URI.create("https://api.prime.coinbase.com/v1/portfolios");
    Map<String, String> headers = customCredentials.generateAuthHeaders("GET", testUri, "");

    assertEquals(
        Base64.getEncoder().encodeToString(fixedSignature),
        headers.get(Constants.CB_ACCESS_SIGNATURE_HEADER));
  }

  @Test
  public void testBuilderStillRequiresSigningKeyWithoutSigner() {
    CoinbaseClientException exception =
        assertThrows(
            CoinbaseClientException.class,
            () ->
                new CoinbasePrimeCredentials.Builder()
                    .accessKey("test-access-key")
                    .passphrase("test-passphrase")
                    .build());
    assertEquals("Signing key is required", exception.getMessage());
  }
}
