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

package com.coinbase.prime.products;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.model.enums.CandlesGranularity;

/**
 * Get Public Product Candles
 *
 * @deprecated Use getProductCandles instead.
 */
@Deprecated
public class GetCandlesRequest extends GetProductCandlesRequest {
  public GetCandlesRequest() {}

  public GetCandlesRequest(GetProductCandlesRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use getProductCandles instead.
   */
  @Deprecated
  public static class Builder extends GetProductCandlesRequest.Builder {
    @Override
    public Builder portfolioId(String portfolioId) {
      super.portfolioId(portfolioId);
      return this;
    }

    @Override
    public Builder productId(String productId) {
      super.productId(productId);
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
    public Builder granularity(CandlesGranularity granularity) {
      super.granularity(granularity);
      return this;
    }

    @Override
    public GetCandlesRequest build() throws CoinbaseClientException {
      super.validate();
      return new GetCandlesRequest(this);
    }
  }
}
