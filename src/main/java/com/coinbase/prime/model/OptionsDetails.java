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

import com.coinbase.prime.model.enums.OptionType;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Options-specific details for a derivative position, including greeks. */
public class OptionsDetails {
  /** Delta greek */
  private String delta;

  /** Gamma greek */
  private String gamma;

  /** Theta greek */
  private String theta;

  /** Vega greek */
  private String vega;

  /** Strike price */
  private String strike;

  /**
   * The type of an option position. - OPTION_TYPE_UNSPECIFIED: Unspecified option type. -
   * OPTION_TYPE_CALL: Call option. - OPTION_TYPE_PUT: Put option.
   */
  @JsonProperty("option_type")
  private OptionType optionType;

  public OptionsDetails() {}

  public OptionsDetails(Builder builder) {
    this.delta = builder.delta;
    this.gamma = builder.gamma;
    this.theta = builder.theta;
    this.vega = builder.vega;
    this.strike = builder.strike;
    this.optionType = builder.optionType;
  }

  public String getDelta() {
    return delta;
  }

  public void setDelta(String delta) {
    this.delta = delta;
  }

  public String getGamma() {
    return gamma;
  }

  public void setGamma(String gamma) {
    this.gamma = gamma;
  }

  public String getTheta() {
    return theta;
  }

  public void setTheta(String theta) {
    this.theta = theta;
  }

  public String getVega() {
    return vega;
  }

  public void setVega(String vega) {
    this.vega = vega;
  }

  public String getStrike() {
    return strike;
  }

  public void setStrike(String strike) {
    this.strike = strike;
  }

  public OptionType getOptionType() {
    return optionType;
  }

  public void setOptionType(OptionType optionType) {
    this.optionType = optionType;
  }

  public static class Builder {
    private String delta;

    private String gamma;

    private String theta;

    private String vega;

    private String strike;

    private OptionType optionType;

    public Builder delta(String delta) {
      this.delta = delta;
      return this;
    }

    public Builder gamma(String gamma) {
      this.gamma = gamma;
      return this;
    }

    public Builder theta(String theta) {
      this.theta = theta;
      return this;
    }

    public Builder vega(String vega) {
      this.vega = vega;
      return this;
    }

    public Builder strike(String strike) {
      this.strike = strike;
      return this;
    }

    public Builder optionType(OptionType optionType) {
      this.optionType = optionType;
      return this;
    }

    public OptionsDetails build() {
      return new OptionsDetails(this);
    }
  }
}
