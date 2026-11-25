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

import com.coinbase.prime.model.enums.MaxDeltaType;
import com.coinbase.prime.model.enums.SpreadLimitType;
import com.fasterxml.jackson.annotation.JsonProperty;

/** SPREAD order preview: the submitted configuration plus per-leg values */
public class SpreadPreview {
  private String limit;

  /**
   * - UNKNOWN_SPREAD_LIMIT_TYPE: nil value - SPREAD_LIMIT_TYPE_BPS: Limit in basis points of the
   * spot price - SPREAD_LIMIT_TYPE_USD: Limit in USD
   */
  @JsonProperty("limit_type")
  private SpreadLimitType limitType;

  @JsonProperty("max_delta_size")
  private String maxDeltaSize;

  /**
   * - UNKNOWN_MAX_DELTA_TYPE: nil value - MAX_DELTA_TYPE_SPOT: Absolute size in spot base units.
   * Not accepted for a calendar spread (two futures). - MAX_DELTA_TYPE_PERCENT: Percentage of the
   * order size - MAX_DELTA_TYPE_CONTRACT: Absolute number of futures contracts
   */
  @JsonProperty("max_delta_type")
  private MaxDeltaType maxDeltaType;

  /** Product ID of the spread product, in the form <futures product_id>:<spot product_id> */
  @JsonProperty("group_product_id")
  private String groupProductId;

  /** Preview values for one leg of a SPREAD order */
  @JsonProperty("primary_leg")
  private LegPreview primaryLeg;

  /** Preview values for one leg of a SPREAD order */
  @JsonProperty("secondary_leg")
  private LegPreview secondaryLeg;

  public SpreadPreview() {}

  public SpreadPreview(Builder builder) {
    this.limit = builder.limit;
    this.limitType = builder.limitType;
    this.maxDeltaSize = builder.maxDeltaSize;
    this.maxDeltaType = builder.maxDeltaType;
    this.groupProductId = builder.groupProductId;
    this.primaryLeg = builder.primaryLeg;
    this.secondaryLeg = builder.secondaryLeg;
  }

  public String getLimit() {
    return limit;
  }

  public void setLimit(String limit) {
    this.limit = limit;
  }

  public SpreadLimitType getLimitType() {
    return limitType;
  }

  public void setLimitType(SpreadLimitType limitType) {
    this.limitType = limitType;
  }

  public String getMaxDeltaSize() {
    return maxDeltaSize;
  }

  public void setMaxDeltaSize(String maxDeltaSize) {
    this.maxDeltaSize = maxDeltaSize;
  }

  public MaxDeltaType getMaxDeltaType() {
    return maxDeltaType;
  }

  public void setMaxDeltaType(MaxDeltaType maxDeltaType) {
    this.maxDeltaType = maxDeltaType;
  }

  public String getGroupProductId() {
    return groupProductId;
  }

  public void setGroupProductId(String groupProductId) {
    this.groupProductId = groupProductId;
  }

  public LegPreview getPrimaryLeg() {
    return primaryLeg;
  }

  public void setPrimaryLeg(LegPreview primaryLeg) {
    this.primaryLeg = primaryLeg;
  }

  public LegPreview getSecondaryLeg() {
    return secondaryLeg;
  }

  public void setSecondaryLeg(LegPreview secondaryLeg) {
    this.secondaryLeg = secondaryLeg;
  }

  public static class Builder {
    private String limit;

    private SpreadLimitType limitType;

    private String maxDeltaSize;

    private MaxDeltaType maxDeltaType;

    private String groupProductId;

    private LegPreview primaryLeg;

    private LegPreview secondaryLeg;

    public Builder limit(String limit) {
      this.limit = limit;
      return this;
    }

    public Builder limitType(SpreadLimitType limitType) {
      this.limitType = limitType;
      return this;
    }

    public Builder maxDeltaSize(String maxDeltaSize) {
      this.maxDeltaSize = maxDeltaSize;
      return this;
    }

    public Builder maxDeltaType(MaxDeltaType maxDeltaType) {
      this.maxDeltaType = maxDeltaType;
      return this;
    }

    public Builder groupProductId(String groupProductId) {
      this.groupProductId = groupProductId;
      return this;
    }

    public Builder primaryLeg(LegPreview primaryLeg) {
      this.primaryLeg = primaryLeg;
      return this;
    }

    public Builder secondaryLeg(LegPreview secondaryLeg) {
      this.secondaryLeg = secondaryLeg;
      return this;
    }

    public SpreadPreview build() {
      return new SpreadPreview(this);
    }
  }
}
