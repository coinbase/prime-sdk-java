/*
 * Copyright 2025-present Coinbase Global, Inc.
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

package com.coinbase.prime.orders;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.common.Pagination;
import com.coinbase.prime.model.enums.OrderSide;
import com.coinbase.prime.model.enums.OrderStatus;
import com.coinbase.prime.model.enums.OrderType;

/**
 * List Portfolio Orders
 *
 * @deprecated Use listOrders instead.
 */
@Deprecated
public class ListPortfolioOrdersRequest extends ListOrdersRequest {
  public ListPortfolioOrdersRequest() {}

  public ListPortfolioOrdersRequest(ListOrdersRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use listOrders instead.
   */
  @Deprecated
  public static class Builder extends ListOrdersRequest.Builder {
    @Override
    public Builder portfolioId(String portfolioId) {
      super.portfolioId(portfolioId);
      return this;
    }

    @Override
    public Builder orderStatuses(OrderStatus[] orderStatuses) {
      super.orderStatuses(orderStatuses);
      return this;
    }

    @Override
    public Builder productIds(String[] productIds) {
      super.productIds(productIds);
      return this;
    }

    @Override
    public Builder orderType(OrderType orderType) {
      super.orderType(orderType);
      return this;
    }

    @Override
    public Builder orderSide(OrderSide orderSide) {
      super.orderSide(orderSide);
      return this;
    }

    @Override
    public Builder startDate(String startDate) {
      super.startDate(startDate);
      return this;
    }

    @Override
    public Builder endDate(String endDate) {
      super.endDate(endDate);
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
    public ListPortfolioOrdersRequest build() throws CoinbaseClientException {
      super.validate();
      return new ListPortfolioOrdersRequest(this);
    }
  }
}
