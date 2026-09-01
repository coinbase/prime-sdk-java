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

import com.coinbase.prime.model.enums.XMLiquidationStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

/** XMLiquidationDetail provides detailed information about a single XM liquidation */
public class XMLiquidationDetail {
  /** Financing liquidation UUID */
  @JsonProperty("liquidation_id")
  private String liquidationId;

  /**
   * - XM_LIQUIDATION_STATUS_PRE_LIQUIDATION: Liquidation is in the pre-liquidation phase -
   * XM_LIQUIDATION_STATUS_LIQUIDATING: Liquidation is actively in progress -
   * XM_LIQUIDATION_STATUS_LIQUIDATED: Liquidation has completed successfully -
   * XM_LIQUIDATION_STATUS_CANCELED: Liquidation was canceled - XM_LIQUIDATION_STATUS_FAILED:
   * Liquidation failed
   */
  private XMLiquidationStatus status;

  /** USD notional shortfall amount that triggered the liquidation */
  @JsonProperty("shortfall_amount")
  private String shortfallAmount;

  /** Timestamp when the pre-liquidation phase started */
  @JsonProperty("pre_liquidation_start_time")
  private OffsetDateTime preLiquidationStartTime;

  /** XMSummary is the realtime evaluated XM margin model, containing positions and netting info */
  @JsonProperty("margin_summary")
  private XMSummary marginSummary;

  /** Timestamp when active liquidation started */
  @JsonProperty("liquidation_start_time")
  private OffsetDateTime liquidationStartTime;

  /** USD notional amount that has been filled so far */
  @JsonProperty("filled_amount")
  private String filledAmount;

  /** USD notional amount remaining to be liquidated */
  @JsonProperty("remaining_amount")
  private String remainingAmount;

  /** Timestamp when the liquidation finished */
  @JsonProperty("liquidation_finish_time")
  private OffsetDateTime liquidationFinishTime;

  /** Per-asset breakdown of liquidated amounts */
  @JsonProperty("asset_breakdown")
  private List<XMLiquidatedAsset> assetBreakdown;

  public XMLiquidationDetail() {}

  public XMLiquidationDetail(Builder builder) {
    this.liquidationId = builder.liquidationId;
    this.status = builder.status;
    this.shortfallAmount = builder.shortfallAmount;
    this.preLiquidationStartTime = builder.preLiquidationStartTime;
    this.marginSummary = builder.marginSummary;
    this.liquidationStartTime = builder.liquidationStartTime;
    this.filledAmount = builder.filledAmount;
    this.remainingAmount = builder.remainingAmount;
    this.liquidationFinishTime = builder.liquidationFinishTime;
    this.assetBreakdown = builder.assetBreakdown;
  }

  public String getLiquidationId() {
    return liquidationId;
  }

  public void setLiquidationId(String liquidationId) {
    this.liquidationId = liquidationId;
  }

  public XMLiquidationStatus getStatus() {
    return status;
  }

  public void setStatus(XMLiquidationStatus status) {
    this.status = status;
  }

  public String getShortfallAmount() {
    return shortfallAmount;
  }

  public void setShortfallAmount(String shortfallAmount) {
    this.shortfallAmount = shortfallAmount;
  }

  public OffsetDateTime getPreLiquidationStartTime() {
    return preLiquidationStartTime;
  }

  public void setPreLiquidationStartTime(OffsetDateTime preLiquidationStartTime) {
    this.preLiquidationStartTime = preLiquidationStartTime;
  }

  public XMSummary getMarginSummary() {
    return marginSummary;
  }

  public void setMarginSummary(XMSummary marginSummary) {
    this.marginSummary = marginSummary;
  }

  public OffsetDateTime getLiquidationStartTime() {
    return liquidationStartTime;
  }

  public void setLiquidationStartTime(OffsetDateTime liquidationStartTime) {
    this.liquidationStartTime = liquidationStartTime;
  }

  public String getFilledAmount() {
    return filledAmount;
  }

  public void setFilledAmount(String filledAmount) {
    this.filledAmount = filledAmount;
  }

  public String getRemainingAmount() {
    return remainingAmount;
  }

  public void setRemainingAmount(String remainingAmount) {
    this.remainingAmount = remainingAmount;
  }

  public OffsetDateTime getLiquidationFinishTime() {
    return liquidationFinishTime;
  }

  public void setLiquidationFinishTime(OffsetDateTime liquidationFinishTime) {
    this.liquidationFinishTime = liquidationFinishTime;
  }

  public List<XMLiquidatedAsset> getAssetBreakdown() {
    return assetBreakdown;
  }

  public void setAssetBreakdown(List<XMLiquidatedAsset> assetBreakdown) {
    this.assetBreakdown = assetBreakdown;
  }

  public static class Builder {
    private String liquidationId;

    private XMLiquidationStatus status;

    private String shortfallAmount;

    private OffsetDateTime preLiquidationStartTime;

    private XMSummary marginSummary;

    private OffsetDateTime liquidationStartTime;

    private String filledAmount;

    private String remainingAmount;

    private OffsetDateTime liquidationFinishTime;

    private List<XMLiquidatedAsset> assetBreakdown;

    public Builder liquidationId(String liquidationId) {
      this.liquidationId = liquidationId;
      return this;
    }

    public Builder status(XMLiquidationStatus status) {
      this.status = status;
      return this;
    }

    public Builder shortfallAmount(String shortfallAmount) {
      this.shortfallAmount = shortfallAmount;
      return this;
    }

    public Builder preLiquidationStartTime(OffsetDateTime preLiquidationStartTime) {
      this.preLiquidationStartTime = preLiquidationStartTime;
      return this;
    }

    public Builder marginSummary(XMSummary marginSummary) {
      this.marginSummary = marginSummary;
      return this;
    }

    public Builder liquidationStartTime(OffsetDateTime liquidationStartTime) {
      this.liquidationStartTime = liquidationStartTime;
      return this;
    }

    public Builder filledAmount(String filledAmount) {
      this.filledAmount = filledAmount;
      return this;
    }

    public Builder remainingAmount(String remainingAmount) {
      this.remainingAmount = remainingAmount;
      return this;
    }

    public Builder liquidationFinishTime(OffsetDateTime liquidationFinishTime) {
      this.liquidationFinishTime = liquidationFinishTime;
      return this;
    }

    public Builder assetBreakdown(List<XMLiquidatedAsset> assetBreakdown) {
      this.assetBreakdown = assetBreakdown;
      return this;
    }

    public XMLiquidationDetail build() {
      return new XMLiquidationDetail(this);
    }
  }
}
