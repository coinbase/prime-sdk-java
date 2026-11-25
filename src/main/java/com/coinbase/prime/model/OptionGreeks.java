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

/** Greeks of one option contract as quoted by the venue, not scaled by order size or side. */
public class OptionGreeks {
  /** Delta greek, in units of the underlying */
  private String delta;

  /** Gamma greek */
  private String gamma;

  /** Theta greek, in USD per day */
  private String theta;

  /** Vega greek, in USD per 1% change in implied volatility */
  private String vega;

  public OptionGreeks() {}

  public OptionGreeks(Builder builder) {
    this.delta = builder.delta;
    this.gamma = builder.gamma;
    this.theta = builder.theta;
    this.vega = builder.vega;
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

  public static class Builder {
    private String delta;

    private String gamma;

    private String theta;

    private String vega;

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

    public OptionGreeks build() {
      return new OptionGreeks(this);
    }
  }
}
