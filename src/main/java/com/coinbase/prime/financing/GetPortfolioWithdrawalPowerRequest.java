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

package com.coinbase.prime.financing;

import static com.coinbase.core.utils.Utils.isNullOrEmpty;

import com.coinbase.core.errors.CoinbaseClientException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Get Portfolio Withdrawal Power */
public class GetPortfolioWithdrawalPowerRequest {
  /** The unique ID of the portfolio */
  @JsonProperty(required = true, value = "portfolio_id")
  @JsonIgnore
  private String portfolioId;

  /** The currency symbol */
  @JsonProperty("symbol")
  private String symbol;

  public GetPortfolioWithdrawalPowerRequest() {}

  public GetPortfolioWithdrawalPowerRequest(Builder builder) {
    this.portfolioId = builder.portfolioId;
    this.symbol = builder.symbol;
  }

  public String getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(String portfolioId) {
    this.portfolioId = portfolioId;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public static class Builder {
    private String portfolioId;
    private String symbol;

    public Builder() {}

    public Builder portfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
      return this;
    }

    public Builder symbol(String symbol) {
      this.symbol = symbol;
      return this;
    }

    public GetPortfolioWithdrawalPowerRequest build() throws CoinbaseClientException {
      validate();
      return new GetPortfolioWithdrawalPowerRequest(this);
    }

    private void validate() throws CoinbaseClientException {
      if (isNullOrEmpty(this.portfolioId)) {
        throw new CoinbaseClientException("PortfolioId is required");
      }
    }
  }
}
