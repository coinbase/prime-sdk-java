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

import com.coinbase.prime.model.enums.RewardsRateTierType;
import com.fasterxml.jackson.annotation.JsonProperty;

/** RewardsRateTier is a single tier in the rewards rate card. */
public class RewardsRateTier {
  /** Rate for this tier. */
  private String rate;

  /** Lower bound of the tier's qualifying criteria (e.g. balance floor). */
  @JsonProperty("lower_limit")
  private String lowerLimit;

  /** Upper bound of the tier's qualifying criteria. */
  @JsonProperty("upper_limit")
  private String upperLimit;

  /** RewardsRateTierType describes what a rewards rate tier is scoped by. */
  @JsonProperty("criteria_type")
  private RewardsRateTierType criteriaType;

  public RewardsRateTier() {}

  public RewardsRateTier(Builder builder) {
    this.rate = builder.rate;
    this.lowerLimit = builder.lowerLimit;
    this.upperLimit = builder.upperLimit;
    this.criteriaType = builder.criteriaType;
  }

  public String getRate() {
    return rate;
  }

  public void setRate(String rate) {
    this.rate = rate;
  }

  public String getLowerLimit() {
    return lowerLimit;
  }

  public void setLowerLimit(String lowerLimit) {
    this.lowerLimit = lowerLimit;
  }

  public String getUpperLimit() {
    return upperLimit;
  }

  public void setUpperLimit(String upperLimit) {
    this.upperLimit = upperLimit;
  }

  public RewardsRateTierType getCriteriaType() {
    return criteriaType;
  }

  public void setCriteriaType(RewardsRateTierType criteriaType) {
    this.criteriaType = criteriaType;
  }

  public static class Builder {
    private String rate;

    private String lowerLimit;

    private String upperLimit;

    private RewardsRateTierType criteriaType;

    public Builder rate(String rate) {
      this.rate = rate;
      return this;
    }

    public Builder lowerLimit(String lowerLimit) {
      this.lowerLimit = lowerLimit;
      return this;
    }

    public Builder upperLimit(String upperLimit) {
      this.upperLimit = upperLimit;
      return this;
    }

    public Builder criteriaType(RewardsRateTierType criteriaType) {
      this.criteriaType = criteriaType;
      return this;
    }

    public RewardsRateTier build() {
      return new RewardsRateTier(this);
    }
  }
}
