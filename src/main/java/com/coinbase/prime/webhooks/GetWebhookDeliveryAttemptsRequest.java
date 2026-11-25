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

/** Get Webhook Delivery Attempts */
public class GetWebhookDeliveryAttemptsRequest {
  @JsonProperty(required = true, value = "portfolio_id")
  @JsonIgnore
  private String portfolioId;

  @JsonProperty(required = true, value = "subscription_id")
  @JsonIgnore
  private String subscriptionId;

  @JsonProperty(required = true, value = "event_id")
  @JsonIgnore
  private String eventId;

  public GetWebhookDeliveryAttemptsRequest() {}

  public GetWebhookDeliveryAttemptsRequest(Builder builder) {
    this.portfolioId = builder.portfolioId;
    this.subscriptionId = builder.subscriptionId;
    this.eventId = builder.eventId;
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

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public static class Builder {
    private String portfolioId;
    private String subscriptionId;
    private String eventId;

    public Builder() {}

    public Builder portfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
      return this;
    }

    public Builder subscriptionId(String subscriptionId) {
      this.subscriptionId = subscriptionId;
      return this;
    }

    public Builder eventId(String eventId) {
      this.eventId = eventId;
      return this;
    }

    public GetWebhookDeliveryAttemptsRequest build() throws CoinbaseClientException {
      validate();
      return new GetWebhookDeliveryAttemptsRequest(this);
    }

    private void validate() throws CoinbaseClientException {
      if (isNullOrEmpty(this.portfolioId)) {
        throw new CoinbaseClientException("PortfolioId is required");
      }
      if (isNullOrEmpty(this.subscriptionId)) {
        throw new CoinbaseClientException("SubscriptionId is required");
      }
      if (isNullOrEmpty(this.eventId)) {
        throw new CoinbaseClientException("EventId is required");
      }
    }
  }
}
