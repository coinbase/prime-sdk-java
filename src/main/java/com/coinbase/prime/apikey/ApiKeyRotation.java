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

import static com.coinbase.core.utils.Utils.isNullOrEmpty;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.utils.Utils;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.Mac;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Decrypts {@code encrypted_credentials} from {@link RotateApiKeyResponse} using only the JDK:
 * HKDF-SHA256 (RFC 5869) and AES-256-GCM.
 *
 * <p>Wire format after Base64 decode: {@code version(1) | salt(32) | nonce(12) |
 * ciphertext+tag}. Version must be {@code 0x01}. HKDF info is {@code api-key-rotation}. See <a
 * href="https://docs.cdp.coinbase.com/prime/rest-api/api-key-rotation">API Key Rotation</a>.
 */
public final class ApiKeyRotation {
  static final byte[] HKDF_INFO = "api-key-rotation".getBytes(StandardCharsets.UTF_8);
  static final int VERSION = 1;
  static final int VERSION_LEN = 1;
  static final int SALT_LEN = 32;
  static final int NONCE_LEN = 12;
  static final int GCM_TAG_LEN = 16;
  static final int AES_KEY_LEN = 32;
  static final int MIN_WIRE_LEN = VERSION_LEN + SALT_LEN + NONCE_LEN + GCM_TAG_LEN;
  private static final int HASH_LEN = 32;

  private ApiKeyRotation() {}

  /**
   * Decrypts rotated credentials from a rotate-API-key response.
   *
   * @param secretKey current API signing key ({@code secret_key} / {@code signingKey})
   * @param response rotate API key response containing {@code encrypted_credentials}
   */
  public static RotatedApiKeyCredentials decrypt(String secretKey, RotateApiKeyResponse response)
      throws CoinbaseClientException {
    if (response == null) {
      throw new CoinbaseClientException("Rotate API key response is required");
    }
    return decrypt(secretKey, response.getEncryptedCredentials());
  }

  /**
   * Decrypts a Base64 {@code encrypted_credentials} payload.
   *
   * @param secretKey current API signing key ({@code secret_key} / {@code signingKey})
   * @param encryptedCredentials Base64-encoded wire payload
   */
  public static RotatedApiKeyCredentials decrypt(String secretKey, String encryptedCredentials)
      throws CoinbaseClientException {
    if (isNullOrEmpty(secretKey)) {
      throw new CoinbaseClientException("Secret key is required to decrypt rotated credentials");
    }
    if (isNullOrEmpty(encryptedCredentials)) {
      throw new CoinbaseClientException("encrypted_credentials is required");
    }

    byte[] raw = decodeBase64PadTolerant(encryptedCredentials);
    if (raw.length < MIN_WIRE_LEN) {
      throw new CoinbaseClientException("encrypted_credentials payload is too short");
    }
    if ((raw[0] & 0xFF) != VERSION) {
      throw new CoinbaseClientException(
          "unsupported encrypted_credentials wire version: " + (raw[0] & 0xFF));
    }

    byte[] salt = Arrays.copyOfRange(raw, VERSION_LEN, VERSION_LEN + SALT_LEN);
    byte[] nonce =
        Arrays.copyOfRange(raw, VERSION_LEN + SALT_LEN, VERSION_LEN + SALT_LEN + NONCE_LEN);
    byte[] ciphertext = Arrays.copyOfRange(raw, VERSION_LEN + SALT_LEN + NONCE_LEN, raw.length);

    byte[] ikm = secretKey.getBytes(StandardCharsets.UTF_8);
    byte[] aesKey = null;
    try {
      aesKey = hkdfSha256(ikm, salt, HKDF_INFO, AES_KEY_LEN);
      Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
      cipher.init(
          Cipher.DECRYPT_MODE,
          new SecretKeySpec(aesKey, "AES"),
          new GCMParameterSpec(GCM_TAG_LEN * 8, nonce));
      byte[] plaintext = cipher.doFinal(ciphertext);
      return Utils.getObjectMapper().readValue(plaintext, RotatedApiKeyCredentials.class);
    } catch (CoinbaseClientException e) {
      throw e;
    } catch (Exception e) {
      throw new CoinbaseClientException("Failed to decrypt rotated API key credentials", e);
    } finally {
      Arrays.fill(ikm, (byte) 0);
      if (aesKey != null) {
        Arrays.fill(aesKey, (byte) 0);
      }
    }
  }

  /**
   * HKDF-SHA256 (RFC 5869) extract-then-expand. Package-visible for RFC test vectors.
   */
  static byte[] hkdfSha256(byte[] ikm, byte[] salt, byte[] info, int length)
      throws GeneralSecurityException {
    if (length <= 0 || length > 255 * HASH_LEN) {
      throw new IllegalArgumentException("invalid HKDF output length");
    }
    byte[] prk = hmacSha256(salt != null ? salt : new byte[HASH_LEN], ikm);
    try {
      return hkdfExpand(prk, info, length);
    } finally {
      Arrays.fill(prk, (byte) 0);
    }
  }

  private static byte[] hkdfExpand(byte[] prk, byte[] info, int length)
      throws GeneralSecurityException {
    byte[] infoBytes = info != null ? info : new byte[0];
    int n = (length + HASH_LEN - 1) / HASH_LEN;
    byte[] okm = new byte[length];
    byte[] t = new byte[0];
    int offset = 0;
    for (int i = 1; i <= n; i++) {
      byte[] input = new byte[t.length + infoBytes.length + 1];
      System.arraycopy(t, 0, input, 0, t.length);
      System.arraycopy(infoBytes, 0, input, t.length, infoBytes.length);
      input[input.length - 1] = (byte) i;
      t = hmacSha256(prk, input);
      int toCopy = Math.min(HASH_LEN, length - offset);
      System.arraycopy(t, 0, okm, offset, toCopy);
      offset += toCopy;
    }
    return okm;
  }

  private static byte[] hmacSha256(byte[] key, byte[] data) throws GeneralSecurityException {
    Mac mac = Mac.getInstance("HmacSHA256");
    mac.init(new SecretKeySpec(key, "HmacSHA256"));
    return mac.doFinal(data);
  }

  static byte[] decodeBase64PadTolerant(String encoded) {
    int mod = encoded.length() % 4;
    String padded = mod == 0 ? encoded : encoded + "====".substring(mod);
    return Base64.getDecoder().decode(padded);
  }
}
