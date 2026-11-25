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

/** Get Webhook Subscription Stats */
public class GetWebhookSubscriptionStatsRequest {
  @JsonProperty(required = true, value = "portfolio_id")
  @JsonIgnore
  private String portfolioId;

  @JsonProperty(required = true, value = "subscription_id")
  @JsonIgnore
  private String subscriptionId;

  @JsonProperty("period")
  private String period;

  public GetWebhookSubscriptionStatsRequest() {}

  public GetWebhookSubscriptionStatsRequest(Builder builder) {
    this.portfolioId = builder.portfolioId;
    this.subscriptionId = builder.subscriptionId;
    this.period = builder.period;
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

  public String getPeriod() {
    return period;
  }

  public void setPeriod(String period) {
    this.period = period;
  }

  public static class Builder {
    private String portfolioId;
    private String subscriptionId;
    private String period;

    public Builder() {}

    public Builder portfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
      return this;
    }

    public Builder subscriptionId(String subscriptionId) {
      this.subscriptionId = subscriptionId;
      return this;
    }

    public Builder period(String period) {
      this.period = period;
      return this;
    }

    public GetWebhookSubscriptionStatsRequest build() throws CoinbaseClientException {
      validate();
      return new GetWebhookSubscriptionStatsRequest(this);
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
