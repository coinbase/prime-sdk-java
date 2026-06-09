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

import com.coinbase.prime.model.enums.PrimeXMMarginThresholdType;
import com.coinbase.prime.model.enums.XMMarginLevel;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PrimeXMMarginThreshold {
  /**
   * - HEALTHY_THRESHOLD: Margin level is healthy - DEFICIT_THRESHOLD: Margin level is breaching the
   * deficit threshold (DT) which will result in the issuance of a Margin Call if this is still the
   * case by the scheduled next Margin Call time (as defined in the margin methodology) -
   * WARNING_THRESHOLD: Margin level is breaching the warning threshold (WT) which will result in
   * the issuance of a Margin Call if this is still the case by the scheduled next Margin Call (as
   * defined in the margin methodology). WT is differentiated from DT in that it means margin health
   * is approaching the UMCT - URGENT_MARGIN_CALL_THRESHOLD: Margin level is breaching the UMCT and,
   * as defined in the margin methodology, this will trigger an urgent margin call -
   * LIQUIDATION_THRESHOLD: Margin level is breaching the liquidation threshold (LT) and, as defined
   * in the margin methodology, this will trigger the SESSION_LOCKED control status and liquidation
   * may commence.
   */
  @JsonProperty("margin_level")
  private XMMarginLevel marginLevel;

  /**
   * - MARGIN_THRESHOLD_EQUITY_RATIO: Threshold based on equity ratio EQ / MR; triggers when EQ / MR
   * >= threshold_value. - MARGIN_THRESHOLD_DEFICIT_RATIO: Threshold based on deficit ratio (MR -
   * EQ) / XMML; triggers when (MR - EQ) / XMML > threshold_value.
   */
  @JsonProperty("threshold_type")
  private PrimeXMMarginThresholdType thresholdType;

  @JsonProperty("threshold_value")
  private String thresholdValue;

  public PrimeXMMarginThreshold() {}

  public PrimeXMMarginThreshold(Builder builder) {
    this.marginLevel = builder.marginLevel;
    this.thresholdType = builder.thresholdType;
    this.thresholdValue = builder.thresholdValue;
  }

  public XMMarginLevel getMarginLevel() {
    return marginLevel;
  }

  public void setMarginLevel(XMMarginLevel marginLevel) {
    this.marginLevel = marginLevel;
  }

  public PrimeXMMarginThresholdType getThresholdType() {
    return thresholdType;
  }

  public void setThresholdType(PrimeXMMarginThresholdType thresholdType) {
    this.thresholdType = thresholdType;
  }

  public String getThresholdValue() {
    return thresholdValue;
  }

  public void setThresholdValue(String thresholdValue) {
    this.thresholdValue = thresholdValue;
  }

  public static class Builder {
    private XMMarginLevel marginLevel;

    private PrimeXMMarginThresholdType thresholdType;

    private String thresholdValue;

    public Builder marginLevel(XMMarginLevel marginLevel) {
      this.marginLevel = marginLevel;
      return this;
    }

    public Builder thresholdType(PrimeXMMarginThresholdType thresholdType) {
      this.thresholdType = thresholdType;
      return this;
    }

    public Builder thresholdValue(String thresholdValue) {
      this.thresholdValue = thresholdValue;
      return this;
    }

    public PrimeXMMarginThreshold build() {
      return new PrimeXMMarginThreshold(this);
    }
  }
}
