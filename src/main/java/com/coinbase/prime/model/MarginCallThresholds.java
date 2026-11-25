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

/**
 * Configured thresholds used to determine `margin_level`. Each entry identifies a margin level, the
 * ratio used to evaluate it, and its threshold value. Historical responses contain the thresholds
 * recorded with the historical health evaluation. Equity at or above the margin requirement
 * produces a healthy margin level. Otherwise, the evaluator selects the first matching rule in the
 * configured order. A threshold comparison identifies a matching level; it doesn't by itself issue
 * a margin call or change account permissions.
 */
public class MarginCallThresholds {
  /** Ordered thresholds used to select a margin level. */
  @JsonProperty("margin_thresholds")
  private List<MarginThreshold> marginThresholds;

  public MarginCallThresholds() {}

  public MarginCallThresholds(Builder builder) {
    this.marginThresholds = builder.marginThresholds;
  }

  public List<MarginThreshold> getMarginThresholds() {
    return marginThresholds;
  }

  public void setMarginThresholds(List<MarginThreshold> marginThresholds) {
    this.marginThresholds = marginThresholds;
  }

  public static class Builder {
    private List<MarginThreshold> marginThresholds;

    public Builder marginThresholds(List<MarginThreshold> marginThresholds) {
      this.marginThresholds = marginThresholds;
      return this;
    }

    public MarginCallThresholds build() {
      return new MarginCallThresholds(this);
    }
  }
}
