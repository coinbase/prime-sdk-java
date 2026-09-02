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

import com.fasterxml.jackson.annotation.JsonProperty;

/** Rotate API Key */
public class RotateApiKeyResponse {
  /**
   * Base64-encoded encrypted payload containing the new API key credentials. Decrypt using your
   * current secret_key with HKDF-SHA256 + AES-256-GCM.
   */
  @JsonProperty("encrypted_credentials")
  private String encryptedCredentials;

  /**
   * The Prime activity ID tracking the consensus approval for this rotation. Use with the
   * Activities endpoints to monitor approval status.
   */
  @JsonProperty("activity_id")
  private String activityId;

  public RotateApiKeyResponse() {}

  public String getEncryptedCredentials() {
    return encryptedCredentials;
  }

  public void setEncryptedCredentials(String encryptedCredentials) {
    this.encryptedCredentials = encryptedCredentials;
  }

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }
}
