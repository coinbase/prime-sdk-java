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

/**
 * PriceIncrementStep overrides the product's price_increment for prices above a threshold. A price
 * takes the increment of the highest step whose threshold it strictly exceeds, and the product's
 * price_increment when it exceeds none.
 */
public class PriceIncrementStep {
  /**
   * Prices strictly greater than this threshold use this step's price increment; a price equal to
   * it takes the next lower step
   */
  @JsonProperty("price_threshold")
  private String priceThreshold;

  /** Minimum price increment applied to prices above the threshold */
  @JsonProperty("price_increment")
  private String priceIncrement;

  public PriceIncrementStep() {}

  public PriceIncrementStep(Builder builder) {
    this.priceThreshold = builder.priceThreshold;
    this.priceIncrement = builder.priceIncrement;
  }

  public String getPriceThreshold() {
    return priceThreshold;
  }

  public void setPriceThreshold(String priceThreshold) {
    this.priceThreshold = priceThreshold;
  }

  public String getPriceIncrement() {
    return priceIncrement;
  }

  public void setPriceIncrement(String priceIncrement) {
    this.priceIncrement = priceIncrement;
  }

  public static class Builder {
    private String priceThreshold;

    private String priceIncrement;

    public Builder priceThreshold(String priceThreshold) {
      this.priceThreshold = priceThreshold;
      return this;
    }

    public Builder priceIncrement(String priceIncrement) {
      this.priceIncrement = priceIncrement;
      return this;
    }

    public PriceIncrementStep build() {
      return new PriceIncrementStep(this);
    }
  }
}
