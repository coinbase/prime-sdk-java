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

import com.coinbase.prime.model.enums.PrimeXMHealthStatus;
import com.coinbase.prime.model.enums.PrimeXMMarginRequirementType;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Cross-margin account summary and nested breakdowns. */
public class CrossMarginPrimeMarginSummary {
  /** Cross Margin Margin Requirement (XMMR) notional. */
  @JsonProperty("margin_requirement")
  private String marginRequirement;

  /**
   * - MARGIN_REQUIREMENT_TYPE_DMR_PLUS_PMR: Integrated (netted) cross-margin requirement for spot
   * assets and all derivatives contracts. - MARGIN_REQUIREMENT_TYPE_IPMR_PLUS_IFMR: Combined
   * cross-margin requirement: Integrated Portfolio Margin (IPMR) plus Ineligible Futures Margin
   * (IFMR).
   */
  @JsonProperty("margin_requirement_type")
  private PrimeXMMarginRequirementType marginRequirementType;

  /** Equity notional. */
  @JsonProperty("account_equity")
  private String accountEquity;

  /** Equity - XMMR (margin excess is > 0). */
  @JsonProperty("margin_excess_shortfall")
  private String marginExcessShortfall;

  /** Credit consumed from Cross Margin Credit Limit (XMCL). */
  @JsonProperty("consumed_credit")
  private String consumedCredit;

  /** XM Credit Limit (XMCL) is the maximum notional USD of total fiat and digital asset loans. */
  @JsonProperty("xm_credit_limit")
  private String xmCreditLimit;

  /** XM Margin Limit (XMML) is the maximum notional USD deficit. */
  @JsonProperty("xm_margin_limit")
  private String xmMarginLimit;

  /** Amount of the XM margin limit consumed by excess deficit. */
  @JsonProperty("consumed_margin_limit")
  private String consumedMarginLimit;

  /** Equity attributed by spot. */
  @JsonProperty("spot_equity")
  private String spotEquity;

  /** Equity attributed by futures. */
  @JsonProperty("futures_equity")
  private String futuresEquity;

  /** Gross market value. */
  @JsonProperty("gross_market_value")
  private String grossMarketValue;

  /** Net market value. */
  @JsonProperty("net_market_value")
  private String netMarketValue;

  /** Net exposure. */
  @JsonProperty("net_exposure")
  private String netExposure;

  /** Gross leverage. */
  @JsonProperty("gross_leverage")
  private String grossLeverage;

  /** Breakdown of the components of spot equity. */
  @JsonProperty("spot_equity_breakdown")
  private CrossMarginPrimeSpotEquityBreakdown spotEquityBreakdown;

  /** Breakdown of the components of derivatives equity. */
  @JsonProperty("derivatives_equity_breakdown")
  private CrossMarginPrimeDerivativesEquityBreakdown derivativesEquityBreakdown;

  /** Groups XM margin requirement components, offset credits, and per-asset rows. */
  @JsonProperty("risk_netting_info")
  private CrossMarginPrimeRiskNettingInfo riskNettingInfo;

  /**
   * - HEALTH_STATUS_HEALTHY: Margin level is healthy. - HEALTH_STATUS_WARNING: Margin level is
   * breaching the warning threshold (WT) which will result in the issuance of a Margin Call if this
   * is still the case by the scheduled next Margin Call (as defined in the margin methodology). WT
   * is differentiated from DT in that it means margin health is approaching the UMCT. -
   * HEALTH_STATUS_CRITICAL: Margin level is breaching the UMCT and, as defined in the margin
   * methodology, this will trigger an urgent margin call. - HEALTH_STATUS_SUSPENDED: Trading and
   * withdrawals are suspended per XM margin methodology. - HEALTH_STATUS_RESTRICTED: Account is in
   * a restricted state per XM margin methodology. - HEALTH_STATUS_PRE_LIQUIDATION: Margin level is
   * breaching the liquidation threshold (LT) and, as defined in the margin methodology, this will
   * trigger the SESSION_LOCKED control status and liquidation may commence. -
   * HEALTH_STATUS_LIQUIDATING: Liquidation has commenced. - HEALTH_STATUS_IN_DEFICIT: Margin level
   * is breaching the deficit threshold (DT) which will result in the issuance of a Margin Call if
   * this is still the case by the scheduled next Margin Call time (as defined in the margin
   * methodology).
   */
  @JsonProperty("health_status")
  private PrimeXMHealthStatus healthStatus;

  /** Equity ratio. */
  @JsonProperty("equity_ratio")
  private String equityRatio;

  /** Deficit ratio. */
  @JsonProperty("deficit_ratio")
  private String deficitRatio;

  @JsonProperty("margin_thresholds")
  private PrimeXMMarginCallThresholds marginThresholds;

  /** FCM excess available to return. */
  @JsonProperty("fcm_excess_available_to_return")
  private String fcmExcessAvailableToReturn;

  public CrossMarginPrimeMarginSummary() {}

