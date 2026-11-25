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

package com.coinbase.prime.advancedtransfer;

import static com.coinbase.core.utils.Utils.isNullOrEmpty;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.model.AdvancedTransfer;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Create Advanced Transfer */
public class CreateAdvancedTransferRequest {
  /** The portfolio ID */
  @JsonProperty(required = true, value = "portfolio_id")
  @JsonIgnore
  private String portfolioId;

  /** AdvancedTransfer represents a complex transfer operation such as a blind match settlement. */
  @JsonProperty("advanced_transfer")
  private AdvancedTransfer advancedTransfer;

  /**
   * Optional client-generated idempotency key. When set, it must be a UUID. A request with the same
   * key returns the stored transfer (id, legs, and state); the request body is not compared. Omit
   * the field to create a new transfer.
   */
  @JsonProperty("idempotency_key")
  private String idempotencyKey;

  public CreateAdvancedTransferRequest() {}

  public CreateAdvancedTransferRequest(Builder builder) {
    this.portfolioId = builder.portfolioId;
    this.advancedTransfer = builder.advancedTransfer;
    this.idempotencyKey = builder.idempotencyKey;
  }

  public String getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(String portfolioId) {
    this.portfolioId = portfolioId;
  }

  public AdvancedTransfer getAdvancedTransfer() {
    return advancedTransfer;
  }

  public void setAdvancedTransfer(AdvancedTransfer advancedTransfer) {
    this.advancedTransfer = advancedTransfer;
  }

  public String getIdempotencyKey() {
    return idempotencyKey;
  }

  public void setIdempotencyKey(String idempotencyKey) {
    this.idempotencyKey = idempotencyKey;
  }

  public static class Builder {
    private String portfolioId;
    private AdvancedTransfer advancedTransfer;
    private String idempotencyKey;

    public Builder() {}

    public Builder portfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
      return this;
    }

    public Builder advancedTransfer(AdvancedTransfer advancedTransfer) {
      this.advancedTransfer = advancedTransfer;
      return this;
    }

    public Builder idempotencyKey(String idempotencyKey) {
      this.idempotencyKey = idempotencyKey;
      return this;
    }

    public CreateAdvancedTransferRequest build() throws CoinbaseClientException {
      validate();
      return new CreateAdvancedTransferRequest(this);
    }

    private void validate() throws CoinbaseClientException {
      if (isNullOrEmpty(this.portfolioId)) {
        throw new CoinbaseClientException("PortfolioId is required");
      }
    }
  }
}
