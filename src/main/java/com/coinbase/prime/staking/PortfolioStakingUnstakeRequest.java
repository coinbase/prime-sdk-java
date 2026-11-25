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
import com.coinbase.prime.model.enums.ValidatorProvider;

/**
 * Request to unstake currency across a portfolio
 *
 * @deprecated Use createPortfolioUnstake instead.
 */
@Deprecated
public class PortfolioStakingUnstakeRequest extends CreatePortfolioUnstakeRequest {
  public PortfolioStakingUnstakeRequest() {}

  public PortfolioStakingUnstakeRequest(CreatePortfolioUnstakeRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use createPortfolioUnstake instead.
   */
  @Deprecated
  public static class Builder extends CreatePortfolioUnstakeRequest.Builder {
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
    public Builder validatorProvider(ValidatorProvider validatorProvider) {
      super.validatorProvider(validatorProvider);
      return this;
    }

    @Override
    public PortfolioStakingUnstakeRequest build() throws CoinbaseClientException {
      super.validate();
      return new PortfolioStakingUnstakeRequest(this);
    }
  }
}