  public CrossMarginPrimeMarginSummary(Builder builder) {
    this.marginRequirement = builder.marginRequirement;
    this.marginRequirementType = builder.marginRequirementType;
    this.accountEquity = builder.accountEquity;
    this.marginExcessShortfall = builder.marginExcessShortfall;
    this.consumedCredit = builder.consumedCredit;
    this.xmCreditLimit = builder.xmCreditLimit;
    this.xmMarginLimit = builder.xmMarginLimit;
    this.consumedMarginLimit = builder.consumedMarginLimit;
    this.spotEquity = builder.spotEquity;
    this.futuresEquity = builder.futuresEquity;
    this.grossMarketValue = builder.grossMarketValue;
    this.netMarketValue = builder.netMarketValue;
    this.netExposure = builder.netExposure;
    this.grossLeverage = builder.grossLeverage;
    this.spotEquityBreakdown = builder.spotEquityBreakdown;
    this.derivativesEquityBreakdown = builder.derivativesEquityBreakdown;
    this.riskNettingInfo = builder.riskNettingInfo;
    this.healthStatus = builder.healthStatus;
    this.equityRatio = builder.equityRatio;
    this.deficitRatio = builder.deficitRatio;
    this.marginThresholds = builder.marginThresholds;
    this.fcmExcessAvailableToReturn = builder.fcmExcessAvailableToReturn;
  }

  public String getMarginRequirement() {
    return marginRequirement;
  }

  public void setMarginRequirement(String marginRequirement) {
    this.marginRequirement = marginRequirement;
  }

  public PrimeXMMarginRequirementType getMarginRequirementType() {
    return marginRequirementType;
  }

  public void setMarginRequirementType(PrimeXMMarginRequirementType marginRequirementType) {
    this.marginRequirementType = marginRequirementType;
  }

  public String getAccountEquity() {
    return accountEquity;
  }

  public void setAccountEquity(String accountEquity) {
    this.accountEquity = accountEquity;
  }

  public String getMarginExcessShortfall() {
    return marginExcessShortfall;
  }

  public void setMarginExcessShortfall(String marginExcessShortfall) {
    this.marginExcessShortfall = marginExcessShortfall;
  }

  public String getConsumedCredit() {
    return consumedCredit;
  }

  public void setConsumedCredit(String consumedCredit) {
    this.consumedCredit = consumedCredit;
  }

  public String getXmCreditLimit() {
    return xmCreditLimit;
  }

  public void setXmCreditLimit(String xmCreditLimit) {
    this.xmCreditLimit = xmCreditLimit;
  }

  public String getXmMarginLimit() {
    return xmMarginLimit;
  }

  public void setXmMarginLimit(String xmMarginLimit) {
    this.xmMarginLimit = xmMarginLimit;
  }

  public String getConsumedMarginLimit() {
    return consumedMarginLimit;
  }

  public void setConsumedMarginLimit(String consumedMarginLimit) {
    this.consumedMarginLimit = consumedMarginLimit;
  }

  public String getSpotEquity() {
    return spotEquity;
  }

  public void setSpotEquity(String spotEquity) {
    this.spotEquity = spotEquity;
  }

  public String getFuturesEquity() {
    return futuresEquity;
  }

  public void setFuturesEquity(String futuresEquity) {
    this.futuresEquity = futuresEquity;
  }

  public String getGrossMarketValue() {
    return grossMarketValue;
  }

  public void setGrossMarketValue(String grossMarketValue) {
    this.grossMarketValue = grossMarketValue;
  }

  public String getNetMarketValue() {
    return netMarketValue;
  }

  public void setNetMarketValue(String netMarketValue) {
    this.netMarketValue = netMarketValue;
  }

  public String getNetExposure() {
    return netExposure;
  }

  public void setNetExposure(String netExposure) {
    this.netExposure = netExposure;
  }

  public String getGrossLeverage() {
    return grossLeverage;
  }

  public void setGrossLeverage(String grossLeverage) {
    this.grossLeverage = grossLeverage;
  }

  public CrossMarginPrimeSpotEquityBreakdown getSpotEquityBreakdown() {
    return spotEquityBreakdown;
  }

  public void setSpotEquityBreakdown(CrossMarginPrimeSpotEquityBreakdown spotEquityBreakdown) {
    this.spotEquityBreakdown = spotEquityBreakdown;
  }

  public CrossMarginPrimeDerivativesEquityBreakdown getDerivativesEquityBreakdown() {
    return derivativesEquityBreakdown;
  }

  public void setDerivativesEquityBreakdown(
      CrossMarginPrimeDerivativesEquityBreakdown derivativesEquityBreakdown) {
    this.derivativesEquityBreakdown = derivativesEquityBreakdown;
  }

  public CrossMarginPrimeRiskNettingInfo getRiskNettingInfo() {
    return riskNettingInfo;
  }

  public void setRiskNettingInfo(CrossMarginPrimeRiskNettingInfo riskNettingInfo) {
    this.riskNettingInfo = riskNettingInfo;
  }

  public PrimeXMHealthStatus getHealthStatus() {
    return healthStatus;
  }

  public void setHealthStatus(PrimeXMHealthStatus healthStatus) {
    this.healthStatus = healthStatus;
  }

