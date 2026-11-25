/*
 * Copyright 2024-present Coinbase Global, Inc.
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

package com.coinbase.prime.activities;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.common.Pagination;
import com.coinbase.prime.model.enums.ActivityCategory;
import com.coinbase.prime.model.enums.ActivityStatus;

/**
 * List Activities
 *
 * @deprecated Use listActivities instead.
 */
@Deprecated
public class ListPortfolioActivitiesRequest extends ListActivitiesRequest {
  public ListPortfolioActivitiesRequest() {}

  public ListPortfolioActivitiesRequest(ListActivitiesRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use listActivities instead.
   */
  @Deprecated
  public static class Builder extends ListActivitiesRequest.Builder {
    @Override
    public Builder portfolioId(String portfolioId) {
      super.portfolioId(portfolioId);
      return this;
    }

    @Override
    public Builder symbols(String[] symbols) {
      super.symbols(symbols);
      return this;
    }

    @Override
    public Builder categories(ActivityCategory[] categories) {
      super.categories(categories);
      return this;
    }

    @Override
    public Builder statuses(ActivityStatus[] statuses) {
      super.statuses(statuses);
      return this;
    }

    @Override
    public Builder startTime(String startTime) {
      super.startTime(startTime);
      return this;
    }

    @Override
    public Builder endTime(String endTime) {
      super.endTime(endTime);
      return this;
    }

    @Override
    public Builder getNetworkUnifiedActivities(Boolean getNetworkUnifiedActivities) {
      super.getNetworkUnifiedActivities(getNetworkUnifiedActivities);
      return this;
    }

    @Override
    public Builder limit(Integer limit) {
      super.limit(limit);
      return this;
    }

    @Override
    public Builder pagination(Pagination pagination) {
      super.pagination(pagination);
      return this;
    }

    @Override
    public ListPortfolioActivitiesRequest build() throws CoinbaseClientException {
      super.validate();
      return new ListPortfolioActivitiesRequest(this);
    }
  }
}
