/*
 * Copyright 2026-present Coinbase Global, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *  Do not edit the class manually.
 */

package com.coinbase.prime.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * WalletStakingMetadata contains optional metadata for wallet staking requests. Optional metadata
 * to attach to this staking or delegation request. Automatic reward crediting (such as SOL
 * inflation) does not produce a taggable transaction and therefore does not support metadata; see
 * the field descriptions below for details.
 */
public class WalletStakingMetadata {
  /**
   * An optional custom identifier (up to 255 bytes) to attach to the transaction. This is not a
   * searchable transaction field. Retries with the same idempotency_key must use the same
   * external_id; a differing value on retry will be silently ignored.
   */
  @JsonProperty("external_id")
  private String externalId;

  public WalletStakingMetadata() {}

  public WalletStakingMetadata(Builder builder) {
    this.externalId = builder.externalId;
  }

  public String getExternalId() {
    return externalId;
  }

  public void setExternalId(String externalId) {
    this.externalId = externalId;
  }

  public static class Builder {
    private String externalId;

    public Builder externalId(String externalId) {
      this.externalId = externalId;
      return this;
    }

    public WalletStakingMetadata build() {
      return new WalletStakingMetadata(this);
    }
  }
}
