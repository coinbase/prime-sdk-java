/*
 * Copyright 2025-present Coinbase Global, Inc.
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

import com.coinbase.prime.model.enums.MarginControlStatus;
import com.coinbase.prime.model.enums.MarginEntityCallStatus;
import com.coinbase.prime.model.enums.MarginHealthStatus;
import com.coinbase.prime.model.enums.MarginLevel;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MarginSummary {
  /** The unique ID of the entity */
  @JsonProperty("entity_id")
  private String entityId;

  /**
   * The margin equity at the entity level. Margin Equity &#x3D; LMV + SMV + Trading Cash Balance +
   * Short Collateral - Pending Withdrawals
   */
  @JsonProperty("margin_equity")
  private String marginEquity;

  /** USD notional value of required equity in entity portfolios */
  @JsonProperty("margin_requirement")
  private String marginRequirement;

  /** margin_equity - margin_requirement */
  @JsonProperty("excess_deficit")
  private String excessDeficit;

  /** The raw amount of portfolio margin credit used */
  @JsonProperty("pm_credit_consumed")
  private String pmCreditConsumed;

  /**
   * The maximum trade finance credit limit. This field is deprecated and will be removed in the
   * future.
   */
  @JsonProperty("tf_credit_limit")
  private String tfCreditLimit;

  /**
   * The amount of trade finance credit used (USD). This field is deprecated and will be removed in
   * the future.
   */
  @JsonProperty("tf_credit_consumed")
  private String tfCreditConsumed;

  /** TF Asset Adjusted Value (USD). This field is deprecated and will be removed in the future. */
  @JsonProperty("tf_adjusted_asset_value")
  private String tfAdjustedAssetValue;

  /**
   * TF Adjusted Liability Value (USD). This field is deprecated and will be removed in the future.
   */
  @JsonProperty("tf_adjusted_liability_value")
  private String tfAdjustedLiabilityValue;

  /**
   * The amount of adjusted credit used. This field is deprecated and will be removed in the future.
   */
  @JsonProperty("tf_adjusted_credit_consumed")
  private String tfAdjustedCreditConsumed;

  /** The amount of adjusted equity. This field is deprecated and will be removed in the future. */
  @JsonProperty("tf_adjusted_equity")
  private String tfAdjustedEquity;

  /** Whether or not a entity is frozen due to balance outstanding or other reason */
  private boolean frozen;

  /** The reason why a entity is frozen */
  @JsonProperty("frozen_reason")
  private String frozenReason;

  /**
   * Whether TF is enabled for the entity. This field is deprecated and will be removed in the
   * future.
   */
  @JsonProperty("tf_enabled")
  private boolean tfEnabled;

  /** Whether PM is enabled for the entity */
  @JsonProperty("pm_enabled")
  private boolean pmEnabled;

  /** Market rates for the list of assets */
  @JsonProperty("market_rates")
  private List<MarketRate> marketRates;

  /** Asset Balances across portfolios */
  @JsonProperty("asset_balances")
  private List<AssetBalance> assetBalances;

  /**
   * Trade finance debit loan amounts. This field is deprecated and will be removed in the future.
   */
  @JsonProperty("tf_loans")
  private List<LoanInfo> tfLoans;

  /** Portfolio Margin debit loan amounts */
  @JsonProperty("pm_loans")
  private List<LoanInfo> pmLoans;

  /** Short collateral amounts */
  @JsonProperty("short_collateral")
  private List<LoanInfo> shortCollateral;

  /** Gross market value (GMV) &#x3D; LMV + Abs (SMV) */
  @JsonProperty("gross_market_value")
  private String grossMarketValue;

  /** Net Market Value (NMV) &#x3D; LMV + SMV */
  @JsonProperty("net_market_value")
  private String netMarketValue;

  /** Long Market Value (LMV) &#x3D; Sum of positive notional for all assets */
  @JsonProperty("long_market_value")
  private String longMarketValue;

  /** Non_Marginable LMV: Sum of positive notional for each non-margin eligible coin */
  @JsonProperty("non_marginable_long_market_value")
  private String nonMarginableLongMarketValue;

  /** Short Market Value (SMV) &#x3D; Sum of negative notional for each margin eligible coin */
  @JsonProperty("short_market_value")
  private String shortMarketValue;

  /** Gross Leverage &#x3D; GMV / Margin Requirement */
  @JsonProperty("gross_leverage")
  private String grossLeverage;

  /** Net Exposure &#x3D; (LMV + SMV) / GMV */
  @JsonProperty("net_exposure")
  private String netExposure;

  @JsonProperty("portfolio_stress_triggered")
  private MarginAddOn portfolioStressTriggered;

  /** PM asset info netted across the entity */
  @JsonProperty("pm_asset_info")
  private List<PmAssetInfo> pmAssetInfo;

  /** PM limit that monitors gross notional borrowings (crypto + fiat) */
  @JsonProperty("pm_credit_limit")
  private String pmCreditLimit;

  /** PM limit that monitors excess deficit */
  @JsonProperty("pm_margin_limit")
  private String pmMarginLimit;

  /** The amount of the margin limit that is consumed by the excess deficit */
  @JsonProperty("pm_margin_consumed")
  private String pmMarginConsumed;

  /**
   * Signed cash adjustment associated with the short collateral balance in a historical margin
   * summary. Negative amounts move cash into short collateral; positive amounts return cash from
   * short collateral. Add this signed amount to the snapshot's cash balance to calculate cash after
   * the transfer. Live margin responses return an empty string for this field. For example, a cash
   * balance of 60000000 and a short collateral transfer of -20000000 produce a cash balance of
   * 40000000 after the transfer. This calculation doesn't determine the amount available to
   * withdraw.
   */
  @JsonProperty("short_collateral_transfer")
  private String shortCollateralTransfer;

  /**
   * Recorded account health state maintained by the margin-management process. Persistence rules
   * filter out short-lived threshold changes. Margin-call and liquidation events also affect this
   * state. A change in the calculated `margin_level` may not immediately change this status. Use
   * `control_status` for trading and withdrawal permissions in live responses. Historical responses
   * contain the recorded health state at the historical evaluation. -
   * MARGIN_HEALTH_STATUS_UNSPECIFIED: No account health state is provided. Don't interpret this
   * value as healthy or as permission to trade or withdraw. - MARGIN_HEALTH_STATUS_HEALTHY: The
   * account's recorded health state is healthy. - MARGIN_HEALTH_STATUS_WARNING: The account's
   * recorded health state is warning. Margin-call issuance follows the applicable margin
   * methodology. - MARGIN_HEALTH_STATUS_CRITICAL: The account's recorded health state is critical,
   * corresponding to an urgent margin-call condition under the applicable margin methodology. -
   * MARGIN_HEALTH_STATUS_SUSPENDED: The account is recorded as suspended under the applicable
   * margin methodology. - MARGIN_HEALTH_STATUS_RESTRICTED: The account is recorded as restricted
   * under the applicable margin methodology. - MARGIN_HEALTH_STATUS_PRE_LIQUIDATION: The account is
   * in a pre-liquidation state. Liquidation may commence under the applicable margin methodology. -
   * MARGIN_HEALTH_STATUS_LIQUIDATING: Liquidation has commenced. - MARGIN_HEALTH_STATUS_IN_DEFICIT:
   * The account's recorded health state is in deficit. Margin-call issuance follows the applicable
   * margin methodology.
   */
  @JsonProperty("health_status")
  private MarginHealthStatus healthStatus;

  /**
   * Calculated margin level based on the account's equity, margin requirement, margin limit, and
   * configured thresholds. This reflects the current calculation for live responses or the recorded
   * calculation for historical responses. It can differ from `health_status`, which is maintained
   * by the margin-management process. The level alone doesn't determine whether a margin call is
   * open or whether trading and withdrawals are allowed. - MARGIN_LEVEL_UNSPECIFIED: No calculated
   * margin level is provided. - MARGIN_LEVEL_HEALTHY: Account equity is at or above the margin
   * requirement. - MARGIN_LEVEL_DEFICIT: The calculation places the account in the deficit band.
   * Margin-call issuance follows the applicable margin methodology. - MARGIN_LEVEL_WARNING: The
   * calculation places the account in the warning band, approaching the critical threshold. -
   * MARGIN_LEVEL_CRITICAL: The calculation places the account in the critical band. -
   * MARGIN_LEVEL_LIQUIDATION: The calculation places the account in the liquidation band. This
   * value alone doesn't indicate that liquidation has commenced.
   */
  @JsonProperty("margin_level")
  private MarginLevel marginLevel;

  /**
   * Configured thresholds used to determine `margin_level`. Each entry identifies a margin level,
   * the ratio used to evaluate it, and its threshold value. Historical responses contain the
   * thresholds recorded with the historical health evaluation. Equity at or above the margin
   * requirement produces a healthy margin level. Otherwise, the evaluator selects the first
   * matching rule in the configured order. A threshold comparison identifies a matching level; it
   * doesn't by itself issue a margin call or change account permissions.
   */
  @JsonProperty("margin_thresholds")
  private MarginCallThresholds marginThresholds;

  /**
   * Trading and withdrawal permissions derived from the account's recorded health state,
   * outstanding margin calls, and account restrictions. These permissions can remain restricted
   * even when the calculated `margin_level` improves. Returns `MARGIN_CONTROL_STATUS_UNSPECIFIED`
   * when the margin service doesn't provide a control status. - MARGIN_CONTROL_STATUS_UNSPECIFIED:
   * No control status is provided. Don't infer permission to trade or withdraw from this value. -
   * MARGIN_CONTROL_STATUS_TRADES_AND_WITHDRAWALS: Allowed to trade and withdraw under the
   * applicable margin methodology. - MARGIN_CONTROL_STATUS_TRADES_ONLY: Allowed to trade but not
   * withdraw under the applicable margin methodology. - MARGIN_CONTROL_STATUS_SESSION_LOCKED: Not
   * allowed to trade or withdraw.
   */
  @JsonProperty("control_status")
  private MarginControlStatus controlStatus;

  /**
   * Account equity divided by margin requirement, expressed as a decimal ratio. For example,
   * \"1.2\" represents equity equal to 120% of the margin requirement. Returns an empty string when
   * either input is unavailable or the margin requirement is zero.
   */
  @JsonProperty("equity_ratio")
  private String equityRatio;

  /**
   * Margin deficit divided by the account's margin limit, calculated as `max(0, (margin requirement
   * - account equity) / pm_margin_limit)`. Expressed as a decimal ratio, not a percentage. Returns
   * an empty string when an input is unavailable or the margin limit is zero.
   */
  @JsonProperty("deficit_ratio")
  private String deficitRatio;

  /**
   * MarginEntityCallStatus summarizes the entity's open margin calls. When several calls are open,
   * it reflects the highest-priority one: aged, then urgent, then standard, then debit.
   * `call_status` describes outstanding calls. Use `margin_level` for calculated margin condition
   * and `control_status` for trading and withdrawal permissions. -
   * MARGIN_ENTITY_CALL_STATUS_UNSPECIFIED: No call status is provided. Don't infer that the entity
   * has no margin calls from this value. - MARGIN_ENTITY_CALL_STATUS_NO_CALL: The entity has no
   * open margin calls. - MARGIN_ENTITY_CALL_STATUS_OPEN_STANDARD_CALL: The entity has an open
   * standard margin call and no urgent or aged calls. - MARGIN_ENTITY_CALL_STATUS_OPEN_URGENT_CALL:
   * The entity has an open urgent margin call and no aged calls. -
   * MARGIN_ENTITY_CALL_STATUS_AGED_CALL: At least one open margin call is past its due time. This
   * status alone does not indicate a contractual Event of Default or that liquidation has begun. -
   * MARGIN_ENTITY_CALL_STATUS_OPEN_DEBIT_CALL: The entity has an open debit call and no standard,
   * urgent, or aged calls.
   */
  @JsonProperty("call_status")
  private MarginEntityCallStatus callStatus;

  public MarginSummary() {}

  public MarginSummary(Builder builder) {
    this.entityId = builder.entityId;
    this.marginEquity = builder.marginEquity;
    this.marginRequirement = builder.marginRequirement;
    this.excessDeficit = builder.excessDeficit;
    this.pmCreditConsumed = builder.pmCreditConsumed;
    this.tfCreditLimit = builder.tfCreditLimit;
    this.tfCreditConsumed = builder.tfCreditConsumed;
    this.tfAdjustedAssetValue = builder.tfAdjustedAssetValue;
    this.tfAdjustedLiabilityValue = builder.tfAdjustedLiabilityValue;
    this.tfAdjustedCreditConsumed = builder.tfAdjustedCreditConsumed;
    this.tfAdjustedEquity = builder.tfAdjustedEquity;
    this.frozen = builder.frozen;
    this.frozenReason = builder.frozenReason;
    this.tfEnabled = builder.tfEnabled;
    this.pmEnabled = builder.pmEnabled;
    this.marketRates = builder.marketRates;
    this.assetBalances = builder.assetBalances;
    this.tfLoans = builder.tfLoans;
    this.pmLoans = builder.pmLoans;
    this.shortCollateral = builder.shortCollateral;
    this.grossMarketValue = builder.grossMarketValue;
    this.netMarketValue = builder.netMarketValue;
    this.longMarketValue = builder.longMarketValue;
    this.nonMarginableLongMarketValue = builder.nonMarginableLongMarketValue;
    this.shortMarketValue = builder.shortMarketValue;
    this.grossLeverage = builder.grossLeverage;
    this.netExposure = builder.netExposure;
    this.portfolioStressTriggered = builder.portfolioStressTriggered;
    this.pmAssetInfo = builder.pmAssetInfo;
    this.pmCreditLimit = builder.pmCreditLimit;
    this.pmMarginLimit = builder.pmMarginLimit;
    this.pmMarginConsumed = builder.pmMarginConsumed;
    this.shortCollateralTransfer = builder.shortCollateralTransfer;
    this.healthStatus = builder.healthStatus;
    this.marginLevel = builder.marginLevel;
    this.marginThresholds = builder.marginThresholds;
    this.controlStatus = builder.controlStatus;
    this.equityRatio = builder.equityRatio;
    this.deficitRatio = builder.deficitRatio;
    this.callStatus = builder.callStatus;
  }

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public String getMarginEquity() {
    return marginEquity;
  }

  public void setMarginEquity(String marginEquity) {
    this.marginEquity = marginEquity;
  }

  public String getMarginRequirement() {
    return marginRequirement;
  }

  public void setMarginRequirement(String marginRequirement) {
    this.marginRequirement = marginRequirement;
  }

  public String getExcessDeficit() {
    return excessDeficit;
  }

  public void setExcessDeficit(String excessDeficit) {
    this.excessDeficit = excessDeficit;
  }

  public String getPmCreditConsumed() {
    return pmCreditConsumed;
  }

  public void setPmCreditConsumed(String pmCreditConsumed) {
    this.pmCreditConsumed = pmCreditConsumed;
  }

  public String getTfCreditLimit() {
    return tfCreditLimit;
  }

  public void setTfCreditLimit(String tfCreditLimit) {
    this.tfCreditLimit = tfCreditLimit;
  }

  public String getTfCreditConsumed() {
    return tfCreditConsumed;
  }

  public void setTfCreditConsumed(String tfCreditConsumed) {
    this.tfCreditConsumed = tfCreditConsumed;
  }

  public String getTfAdjustedAssetValue() {
    return tfAdjustedAssetValue;
  }

  public void setTfAdjustedAssetValue(String tfAdjustedAssetValue) {
    this.tfAdjustedAssetValue = tfAdjustedAssetValue;
  }

  public String getTfAdjustedLiabilityValue() {
    return tfAdjustedLiabilityValue;
  }

  public void setTfAdjustedLiabilityValue(String tfAdjustedLiabilityValue) {
    this.tfAdjustedLiabilityValue = tfAdjustedLiabilityValue;
  }

  public String getTfAdjustedCreditConsumed() {
    return tfAdjustedCreditConsumed;
  }

  public void setTfAdjustedCreditConsumed(String tfAdjustedCreditConsumed) {
    this.tfAdjustedCreditConsumed = tfAdjustedCreditConsumed;
  }

  public String getTfAdjustedEquity() {
    return tfAdjustedEquity;
  }

  public void setTfAdjustedEquity(String tfAdjustedEquity) {
    this.tfAdjustedEquity = tfAdjustedEquity;
  }

  public boolean getFrozen() {
    return frozen;
  }

  public void setFrozen(boolean frozen) {
    this.frozen = frozen;
  }

  public String getFrozenReason() {
    return frozenReason;
  }

  public void setFrozenReason(String frozenReason) {
    this.frozenReason = frozenReason;
  }

  public boolean getTfEnabled() {
    return tfEnabled;
  }

  public void setTfEnabled(boolean tfEnabled) {
    this.tfEnabled = tfEnabled;
  }

  public boolean getPmEnabled() {
    return pmEnabled;
  }

  public void setPmEnabled(boolean pmEnabled) {
    this.pmEnabled = pmEnabled;
  }

  public List<MarketRate> getMarketRates() {
    return marketRates;
  }

  public void setMarketRates(List<MarketRate> marketRates) {
    this.marketRates = marketRates;
  }

  public List<AssetBalance> getAssetBalances() {
    return assetBalances;
  }

  public void setAssetBalances(List<AssetBalance> assetBalances) {
    this.assetBalances = assetBalances;
  }

  public List<LoanInfo> getTfLoans() {
    return tfLoans;
  }

  public void setTfLoans(List<LoanInfo> tfLoans) {
    this.tfLoans = tfLoans;
  }

  public List<LoanInfo> getPmLoans() {
    return pmLoans;
  }

  public void setPmLoans(List<LoanInfo> pmLoans) {
    this.pmLoans = pmLoans;
  }

  public List<LoanInfo> getShortCollateral() {
    return shortCollateral;
  }

  public void setShortCollateral(List<LoanInfo> shortCollateral) {
    this.shortCollateral = shortCollateral;
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

  public String getLongMarketValue() {
    return longMarketValue;
  }

  public void setLongMarketValue(String longMarketValue) {
    this.longMarketValue = longMarketValue;
  }

  public String getNonMarginableLongMarketValue() {
    return nonMarginableLongMarketValue;
  }

  public void setNonMarginableLongMarketValue(String nonMarginableLongMarketValue) {
    this.nonMarginableLongMarketValue = nonMarginableLongMarketValue;
  }

  public String getShortMarketValue() {
    return shortMarketValue;
  }

  public void setShortMarketValue(String shortMarketValue) {
    this.shortMarketValue = shortMarketValue;
  }

  public String getGrossLeverage() {
    return grossLeverage;
  }

  public void setGrossLeverage(String grossLeverage) {
    this.grossLeverage = grossLeverage;
  }

  public String getNetExposure() {
    return netExposure;
  }

  public void setNetExposure(String netExposure) {
    this.netExposure = netExposure;
  }

  public MarginAddOn getPortfolioStressTriggered() {
    return portfolioStressTriggered;
  }

  public void setPortfolioStressTriggered(MarginAddOn portfolioStressTriggered) {
    this.portfolioStressTriggered = portfolioStressTriggered;
  }

  public List<PmAssetInfo> getPmAssetInfo() {
    return pmAssetInfo;
  }

  public void setPmAssetInfo(List<PmAssetInfo> pmAssetInfo) {
    this.pmAssetInfo = pmAssetInfo;
  }

  public String getPmCreditLimit() {
    return pmCreditLimit;
  }

  public void setPmCreditLimit(String pmCreditLimit) {
    this.pmCreditLimit = pmCreditLimit;
  }

  public String getPmMarginLimit() {
    return pmMarginLimit;
  }

  public void setPmMarginLimit(String pmMarginLimit) {
    this.pmMarginLimit = pmMarginLimit;
  }

  public String getPmMarginConsumed() {
    return pmMarginConsumed;
  }

  public void setPmMarginConsumed(String pmMarginConsumed) {
    this.pmMarginConsumed = pmMarginConsumed;
  }

  public String getShortCollateralTransfer() {
    return shortCollateralTransfer;
  }

  public void setShortCollateralTransfer(String shortCollateralTransfer) {
    this.shortCollateralTransfer = shortCollateralTransfer;
  }

  public MarginHealthStatus getHealthStatus() {
    return healthStatus;
  }

  public void setHealthStatus(MarginHealthStatus healthStatus) {
    this.healthStatus = healthStatus;
  }

  public MarginLevel getMarginLevel() {
    return marginLevel;
  }

  public void setMarginLevel(MarginLevel marginLevel) {
    this.marginLevel = marginLevel;
  }

  public MarginCallThresholds getMarginThresholds() {
    return marginThresholds;
  }

  public void setMarginThresholds(MarginCallThresholds marginThresholds) {
    this.marginThresholds = marginThresholds;
  }

  public MarginControlStatus getControlStatus() {
    return controlStatus;
  }

  public void setControlStatus(MarginControlStatus controlStatus) {
    this.controlStatus = controlStatus;
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

  public MarginEntityCallStatus getCallStatus() {
    return callStatus;
  }

  public void setCallStatus(MarginEntityCallStatus callStatus) {
    this.callStatus = callStatus;
  }

  public static class Builder {
    private String entityId;

    private String marginEquity;

    private String marginRequirement;

    private String excessDeficit;

    private String pmCreditConsumed;

    private String tfCreditLimit;

    private String tfCreditConsumed;

    private String tfAdjustedAssetValue;

    private String tfAdjustedLiabilityValue;

    private String tfAdjustedCreditConsumed;

    private String tfAdjustedEquity;

    private boolean frozen;

    private String frozenReason;

    private boolean tfEnabled;

    private boolean pmEnabled;

    private List<MarketRate> marketRates;

    private List<AssetBalance> assetBalances;

    private List<LoanInfo> tfLoans;

    private List<LoanInfo> pmLoans;

    private List<LoanInfo> shortCollateral;

    private String grossMarketValue;

    private String netMarketValue;

    private String longMarketValue;

    private String nonMarginableLongMarketValue;

    private String shortMarketValue;

    private String grossLeverage;

    private String netExposure;

    private MarginAddOn portfolioStressTriggered;

    private List<PmAssetInfo> pmAssetInfo;

    private String pmCreditLimit;

    private String pmMarginLimit;

    private String pmMarginConsumed;

    private String shortCollateralTransfer;

    private MarginHealthStatus healthStatus;

    private MarginLevel marginLevel;

    private MarginCallThresholds marginThresholds;

    private MarginControlStatus controlStatus;

    private String equityRatio;

    private String deficitRatio;

    private MarginEntityCallStatus callStatus;

    public Builder entityId(String entityId) {
      this.entityId = entityId;
      return this;
    }

    public Builder marginEquity(String marginEquity) {
      this.marginEquity = marginEquity;
      return this;
    }

    public Builder marginRequirement(String marginRequirement) {
      this.marginRequirement = marginRequirement;
      return this;
    }

    public Builder excessDeficit(String excessDeficit) {
      this.excessDeficit = excessDeficit;
      return this;
    }

    public Builder pmCreditConsumed(String pmCreditConsumed) {
      this.pmCreditConsumed = pmCreditConsumed;
      return this;
    }

    public Builder tfCreditLimit(String tfCreditLimit) {
      this.tfCreditLimit = tfCreditLimit;
      return this;
    }

    public Builder tfCreditConsumed(String tfCreditConsumed) {
      this.tfCreditConsumed = tfCreditConsumed;
      return this;
    }

    public Builder tfAdjustedAssetValue(String tfAdjustedAssetValue) {
      this.tfAdjustedAssetValue = tfAdjustedAssetValue;
      return this;
    }

    public Builder tfAdjustedLiabilityValue(String tfAdjustedLiabilityValue) {
      this.tfAdjustedLiabilityValue = tfAdjustedLiabilityValue;
      return this;
    }

    public Builder tfAdjustedCreditConsumed(String tfAdjustedCreditConsumed) {
      this.tfAdjustedCreditConsumed = tfAdjustedCreditConsumed;
      return this;
    }

    public Builder tfAdjustedEquity(String tfAdjustedEquity) {
      this.tfAdjustedEquity = tfAdjustedEquity;
      return this;
    }

    public Builder frozen(boolean frozen) {
      this.frozen = frozen;
      return this;
    }

    public Builder frozenReason(String frozenReason) {
      this.frozenReason = frozenReason;
      return this;
    }

    public Builder tfEnabled(boolean tfEnabled) {
      this.tfEnabled = tfEnabled;
      return this;
    }

    public Builder pmEnabled(boolean pmEnabled) {
      this.pmEnabled = pmEnabled;
      return this;
    }

    public Builder marketRates(List<MarketRate> marketRates) {
      this.marketRates = marketRates;
      return this;
    }

    public Builder assetBalances(List<AssetBalance> assetBalances) {
      this.assetBalances = assetBalances;
      return this;
    }

    public Builder tfLoans(List<LoanInfo> tfLoans) {
      this.tfLoans = tfLoans;
      return this;
    }

    public Builder pmLoans(List<LoanInfo> pmLoans) {
      this.pmLoans = pmLoans;
      return this;
    }

    public Builder shortCollateral(List<LoanInfo> shortCollateral) {
      this.shortCollateral = shortCollateral;
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

    public Builder longMarketValue(String longMarketValue) {
      this.longMarketValue = longMarketValue;
      return this;
    }

    public Builder nonMarginableLongMarketValue(String nonMarginableLongMarketValue) {
      this.nonMarginableLongMarketValue = nonMarginableLongMarketValue;
      return this;
    }

    public Builder shortMarketValue(String shortMarketValue) {
      this.shortMarketValue = shortMarketValue;
      return this;
    }

    public Builder grossLeverage(String grossLeverage) {
      this.grossLeverage = grossLeverage;
      return this;
    }

    public Builder netExposure(String netExposure) {
      this.netExposure = netExposure;
      return this;
    }

    public Builder portfolioStressTriggered(MarginAddOn portfolioStressTriggered) {
      this.portfolioStressTriggered = portfolioStressTriggered;
      return this;
    }

    public Builder pmAssetInfo(List<PmAssetInfo> pmAssetInfo) {
      this.pmAssetInfo = pmAssetInfo;
      return this;
    }

    public Builder pmCreditLimit(String pmCreditLimit) {
      this.pmCreditLimit = pmCreditLimit;
      return this;
    }

    public Builder pmMarginLimit(String pmMarginLimit) {
      this.pmMarginLimit = pmMarginLimit;
      return this;
    }

    public Builder pmMarginConsumed(String pmMarginConsumed) {
      this.pmMarginConsumed = pmMarginConsumed;
      return this;
    }

    public Builder shortCollateralTransfer(String shortCollateralTransfer) {
      this.shortCollateralTransfer = shortCollateralTransfer;
      return this;
    }

    public Builder healthStatus(MarginHealthStatus healthStatus) {
      this.healthStatus = healthStatus;
      return this;
    }

    public Builder marginLevel(MarginLevel marginLevel) {
      this.marginLevel = marginLevel;
      return this;
    }

    public Builder marginThresholds(MarginCallThresholds marginThresholds) {
      this.marginThresholds = marginThresholds;
      return this;
    }

    public Builder controlStatus(MarginControlStatus controlStatus) {
      this.controlStatus = controlStatus;
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

    public Builder callStatus(MarginEntityCallStatus callStatus) {
      this.callStatus = callStatus;
      return this;
    }

    public MarginSummary build() {
      return new MarginSummary(this);
    }
  }
}
