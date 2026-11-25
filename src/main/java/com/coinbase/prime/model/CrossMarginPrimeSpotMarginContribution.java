/*
 * Copyright 2026-present Coinbase Global, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *  Do not edit the class manually.
 */

package com.coinbase.prime.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Breakdown of a position row's spot equity contribution. All values are zero when no contribution
 * data is available. If the asset class is unknown, the contribution amounts are still returned.
 */
public class CrossMarginPrimeSpotMarginContribution {
  /**
   * Signed spot value in USD after pending withdrawals, before collateral caps and cash-equivalent
   * haircuts.
   */
  @JsonProperty("raw_notional")
  private String rawNotional;

  /** Long notional excluded above the collateral cap. */
  @JsonProperty("ineligible_notional")
  private String ineligibleNotional;

  /** Amount removed by the cash-equivalent long haircut. */
  @JsonProperty("haircut_notional")
  private String haircutNotional;

  /**
   * Final spot equity contribution. Positive values are eligible collateral; negative values are
   * liabilities.
   */
  @JsonProperty("equity_contribution")
  private String equityContribution;

  public CrossMarginPrimeSpotMarginContribution() {}

  public CrossMarginPrimeSpotMarginContribution(Builder builder) {
    this.rawNotional = builder.rawNotional;
    this.ineligibleNotional = builder.ineligibleNotional;
    this.haircutNotional = builder.haircutNotional;
    this.equityContribution = builder.equityContribution;
  }

  public String getRawNotional() {
    return rawNotional;
  }

  public void setRawNotional(String rawNotional) {
    this.rawNotional = rawNotional;
  }

  public String getIneligibleNotional() {
    return ineligibleNotional;
  }

  public void setIneligibleNotional(String ineligibleNotional) {
    this.ineligibleNotional = ineligibleNotional;
  }

  public String getHaircutNotional() {
    return haircutNotional;
  }

  public void setHaircutNotional(String haircutNotional) {
    this.haircutNotional = haircutNotional;
  }

  public String getEquityContribution() {
    return equityContribution;
  }

  public void setEquityContribution(String equityContribution) {
    this.equityContribution = equityContribution;
  }

  public static class Builder {
    private String rawNotional;

    private String ineligibleNotional;

    private String haircutNotional;

    private String equityContribution;

    public Builder rawNotional(String rawNotional) {
      this.rawNotional = rawNotional;
      return this;
    }

    public Builder ineligibleNotional(String ineligibleNotional) {
      this.ineligibleNotional = ineligibleNotional;
      return this;
    }

    public Builder haircutNotional(String haircutNotional) {
      this.haircutNotional = haircutNotional;
      return this;
    }

    public Builder equityContribution(String equityContribution) {
      this.equityContribution = equityContribution;
      return this;
    }

    public CrossMarginPrimeSpotMarginContribution build() {
      return new CrossMarginPrimeSpotMarginContribution(this);
    }
  }
}
