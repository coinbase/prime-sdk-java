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

import com.coinbase.prime.model.enums.MarginLevel;
import com.coinbase.prime.model.enums.MarginThresholdMarginThresholdType;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A single margin level, the ratio used to evaluate its threshold, and the threshold value. */
public class MarginThreshold {
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
   * Ratio used to evaluate a threshold. Rules are evaluated in configured order; the first matching
   * rule determines the margin level when equity is below the margin requirement. A zero threshold
   * imposes no constraint for that ratio. If the denominator is zero, the evaluator skips the
   * division and checks the numerator condition: nonnegative equity for an equity-ratio rule, or a
   * positive deficit for a deficit-ratio rule. - MARGIN_THRESHOLD_TYPE_UNSPECIFIED: No threshold
   * type is provided. - MARGIN_THRESHOLD_TYPE_EQUITY_RATIO: For a nonzero threshold and positive
   * margin requirement, the rule matches when equity is nonnegative and equity / margin requirement
   * is greater than or equal to threshold_value. - MARGIN_THRESHOLD_TYPE_DEFICIT_RATIO: For a
   * nonzero threshold and positive margin limit, the rule matches when the deficit is positive and
   * (margin requirement - equity) / margin limit is greater than threshold_value. -
   * MARGIN_THRESHOLD_TYPE_NONE: Fallback rule with no numeric ratio threshold.
   */
  @JsonProperty("threshold_type")
  private MarginThresholdMarginThresholdType thresholdType;

  /**
   * Threshold expressed as a decimal ratio, not a percentage. For example, \"0.8\" represents 80%.
   * Interpret this value together with threshold_type.
   */
  @JsonProperty("threshold_value")
  private String thresholdValue;

  public MarginThreshold() {}

  public MarginThreshold(Builder builder) {
    this.marginLevel = builder.marginLevel;
    this.thresholdType = builder.thresholdType;
    this.thresholdValue = builder.thresholdValue;
  }

  public MarginLevel getMarginLevel() {
    return marginLevel;
  }

  public void setMarginLevel(MarginLevel marginLevel) {
    this.marginLevel = marginLevel;
  }

  public MarginThresholdMarginThresholdType getThresholdType() {
    return thresholdType;
  }

  public void setThresholdType(MarginThresholdMarginThresholdType thresholdType) {
    this.thresholdType = thresholdType;
  }

  public String getThresholdValue() {
    return thresholdValue;
  }

  public void setThresholdValue(String thresholdValue) {
    this.thresholdValue = thresholdValue;
  }

  public static class Builder {
    private MarginLevel marginLevel;

    private MarginThresholdMarginThresholdType thresholdType;

    private String thresholdValue;

    public Builder marginLevel(MarginLevel marginLevel) {
      this.marginLevel = marginLevel;
      return this;
    }

    public Builder thresholdType(MarginThresholdMarginThresholdType thresholdType) {
      this.thresholdType = thresholdType;
      return this;
    }

    public Builder thresholdValue(String thresholdValue) {
      this.thresholdValue = thresholdValue;
      return this;
    }

    public MarginThreshold build() {
      return new MarginThreshold(this);
    }
  }
}
