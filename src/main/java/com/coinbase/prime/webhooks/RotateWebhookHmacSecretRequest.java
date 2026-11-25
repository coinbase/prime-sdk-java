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

package com.coinbase.prime.webhooks;

import static com.coinbase.core.utils.Utils.isNullOrEmpty;

import com.coinbase.core.errors.CoinbaseClientException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Rotate Webhook HMAC Secret */
public class RotateWebhookHmacSecretRequest {
  @JsonProperty(required = true, value = "portfolio_id")
  @JsonIgnore
  private String portfolioId;

  @JsonProperty(required = true, value = "subscription_id")
  @JsonIgnore
  private String subscriptionId;

  /**
   * revoke_previous_immediately invalidates the old secret immediately instead of keeping it valid
   * for the server grace window. Use when the secret may be compromised.
   */
  @JsonProperty("revoke_previous_immediately")
  private Boolean revokePreviousImmediately;

  public RotateWebhookHmacSecretRequest() {}

  public RotateWebhookHmacSecretRequest(Builder builder) {
    this.portfolioId = builder.portfolioId;
    this.subscriptionId = builder.subscriptionId;
    this.revokePreviousImmediately = builder.revokePreviousImmediately;
  }

  public String getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(String portfolioId) {
    this.portfolioId = portfolioId;
  }

  public String getSubscriptionId() {
    return subscriptionId;
  }

  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  public Boolean getRevokePreviousImmediately() {
    return revokePreviousImmediately;
  }

  public void setRevokePreviousImmediately(Boolean revokePreviousImmediately) {
    this.revokePreviousImmediately = revokePreviousImmediately;
  }

  public static class Builder {
    private String portfolioId;
    private String subscriptionId;
    private Boolean revokePreviousImmediately;

    public Builder() {}

    public Builder portfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
      return this;
    }

    public Builder subscriptionId(String subscriptionId) {
      this.subscriptionId = subscriptionId;
      return this;
    }

    public Builder revokePreviousImmediately(Boolean revokePreviousImmediately) {
      this.revokePreviousImmediately = revokePreviousImmediately;
      return this;
    }

    public RotateWebhookHmacSecretRequest build() throws CoinbaseClientException {
      validate();
      return new RotateWebhookHmacSecretRequest(this);
    }

    private void validate() throws CoinbaseClientException {
      if (isNullOrEmpty(this.portfolioId)) {
        throw new CoinbaseClientException("PortfolioId is required");
      }
      if (isNullOrEmpty(this.subscriptionId)) {
        throw new CoinbaseClientException("SubscriptionId is required");
      }
    }
  }
}
