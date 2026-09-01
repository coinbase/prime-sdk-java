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

/** A single fee tier in the progressive stablecoin conversion schedule. */
public class ConversionFeeTier {
    /**
     * Inclusive lower bound for the tier in USD.
     */
    @JsonProperty("min_threshold")
    private String minThreshold;

    /**
     * Exclusive upper bound for the tier in USD. Empty string for the highest, unbounded tier.
     */
    @JsonProperty("max_threshold")
    private String maxThreshold;

    /**
     * Tier rate in basis points.
     */
    @JsonProperty("rate_bps")
    private String rateBps;

    public ConversionFeeTier() {
    }

    public ConversionFeeTier(Builder builder) {
        this.minThreshold = builder.minThreshold;
        this.maxThreshold = builder.maxThreshold;
        this.rateBps = builder.rateBps;
    }
    public String getMinThreshold() {
        return minThreshold;
    }

    public void setMinThreshold(String minThreshold) {
        this.minThreshold = minThreshold;
    }
    public String getMaxThreshold() {
        return maxThreshold;
    }

    public void setMaxThreshold(String maxThreshold) {
        this.maxThreshold = maxThreshold;
    }
    public String getRateBps() {
        return rateBps;
    }

    public void setRateBps(String rateBps) {
        this.rateBps = rateBps;
    }
    public static class Builder {
        private String minThreshold;

        private String maxThreshold;

        private String rateBps;

        public Builder minThreshold(String minThreshold) {
            this.minThreshold = minThreshold;
            return this;
        }

        public Builder maxThreshold(String maxThreshold) {
            this.maxThreshold = maxThreshold;
            return this;
        }

        public Builder rateBps(String rateBps) {
            this.rateBps = rateBps;
            return this;
        }

        public ConversionFeeTier build() {
            return new ConversionFeeTier(this);
        }
    }
}

