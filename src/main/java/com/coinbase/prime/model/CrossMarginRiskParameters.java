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

/** XM 2.0 risk parameters for an asset tier. */
public class CrossMarginRiskParameters {
    /**
     * Asset tier identifier.
     */
    @JsonProperty("asset_tier")
    private String assetTier;

    /**
     * Base ratio for long positions.
     */
    @JsonProperty("base_ratio_long")
    private String baseRatioLong;

    /**
     * Base ratio for short positions.
     */
    @JsonProperty("base_ratio_short")
    private String baseRatioShort;

    /**
     * Volatility rate for long positions.
     */
    @JsonProperty("volatility_rate_long")
    private String volatilityRateLong;

    /**
     * Volatility rate for short positions.
     */
    @JsonProperty("volatility_rate_short")
    private String volatilityRateShort;

    /**
     * Volatility low threshold.
     */
    @JsonProperty("volatility_low_threshold")
    private String volatilityLowThreshold;

    /**
     * Volatility high threshold.
     */
    @JsonProperty("volatility_high_threshold")
    private String volatilityHighThreshold;

    /**
     * Liquidity A for long positions.
     */
    @JsonProperty("liquidity_a_long")
    private String liquidityALong;

    /**
     * Liquidity A for short positions.
     */
    @JsonProperty("liquidity_a_short")
    private String liquidityAShort;

    /**
     * Liquidity B for short positions.
     */
    @JsonProperty("liquidity_b_short")
    private String liquidityBShort;

    /**
     * Liquidity threshold.
     */
    @JsonProperty("liquidity_threshold")
    private String liquidityThreshold;

    /**
     * Basis offset credit rate.
     */
    @JsonProperty("basis_offset_credit_rate")
    private String basisOffsetCreditRate;

    public CrossMarginRiskParameters() {
    }

    public CrossMarginRiskParameters(Builder builder) {
        this.assetTier = builder.assetTier;
        this.baseRatioLong = builder.baseRatioLong;
        this.baseRatioShort = builder.baseRatioShort;
        this.volatilityRateLong = builder.volatilityRateLong;
        this.volatilityRateShort = builder.volatilityRateShort;
        this.volatilityLowThreshold = builder.volatilityLowThreshold;
        this.volatilityHighThreshold = builder.volatilityHighThreshold;
        this.liquidityALong = builder.liquidityALong;
        this.liquidityAShort = builder.liquidityAShort;
        this.liquidityBShort = builder.liquidityBShort;
        this.liquidityThreshold = builder.liquidityThreshold;
        this.basisOffsetCreditRate = builder.basisOffsetCreditRate;
    }
    public String getAssetTier() {
        return assetTier;
    }

    public void setAssetTier(String assetTier) {
        this.assetTier = assetTier;
    }
    public String getBaseRatioLong() {
        return baseRatioLong;
    }

    public void setBaseRatioLong(String baseRatioLong) {
        this.baseRatioLong = baseRatioLong;
    }
    public String getBaseRatioShort() {
        return baseRatioShort;
    }

    public void setBaseRatioShort(String baseRatioShort) {
        this.baseRatioShort = baseRatioShort;
    }
    public String getVolatilityRateLong() {
        return volatilityRateLong;
    }

    public void setVolatilityRateLong(String volatilityRateLong) {
        this.volatilityRateLong = volatilityRateLong;
    }
    public String getVolatilityRateShort() {
        return volatilityRateShort;
    }

    public void setVolatilityRateShort(String volatilityRateShort) {
        this.volatilityRateShort = volatilityRateShort;
    }
    public String getVolatilityLowThreshold() {
        return volatilityLowThreshold;
    }

    public void setVolatilityLowThreshold(String volatilityLowThreshold) {
        this.volatilityLowThreshold = volatilityLowThreshold;
    }
    public String getVolatilityHighThreshold() {
        return volatilityHighThreshold;
    }

    public void setVolatilityHighThreshold(String volatilityHighThreshold) {
        this.volatilityHighThreshold = volatilityHighThreshold;
    }
    public String getLiquidityALong() {
        return liquidityALong;
    }

    public void setLiquidityALong(String liquidityALong) {
        this.liquidityALong = liquidityALong;
    }
    public String getLiquidityAShort() {
        return liquidityAShort;
    }

    public void setLiquidityAShort(String liquidityAShort) {
        this.liquidityAShort = liquidityAShort;
    }
    public String getLiquidityBShort() {
        return liquidityBShort;
    }

    public void setLiquidityBShort(String liquidityBShort) {
        this.liquidityBShort = liquidityBShort;
    }
    public String getLiquidityThreshold() {
        return liquidityThreshold;
    }

    public void setLiquidityThreshold(String liquidityThreshold) {
        this.liquidityThreshold = liquidityThreshold;
    }
    public String getBasisOffsetCreditRate() {
        return basisOffsetCreditRate;
    }

    public void setBasisOffsetCreditRate(String basisOffsetCreditRate) {
        this.basisOffsetCreditRate = basisOffsetCreditRate;
    }
    public static class Builder {
        private String assetTier;

        private String baseRatioLong;

        private String baseRatioShort;

        private String volatilityRateLong;

        private String volatilityRateShort;

        private String volatilityLowThreshold;

        private String volatilityHighThreshold;

        private String liquidityALong;

        private String liquidityAShort;

        private String liquidityBShort;

        private String liquidityThreshold;

        private String basisOffsetCreditRate;

        public Builder assetTier(String assetTier) {
            this.assetTier = assetTier;
            return this;
        }

        public Builder baseRatioLong(String baseRatioLong) {
            this.baseRatioLong = baseRatioLong;
            return this;
        }

        public Builder baseRatioShort(String baseRatioShort) {
            this.baseRatioShort = baseRatioShort;
            return this;
        }

        public Builder volatilityRateLong(String volatilityRateLong) {
            this.volatilityRateLong = volatilityRateLong;
            return this;
        }

        public Builder volatilityRateShort(String volatilityRateShort) {
            this.volatilityRateShort = volatilityRateShort;
            return this;
        }

        public Builder volatilityLowThreshold(String volatilityLowThreshold) {
            this.volatilityLowThreshold = volatilityLowThreshold;
            return this;
        }

        public Builder volatilityHighThreshold(String volatilityHighThreshold) {
            this.volatilityHighThreshold = volatilityHighThreshold;
            return this;
        }

        public Builder liquidityALong(String liquidityALong) {
            this.liquidityALong = liquidityALong;
            return this;
        }

        public Builder liquidityAShort(String liquidityAShort) {
            this.liquidityAShort = liquidityAShort;
            return this;
        }

        public Builder liquidityBShort(String liquidityBShort) {
            this.liquidityBShort = liquidityBShort;
            return this;
        }

        public Builder liquidityThreshold(String liquidityThreshold) {
            this.liquidityThreshold = liquidityThreshold;
            return this;
        }

        public Builder basisOffsetCreditRate(String basisOffsetCreditRate) {
            this.basisOffsetCreditRate = basisOffsetCreditRate;
            return this;
        }

        public CrossMarginRiskParameters build() {
            return new CrossMarginRiskParameters(this);
        }
    }
}

