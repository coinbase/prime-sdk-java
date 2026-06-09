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

/** Details for a custom stablecoin reward payout transaction */
public class CustomStablecoinRewardDetails {
  /** ISO-formatted start date of the reward period (e.g. 2025-02-01T00:00:00Z) */
  @JsonProperty("start_date")
  private String startDate;

  /** ISO-formatted end date of the reward period (e.g. 2025-02-28T00:00:00Z) */
  @JsonProperty("end_date")
  private String endDate;

  public CustomStablecoinRewardDetails() {}

  public CustomStablecoinRewardDetails(Builder builder) {
    this.startDate = builder.startDate;
    this.endDate = builder.endDate;
  }

  public String getStartDate() {
    return startDate;
  }

  public void setStartDate(String startDate) {
    this.startDate = startDate;
  }

  public String getEndDate() {
    return endDate;
  }

  public void setEndDate(String endDate) {
    this.endDate = endDate;
  }

  public static class Builder {
    private String startDate;

    private String endDate;

    public Builder startDate(String startDate) {
      this.startDate = startDate;
      return this;
    }

    public Builder endDate(String endDate) {
      this.endDate = endDate;
      return this;
    }

    public CustomStablecoinRewardDetails build() {
      return new CustomStablecoinRewardDetails(this);
    }
  }
}
