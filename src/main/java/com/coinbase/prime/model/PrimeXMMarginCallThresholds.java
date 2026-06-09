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
import java.util.List;

public class PrimeXMMarginCallThresholds {
  /** Deficit threshold (DT). */
  @JsonProperty("deficit_threshold")
  private String deficitThreshold;

  /** Warning threshold (WT). */
  @JsonProperty("warning_threshold")
  private String warningThreshold;

  /** Urgent margin call threshold (UMCT). */
  @JsonProperty("critical_threshold")
  private String criticalThreshold;

  /** Liquidation threshold (LT). */
  @JsonProperty("liquidation_threshold")
  private String liquidationThreshold;

  /** Structured margin thresholds by margin level. */
  @JsonProperty("margin_thresholds")
  private List<PrimeXMMarginThreshold> marginThresholds;

  public PrimeXMMarginCallThresholds() {}

  public PrimeXMMarginCallThresholds(Builder builder) {
    this.deficitThreshold = builder.deficitThreshold;
    this.warningThreshold = builder.warningThreshold;
    this.criticalThreshold = builder.criticalThreshold;
    this.liquidationThreshold = builder.liquidationThreshold;
    this.marginThresholds = builder.marginThresholds;
  }

  public String getDeficitThreshold() {
    return deficitThreshold;
  }

  public void setDeficitThreshold(String deficitThreshold) {
    this.deficitThreshold = deficitThreshold;
  }

  public String getWarningThreshold() {
    return warningThreshold;
  }

  public void setWarningThreshold(String warningThreshold) {
    this.warningThreshold = warningThreshold;
  }

  public String getCriticalThreshold() {
    return criticalThreshold;
  }

  public void setCriticalThreshold(String criticalThreshold) {
    this.criticalThreshold = criticalThreshold;
  }

  public String getLiquidationThreshold() {
    return liquidationThreshold;
  }

  public void setLiquidationThreshold(String liquidationThreshold) {
    this.liquidationThreshold = liquidationThreshold;
  }

  public List<PrimeXMMarginThreshold> getMarginThresholds() {
    return marginThresholds;
  }

  public void setMarginThresholds(List<PrimeXMMarginThreshold> marginThresholds) {
    this.marginThresholds = marginThresholds;
  }

  public static class Builder {
    private String deficitThreshold;

    private String warningThreshold;

    private String criticalThreshold;

    private String liquidationThreshold;

    private List<PrimeXMMarginThreshold> marginThresholds;

    public Builder deficitThreshold(String deficitThreshold) {
      this.deficitThreshold = deficitThreshold;
      return this;
    }

    public Builder warningThreshold(String warningThreshold) {
      this.warningThreshold = warningThreshold;
      return this;
    }

    public Builder criticalThreshold(String criticalThreshold) {
      this.criticalThreshold = criticalThreshold;
      return this;
    }

    public Builder liquidationThreshold(String liquidationThreshold) {
      this.liquidationThreshold = liquidationThreshold;
      return this;
    }

    public Builder marginThresholds(List<PrimeXMMarginThreshold> marginThresholds) {
      this.marginThresholds = marginThresholds;
      return this;
    }

    public PrimeXMMarginCallThresholds build() {
      return new PrimeXMMarginCallThresholds(this);
    }
  }
}
