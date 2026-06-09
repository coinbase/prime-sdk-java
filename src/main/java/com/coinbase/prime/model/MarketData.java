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

public class MarketData {
  /** Base asset symbol (e.g., BTC, ETH, SOL) */
  private String symbol;

  /** Daily historical volatility over trailing 5 days (decimal, e.g., 0.65 &#x3D; 65%) */
  @JsonProperty("vol_5d")
  private String vol5d;

  /** Daily historical volatility over trailing 30 days (decimal, e.g., 0.65 &#x3D; 65%) */
  @JsonProperty("vol_30d")
  private String vol30d;

  /** Daily historical volatility over trailing 90 days (decimal, e.g., 0.65 &#x3D; 65%) */
  @JsonProperty("vol_90d")
  private String vol90d;

  /** Average daily trading volume over trailing 30 days (USD) */
  @JsonProperty("adv_30d")
  private String adv30d;

  /**
   * Weighted blend of the most recent vol_5d and the max vol_5d over last 30 days into a single
   * volatility measure (decimal).
   */
  @JsonProperty("weighted_vol")
  private String weightedVol;

  public MarketData() {}

  public MarketData(Builder builder) {
    this.symbol = builder.symbol;
    this.vol5d = builder.vol5d;
    this.vol30d = builder.vol30d;
    this.vol90d = builder.vol90d;
    this.adv30d = builder.adv30d;
    this.weightedVol = builder.weightedVol;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getVol5d() {
    return vol5d;
  }

  public void setVol5d(String vol5d) {
    this.vol5d = vol5d;
  }

  public String getVol30d() {
    return vol30d;
  }

  public void setVol30d(String vol30d) {
    this.vol30d = vol30d;
  }

  public String getVol90d() {
    return vol90d;
  }

  public void setVol90d(String vol90d) {
    this.vol90d = vol90d;
  }

  public String getAdv30d() {
    return adv30d;
  }

  public void setAdv30d(String adv30d) {
    this.adv30d = adv30d;
  }

  public String getWeightedVol() {
    return weightedVol;
  }

  public void setWeightedVol(String weightedVol) {
    this.weightedVol = weightedVol;
  }

  public static class Builder {
    private String symbol;

    private String vol5d;

    private String vol30d;

    private String vol90d;

    private String adv30d;

    private String weightedVol;

    public Builder symbol(String symbol) {
      this.symbol = symbol;
      return this;
    }

    public Builder vol5d(String vol5d) {
      this.vol5d = vol5d;
      return this;
    }

    public Builder vol30d(String vol30d) {
      this.vol30d = vol30d;
      return this;
    }

    public Builder vol90d(String vol90d) {
      this.vol90d = vol90d;
      return this;
    }

    public Builder adv30d(String adv30d) {
      this.adv30d = adv30d;
      return this;
    }

    public Builder weightedVol(String weightedVol) {
      this.weightedVol = weightedVol;
      return this;
    }

    public MarketData build() {
      return new MarketData(this);
    }
  }
}
