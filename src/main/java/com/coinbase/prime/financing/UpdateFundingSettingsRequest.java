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

package com.coinbase.prime.financing;

import com.coinbase.core.errors.CoinbaseClientException;

/**
 * Update Funding Settings
 *
 * @deprecated Use setFundingSettings instead.
 */
@Deprecated
public class UpdateFundingSettingsRequest extends SetFundingSettingsRequest {
  public UpdateFundingSettingsRequest() {}

  public UpdateFundingSettingsRequest(SetFundingSettingsRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use setFundingSettings instead.
   */
  @Deprecated
  public static class Builder extends SetFundingSettingsRequest.Builder {
    @Override
    public Builder entityId(String entityId) {
      super.entityId(entityId);
      return this;
    }

    @Override
    public Builder designatedFundingPortfolioId(String designatedFundingPortfolioId) {
      super.designatedFundingPortfolioId(designatedFundingPortfolioId);
      return this;
    }

    @Override
    public Builder automaticConversionEnabled(Boolean automaticConversionEnabled) {
      super.automaticConversionEnabled(automaticConversionEnabled);
      return this;
    }

    @Override
    public Builder automaticLoanEnabled(Boolean automaticLoanEnabled) {
      super.automaticLoanEnabled(automaticLoanEnabled);
      return this;
    }

    @Override
    public Builder automaticExcessReturnEnabled(Boolean automaticExcessReturnEnabled) {
      super.automaticExcessReturnEnabled(automaticExcessReturnEnabled);
      return this;
    }

    @Override
    public Builder excessFundsTargetAmount(String excessFundsTargetAmount) {
      super.excessFundsTargetAmount(excessFundsTargetAmount);
      return this;
    }

    @Override
    public UpdateFundingSettingsRequest build() throws CoinbaseClientException {
      super.validate();
      return new UpdateFundingSettingsRequest(this);
    }
  }
}
