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

import com.coinbase.core.errors.CoinbaseClientException;

/**
 * Get Portfolio Counterparty ID
 *
 * @deprecated Use getCounterpartyId instead.
 */
@Deprecated
public class GetPortfolioCounterpartyIdRequest extends GetCounterpartyIdRequest {
  public GetPortfolioCounterpartyIdRequest() {}

  public GetPortfolioCounterpartyIdRequest(GetCounterpartyIdRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use getCounterpartyId instead.
   */
  @Deprecated
  public static class Builder extends GetCounterpartyIdRequest.Builder {
    @Override
    public Builder portfolioId(String portfolioId) {
      super.portfolioId(portfolioId);
      return this;
    }

    @Override
    public GetPortfolioCounterpartyIdRequest build() throws CoinbaseClientException {
      super.validate();
      return new GetPortfolioCounterpartyIdRequest(this);
    }
  }
}
