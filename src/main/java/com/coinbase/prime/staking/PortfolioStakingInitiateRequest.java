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

package com.coinbase.prime.staking;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.model.PortfolioStakingMetadata;

/**
 * Request to stake currency in a portfolio
 *
 * @deprecated Use createPortfolioStake instead.
 */
@Deprecated
public class PortfolioStakingInitiateRequest extends CreatePortfolioStakeRequest {
  public PortfolioStakingInitiateRequest() {}

  public PortfolioStakingInitiateRequest(CreatePortfolioStakeRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use createPortfolioStake instead.
   */
  @Deprecated
  public static class Builder extends CreatePortfolioStakeRequest.Builder {
    @Override
    public Builder portfolioId(String portfolioId) {
      super.portfolioId(portfolioId);
      return this;
    }

    @Override
    public Builder idempotencyKey(String idempotencyKey) {
      super.idempotencyKey(idempotencyKey);
      return this;
    }

    @Override
    public Builder currencySymbol(String currencySymbol) {
      super.currencySymbol(currencySymbol);
      return this;
    }

    @Override
    public Builder amount(String amount) {
      super.amount(amount);
      return this;
    }

    @Override
    public Builder metadata(PortfolioStakingMetadata metadata) {
      super.metadata(metadata);
      return this;
    }

    @Override
    public PortfolioStakingInitiateRequest build() throws CoinbaseClientException {
      super.validate();
      return new PortfolioStakingInitiateRequest(this);
    }
  }
}
