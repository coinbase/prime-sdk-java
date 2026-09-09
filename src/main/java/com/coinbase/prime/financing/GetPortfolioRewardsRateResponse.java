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

import com.coinbase.prime.model.BetaRewardsRateTier;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Get Portfolio Rewards Rate (Beta) */
public class GetPortfolioRewardsRateResponse {
  @JsonProperty("current_rate")
  private String currentRate;

  @JsonProperty("available_rates")
  private BetaRewardsRateTier[] availableRates;

  public GetPortfolioRewardsRateResponse() {}

  public String getCurrentRate() {
    return currentRate;
  }

  public void setCurrentRate(String currentRate) {
    this.currentRate = currentRate;
  }

  public BetaRewardsRateTier[] getAvailableRates() {
    return availableRates;
  }

  public void setAvailableRates(BetaRewardsRateTier[] availableRates) {
    this.availableRates = availableRates;
  }
}
