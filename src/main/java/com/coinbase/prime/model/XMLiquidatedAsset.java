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

/** XMLiquidatedAsset provides per-asset detail for a liquidation */
public class XMLiquidatedAsset {
  /** Asset currency */
  private String asset;

  /** Amount (nominal) of the asset that has been liquidated */
  @JsonProperty("liquidated_amount")
  private String liquidatedAmount;

  /** USD notional value of the liquidated amount */
  @JsonProperty("liquidated_notional")
  private String liquidatedNotional;

  /** Amount (nominal) of the asset remaining to be liquidated */
  @JsonProperty("remaining_amount")
  private String remainingAmount;

  /** USD notional value of the remaining amount */
  @JsonProperty("remaining_notional")
  private String remainingNotional;

  public XMLiquidatedAsset() {}

  public XMLiquidatedAsset(Builder builder) {
    this.asset = builder.asset;
    this.liquidatedAmount = builder.liquidatedAmount;
    this.liquidatedNotional = builder.liquidatedNotional;
    this.remainingAmount = builder.remainingAmount;
    this.remainingNotional = builder.remainingNotional;
  }

  public String getAsset() {
    return asset;
  }

  public void setAsset(String asset) {
    this.asset = asset;
  }

  public String getLiquidatedAmount() {
    return liquidatedAmount;
  }

  public void setLiquidatedAmount(String liquidatedAmount) {
    this.liquidatedAmount = liquidatedAmount;
  }

  public String getLiquidatedNotional() {
    return liquidatedNotional;
  }

  public void setLiquidatedNotional(String liquidatedNotional) {
    this.liquidatedNotional = liquidatedNotional;
  }

  public String getRemainingAmount() {
    return remainingAmount;
  }

  public void setRemainingAmount(String remainingAmount) {
    this.remainingAmount = remainingAmount;
  }

  public String getRemainingNotional() {
    return remainingNotional;
  }

  public void setRemainingNotional(String remainingNotional) {
    this.remainingNotional = remainingNotional;
  }

  public static class Builder {
    private String asset;

    private String liquidatedAmount;

    private String liquidatedNotional;

    private String remainingAmount;

    private String remainingNotional;

    public Builder asset(String asset) {
      this.asset = asset;
      return this;
    }

    public Builder liquidatedAmount(String liquidatedAmount) {
      this.liquidatedAmount = liquidatedAmount;
      return this;
    }

    public Builder liquidatedNotional(String liquidatedNotional) {
      this.liquidatedNotional = liquidatedNotional;
      return this;
    }

    public Builder remainingAmount(String remainingAmount) {
      this.remainingAmount = remainingAmount;
      return this;
    }

    public Builder remainingNotional(String remainingNotional) {
      this.remainingNotional = remainingNotional;
      return this;
    }

    public XMLiquidatedAsset build() {
      return new XMLiquidatedAsset(this);
    }
  }
}
