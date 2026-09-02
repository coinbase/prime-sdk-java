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

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.core.utils.Utils;
import com.coinbase.prime.credentials.CoinbasePrimeCredentials;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Plaintext credentials returned after decrypting a rotate-API-key payload. */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RotatedApiKeyCredentials {
  @JsonProperty("access_key")
  private String accessKey;

  @JsonProperty("secret_key")
  private String secretKey;

  @JsonProperty("passphrase")
  private String passphrase;

  @JsonProperty("service_account_id")
  private String serviceAccountId;

  public RotatedApiKeyCredentials() {}

  public String getAccessKey() {
    return accessKey;
  }

  public void setAccessKey(String accessKey) {
    this.accessKey = accessKey;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public void setSecretKey(String secretKey) {
    this.secretKey = secretKey;
  }

  public String getPassphrase() {
    return passphrase;
  }

  public void setPassphrase(String passphrase) {
    this.passphrase = passphrase;
  }

  public String getServiceAccountId() {
    return serviceAccountId;
  }

  public void setServiceAccountId(String serviceAccountId) {
    this.serviceAccountId = serviceAccountId;
  }

  /**
   * Builds SDK credentials from the rotated key. {@code secret_key} maps to {@code signingKey}.
   *
   * @throws CoinbaseClientException if required fields are missing
   */
  public CoinbasePrimeCredentials toPrimeCredentials() throws CoinbaseClientException {
    if (Utils.isNullOrEmpty(serviceAccountId)) {
      return new CoinbasePrimeCredentials(accessKey, passphrase, secretKey);
    }
    return new CoinbasePrimeCredentials(accessKey, passphrase, secretKey, serviceAccountId);
  }
}