  public String getEquityRatio() {
    return equityRatio;
  }

  public void setEquityRatio(String equityRatio) {
    this.equityRatio = equityRatio;
  }

  public String getDeficitRatio() {
    return deficitRatio;
  }

  public void setDeficitRatio(String deficitRatio) {
    this.deficitRatio = deficitRatio;
  }

  public PrimeXMMarginCallThresholds getMarginThresholds() {
    return marginThresholds;
  }

  public void setMarginThresholds(PrimeXMMarginCallThresholds marginThresholds) {
    this.marginThresholds = marginThresholds;
  }

  public String getFcmExcessAvailableToReturn() {
    return fcmExcessAvailableToReturn;
  }

  public void setFcmExcessAvailableToReturn(String fcmExcessAvailableToReturn) {
    this.fcmExcessAvailableToReturn = fcmExcessAvailableToReturn;
  }

  public static class Builder {
    private String marginRequirement;

    private PrimeXMMarginRequirementType marginRequirementType;

    private String accountEquity;

    private String marginExcessShortfall;

    private String consumedCredit;

    private String xmCreditLimit;

    private String xmMarginLimit;

    private String consumedMarginLimit;

    private String spotEquity;

    private String futuresEquity;

    private String grossMarketValue;

    private String netMarketValue;

    private String netExposure;

    private String grossLeverage;

    private CrossMarginPrimeSpotEquityBreakdown spotEquityBreakdown;

    private CrossMarginPrimeDerivativesEquityBreakdown derivativesEquityBreakdown;

    private CrossMarginPrimeRiskNettingInfo riskNettingInfo;

    private PrimeXMHealthStatus healthStatus;

    private String equityRatio;

    private String deficitRatio;

    private PrimeXMMarginCallThresholds marginThresholds;

    private String fcmExcessAvailableToReturn;

    public Builder marginRequirement(String marginRequirement) {
      this.marginRequirement = marginRequirement;
      return this;
    }

    public Builder marginRequirementType(PrimeXMMarginRequirementType marginRequirementType) {
      this.marginRequirementType = marginRequirementType;
      return this;
    }

    public Builder accountEquity(String accountEquity) {
      this.accountEquity = accountEquity;
      return this;
    }

    public Builder marginExcessShortfall(String marginExcessShortfall) {
      this.marginExcessShortfall = marginExcessShortfall;
      return this;
    }

    public Builder consumedCredit(String consumedCredit) {
      this.consumedCredit = consumedCredit;
      return this;
    }

    public Builder xmCreditLimit(String xmCreditLimit) {
      this.xmCreditLimit = xmCreditLimit;
      return this;
    }

    public Builder xmMarginLimit(String xmMarginLimit) {
      this.xmMarginLimit = xmMarginLimit;
      return this;
    }

    public Builder consumedMarginLimit(String consumedMarginLimit) {
      this.consumedMarginLimit = consumedMarginLimit;
      return this;
    }

    public Builder spotEquity(String spotEquity) {
      this.spotEquity = spotEquity;
      return this;
    }

    public Builder futuresEquity(String futuresEquity) {
      this.futuresEquity = futuresEquity;
      return this;
    }

    public Builder grossMarketValue(String grossMarketValue) {
      this.grossMarketValue = grossMarketValue;
      return this;
    }

    public Builder netMarketValue(String netMarketValue) {
      this.netMarketValue = netMarketValue;
      return this;
    }

    public Builder netExposure(String netExposure) {
      this.netExposure = netExposure;
      return this;
    }

    public Builder grossLeverage(String grossLeverage) {
      this.grossLeverage = grossLeverage;
      return this;
    }

    public Builder spotEquityBreakdown(CrossMarginPrimeSpotEquityBreakdown spotEquityBreakdown) {
      this.spotEquityBreakdown = spotEquityBreakdown;
      return this;
    }

    public Builder derivativesEquityBreakdown(
        CrossMarginPrimeDerivativesEquityBreakdown derivativesEquityBreakdown) {
      this.derivativesEquityBreakdown = derivativesEquityBreakdown;
      return this;
    }

    public Builder riskNettingInfo(CrossMarginPrimeRiskNettingInfo riskNettingInfo) {
      this.riskNettingInfo = riskNettingInfo;
      return this;
    }

    public Builder healthStatus(PrimeXMHealthStatus healthStatus) {
      this.healthStatus = healthStatus;
      return this;
    }

    public Builder equityRatio(String equityRatio) {
      this.equityRatio = equityRatio;
      return this;
    }

    public Builder deficitRatio(String deficitRatio) {
      this.deficitRatio = deficitRatio;
      return this;
    }

    public Builder marginThresholds(PrimeXMMarginCallThresholds marginThresholds) {
      this.marginThresholds = marginThresholds;
      return this;
    }

    public Builder fcmExcessAvailableToReturn(String fcmExcessAvailableToReturn) {
      this.fcmExcessAvailableToReturn = fcmExcessAvailableToReturn;
      return this;
    }

    public CrossMarginPrimeMarginSummary build() {
      return new CrossMarginPrimeMarginSummary(this);
    }
  }
}
