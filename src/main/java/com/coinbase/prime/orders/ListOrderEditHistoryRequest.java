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

/**
 * List Order Edit History
 *
 * @deprecated Use getOrderEditHistory instead.
 */
@Deprecated
public class ListOrderEditHistoryRequest extends GetOrderEditHistoryRequest {
  public ListOrderEditHistoryRequest() {}

  public ListOrderEditHistoryRequest(GetOrderEditHistoryRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use getOrderEditHistory instead.
   */
  @Deprecated
  public static class Builder extends GetOrderEditHistoryRequest.Builder {
    @Override
    public Builder portfolioId(String portfolioId) {
      super.portfolioId(portfolioId);
      return this;
    }

    @Override
    public Builder orderId(String orderId) {
      super.orderId(orderId);
      return this;
    }

    @Override
    public ListOrderEditHistoryRequest build() throws CoinbaseClientException {
      super.validate();
      return new ListOrderEditHistoryRequest(this);
    }
  }
}
