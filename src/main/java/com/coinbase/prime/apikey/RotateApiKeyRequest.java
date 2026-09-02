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
import com.fasterxml.jackson.annotation.JsonProperty;

/** Rotate API Key */
public class RotateApiKeyRequest {
  /**
   * How long the old key remains active after the new key is approved, in seconds. Set to 0 for
   * immediate expiry on approval. Cannot extend beyond the original key's expiry.
   */
  @JsonProperty("duration_seconds")
  private Long durationSeconds;

  public RotateApiKeyRequest() {}

  public RotateApiKeyRequest(Builder builder) {
    this.durationSeconds = builder.durationSeconds;
  }

  public Long getDurationSeconds() {
    return durationSeconds;
  }

  public void setDurationSeconds(Long durationSeconds) {
    this.durationSeconds = durationSeconds;
  }

  public static class Builder {
    private Long durationSeconds;

    public Builder() {}

    public Builder durationSeconds(Long durationSeconds) {
      this.durationSeconds = durationSeconds;
      return this;
    }

    public RotateApiKeyRequest build() throws CoinbaseClientException {
      return new RotateApiKeyRequest(this);
    }
  }
}
