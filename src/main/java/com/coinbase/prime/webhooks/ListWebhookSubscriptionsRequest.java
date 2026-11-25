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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** List Webhook Subscriptions */
public class ListWebhookSubscriptionsRequest extends PrimeListRequest {
  @JsonProperty(required = true, value = "portfolio_id")
  @JsonIgnore
  private String portfolioId;

  @JsonProperty("include_disabled")
  private Boolean includeDisabled;

  @JsonProperty("event_type_filter")
  private String eventTypeFilter;

  public ListWebhookSubscriptionsRequest() {}

  public ListWebhookSubscriptionsRequest(Builder builder) {
    super(builder.cursor, builder.sortDirection, builder.limit);
    this.portfolioId = builder.portfolioId;
    this.includeDisabled = builder.includeDisabled;
    this.eventTypeFilter = builder.eventTypeFilter;
  }

  public String getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(String portfolioId) {
    this.portfolioId = portfolioId;
  }

  public Boolean getIncludeDisabled() {
    return includeDisabled;
  }

  public void setIncludeDisabled(Boolean includeDisabled) {
    this.includeDisabled = includeDisabled;
  }

  public String getEventTypeFilter() {
    return eventTypeFilter;
  }

  public void setEventTypeFilter(String eventTypeFilter) {
    this.eventTypeFilter = eventTypeFilter;
  }

  public static class Builder {
    private String portfolioId;
    private Boolean includeDisabled;
    private String eventTypeFilter;
    private String cursor;
    private SortDirection sortDirection;
    private Integer limit;

    public Builder() {}

    public Builder portfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
      return this;
    }

    public Builder includeDisabled(Boolean includeDisabled) {
      this.includeDisabled = includeDisabled;
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

    public ListWebhookSubscriptionsRequest build() throws CoinbaseClientException {
      validate();
      return new ListWebhookSubscriptionsRequest(this);
    }

    private void validate() throws CoinbaseClientException {
      if (isNullOrEmpty(this.portfolioId)) {
        throw new CoinbaseClientException("PortfolioId is required");
      }
    }
  }
}
