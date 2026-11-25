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
import com.coinbase.prime.common.Pagination;
import com.coinbase.prime.common.PrimeListRequest;
import com.coinbase.prime.model.enums.SortDirection;
import com.coinbase.prime.model.enums.WebhookDeliveryStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Get Webhook Delivery Logs */
public class GetWebhookDeliveryLogsRequest extends PrimeListRequest {
  @JsonProperty(required = true, value = "portfolio_id")
  @JsonIgnore
  private String portfolioId;

  @JsonProperty(required = true, value = "subscription_id")
  @JsonIgnore
  private String subscriptionId;

  @JsonProperty("status_filter")
  private WebhookDeliveryStatus statusFilter;

  @JsonProperty("event_type_filter")
  private String eventTypeFilter;

  public GetWebhookDeliveryLogsRequest() {}

  public GetWebhookDeliveryLogsRequest(Builder builder) {
    super(builder.cursor, builder.sortDirection, builder.limit);
    this.portfolioId = builder.portfolioId;
    this.subscriptionId = builder.subscriptionId;
    this.statusFilter = builder.statusFilter;
    this.eventTypeFilter = builder.eventTypeFilter;
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

  public WebhookDeliveryStatus getStatusFilter() {
    return statusFilter;
  }

  public void setStatusFilter(WebhookDeliveryStatus statusFilter) {
    this.statusFilter = statusFilter;
  }

  public String getEventTypeFilter() {
    return eventTypeFilter;
  }

  public void setEventTypeFilter(String eventTypeFilter) {
    this.eventTypeFilter = eventTypeFilter;
  }

  public static class Builder {
    private String portfolioId;
    private String subscriptionId;
    private WebhookDeliveryStatus statusFilter;
    private String eventTypeFilter;
    private String cursor;
    private SortDirection sortDirection;
    private Integer limit;

    public Builder() {}

    public Builder portfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
      return this;
    }

    public Builder subscriptionId(String subscriptionId) {
      this.subscriptionId = subscriptionId;
      return this;
    }

    public Builder statusFilter(WebhookDeliveryStatus statusFilter) {
      this.statusFilter = statusFilter;
      return this;
    }

    public Builder eventTypeFilter(String eventTypeFilter) {
      this.eventTypeFilter = eventTypeFilter;
      return this;
    }

    public Builder limit(Integer limit) {
      this.limit = limit;
      return this;
    }

    public Builder pagination(Pagination pagination) {
      this.cursor = pagination.getNextCursor();
      this.sortDirection = pagination.getSortDirection();
      return this;
    }

    public GetWebhookDeliveryLogsRequest build() throws CoinbaseClientException {
      validate();
      return new GetWebhookDeliveryLogsRequest(this);
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
