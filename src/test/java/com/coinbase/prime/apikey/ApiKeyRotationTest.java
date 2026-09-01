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

package com.coinbase.prime.apikey;

import static org.junit.jupiter.api.Assertions.*;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.credentials.CoinbasePrimeCredentials;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.junit.jupiter.api.Test;

public class ApiKeyRotationTest {

  @Test
  public void hkdfSha256MatchesRfc5869TestCase1() throws Exception {
    // RFC 5869 Appendix A.1
    byte[] ikm = hex("0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b0b");
    byte[] salt = hex("000102030405060708090a0b0c");
    byte[] info = hex("f0f1f2f3f4f5f6f7f8f9");
    byte[] okm =
        ApiKeyRotation.hkdfSha256(ikm, salt, info, 42);
    assertArrayEquals(
        hex("3cb25f25faacd57a90434f64d0362f2a2d2d0a90cf1a5a4c5db02d56ecc4c5bf34007208d5b887185865"),
        okm);
  }

  @Test
  public void decryptRoundTrip() throws Exception {
    String secretKey = "current-signing-key";
    String plaintext =
        "{"
            + "\"access_key\":\"new-access\","
            + "\"secret_key\":\"new-secret\","
            + "\"passphrase\":\"new-pass\","
            + "\"service_account_id\":\"svc-1\""
            + "}";
    String encrypted = encrypt(secretKey, plaintext);

    RotateApiKeyResponse response = new RotateApiKeyResponse();
    response.setEncryptedCredentials(encrypted);
    response.setActivityId("activity-1");

    RotatedApiKeyCredentials creds = ApiKeyRotation.decrypt(secretKey, response);
    assertEquals("new-access", creds.getAccessKey());
    assertEquals("new-secret", creds.getSecretKey());
    assertEquals("new-pass", creds.getPassphrase());
    assertEquals("svc-1", creds.getServiceAccountId());

    CoinbasePrimeCredentials prime = creds.toPrimeCredentials();
    assertEquals("new-access", prime.getAccessKey());
    assertEquals("new-pass", prime.getPassphrase());
  }

  @Test
  public void decryptAcceptsMissingBase64Padding() throws Exception {
    String secretKey = "current-signing-key";
    String plaintext =
        "{\"access_key\":\"a\",\"secret_key\":\"s\",\"passphrase\":\"p\"}";
    String encrypted = encrypt(secretKey, plaintext);
    while (encrypted.endsWith("=")) {
      encrypted = encrypted.substring(0, encrypted.length() - 1);
    }

    RotatedApiKeyCredentials creds = ApiKeyRotation.decrypt(secretKey, encrypted);
    assertEquals("a", creds.getAccessKey());
    assertEquals("s", creds.getSecretKey());
    assertEquals("p", creds.getPassphrase());
  }

  @Test
  public void decryptRejectsWrongVersion() {
    byte[] raw = new byte[ApiKeyRotation.MIN_WIRE_LEN];
    raw[0] = 2;
    String payload = Base64.getEncoder().encodeToString(raw);
    CoinbaseClientException ex =
        assertThrows(
            CoinbaseClientException.class, () -> ApiKeyRotation.decrypt("secret", payload));
    assertTrue(ex.getMessage().contains("wire version"));
  }

  @Test
  public void decryptRejectsWrongSecret() throws Exception {
    String encrypted =
        encrypt(
            "correct-secret",
            "{\"access_key\":\"a\",\"secret_key\":\"s\",\"passphrase\":\"p\"}");
    assertThrows(
        CoinbaseClientException.class, () -> ApiKeyRotation.decrypt("wrong-secret", encrypted));
  }

  private static String encrypt(String secretKey, String plaintextJson) throws Exception {
    byte[] salt = new byte[ApiKeyRotation.SALT_LEN];
    byte[] nonce = new byte[ApiKeyRotation.NONCE_LEN];
    SecureRandom random = new SecureRandom();
    random.nextBytes(salt);
    random.nextBytes(nonce);

    byte[] aesKey =
        ApiKeyRotation.hkdfSha256(
            secretKey.getBytes(StandardCharsets.UTF_8),
            salt,
            ApiKeyRotation.HKDF_INFO,
            ApiKeyRotation.AES_KEY_LEN);
    Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
    cipher.init(
        Cipher.ENCRYPT_MODE,
        new SecretKeySpec(aesKey, "AES"),
        new GCMParameterSpec(ApiKeyRotation.GCM_TAG_LEN * 8, nonce));
    byte[] ciphertext = cipher.doFinal(plaintextJson.getBytes(StandardCharsets.UTF_8));

    byte[] raw =
        new byte[ApiKeyRotation.VERSION_LEN + salt.length + nonce.length + ciphertext.length];
    raw[0] = ApiKeyRotation.VERSION;
    System.arraycopy(salt, 0, raw, ApiKeyRotation.VERSION_LEN, salt.length);
    System.arraycopy(nonce, 0, raw, ApiKeyRotation.VERSION_LEN + salt.length, nonce.length);
    System.arraycopy(
        ciphertext,
        0,
        raw,
        ApiKeyRotation.VERSION_LEN + salt.length + nonce.length,
        ciphertext.length);
    return Base64.getEncoder().encodeToString(raw);
  }

  private static byte[] hex(String s) {
    int len = s.length();
    byte[] data = new byte[len / 2];
    for (int i = 0; i < len; i += 2) {
      data[i / 2] =
          (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
    }
    return data;
  }
}
