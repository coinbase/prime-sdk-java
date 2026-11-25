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

/**
 * `primary_leg`: its product_id must equal the order's `product_id`, and `base_quantity` is in its
 * units. For a calendar spread (two futures) it must be the back month.
 */
public class SpreadConfig {
  /**
   * Worst acceptable spread (futures - spot), signed decimal. A buy-futures spread executes when
   * the spread is at or below the limit, a sell-futures spread when it is at or above.
   */
  private String limit;

  /**
   * - UNKNOWN_SPREAD_LIMIT_TYPE: nil value - SPREAD_LIMIT_TYPE_BPS: Limit in basis points of the
   * spot price - SPREAD_LIMIT_TYPE_USD: Limit in USD
   */
  @JsonProperty("limit_type")
  private SpreadLimitType limitType;

  /**
   * Maximum allowed difference between the filled sizes of the two legs, in the unit given by
   * `max_delta_type`
   */
  @JsonProperty("max_delta_size")
  private String maxDeltaSize;

  /**
   * - UNKNOWN_MAX_DELTA_TYPE: nil value - MAX_DELTA_TYPE_SPOT: Absolute size in spot base units.
   * Not accepted for a calendar spread (two futures). - MAX_DELTA_TYPE_PERCENT: Percentage of the
   * order size - MAX_DELTA_TYPE_CONTRACT: Absolute number of futures contracts
   */
  @JsonProperty("max_delta_type")
  private MaxDeltaType maxDeltaType;

  /** One leg of a SPREAD order */
  @JsonProperty("primary_leg")
  private Leg primaryLeg;

  /** One leg of a SPREAD order */
  @JsonProperty("secondary_leg")
  private Leg secondaryLeg;

  public SpreadConfig() {}

  public SpreadConfig(Builder builder) {
    this.limit = builder.limit;
    this.limitType = builder.limitType;
    this.maxDeltaSize = builder.maxDeltaSize;
    this.maxDeltaType = builder.maxDeltaType;
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

  public Leg getPrimaryLeg() {
    return primaryLeg;
  }

  public void setPrimaryLeg(Leg primaryLeg) {
    this.primaryLeg = primaryLeg;
  }

  public Leg getSecondaryLeg() {
    return secondaryLeg;
  }

  public void setSecondaryLeg(Leg secondaryLeg) {
    this.secondaryLeg = secondaryLeg;
  }

  public static class Builder {
    private String limit;

    private SpreadLimitType limitType;

    private String maxDeltaSize;

    private MaxDeltaType maxDeltaType;

    private Leg primaryLeg;

    private Leg secondaryLeg;

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

    public Builder primaryLeg(Leg primaryLeg) {
      this.primaryLeg = primaryLeg;
      return this;
    }

    public Builder secondaryLeg(Leg secondaryLeg) {
      this.secondaryLeg = secondaryLeg;
      return this;
    }

    public SpreadConfig build() {
      return new SpreadConfig(this);
    }
  }
}
