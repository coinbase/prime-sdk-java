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

/** CrossMarginPrimeXMPosition is a single per-asset XM row (proto-backed fields from XMPositionDetails). */
public class CrossMarginPrimeXMPosition {
    /**
     * Position currency
     */
    private String currency;

    /**
     * Current market price
     */
    @JsonProperty("market_price")
    private String marketPrice;

    /**
     * XM spot balance nominal
     */
    @JsonProperty("spot_balance")
    private String spotBalance;

    /**
     * XM spot balance notional
     */
    @JsonProperty("spot_balance_notional")
    private String spotBalanceNotional;

    /**
     * XM futures balance nominal
     */
    @JsonProperty("futures_balance")
    private String futuresBalance;

    /**
     * XM futures balance notional
     */
    @JsonProperty("futures_balance_notional")
    private String futuresBalanceNotional;

    /**
     * Base margin requirement notional
     */
    @JsonProperty("base_requirement")
    private String baseRequirement;

    /**
     * Total margin required
     */
    @JsonProperty("total_position_margin")
    private String totalPositionMargin;

    /**
     * Basis offset credit applied to this asset row.
     */
    @JsonProperty("basis_credit")
    private String basisCredit;

    /**
     * Post-netting USD notional for futures on this asset
     */
    @JsonProperty("futures_netted_notional")
    private String futuresNettedNotional;

    /**
     * Margin attributed to futures netting for this asset row.
     */
    @JsonProperty("futures_netting_margin")
    private String futuresNettingMargin;

    /**
     * Per-asset long amount from position_summary.
     */
    @JsonProperty("long_amount")
    private String longAmount;

    /**
     * Per-asset short amount from position_summary.
     */
    @JsonProperty("short_amount")
    private String shortAmount;

    /**
     * Volatility margin add-on for this asset.
     */
    @JsonProperty("volatility_addon")
    private String volatilityAddon;

    /**
     * Liquidity margin add-on for this asset.
     */
    @JsonProperty("liquidity_addon")
    private String liquidityAddon;

    public CrossMarginPrimeXMPosition() {
    }

    public CrossMarginPrimeXMPosition(Builder builder) {
        this.currency = builder.currency;
        this.marketPrice = builder.marketPrice;
        this.spotBalance = builder.spotBalance;
        this.spotBalanceNotional = builder.spotBalanceNotional;
        this.futuresBalance = builder.futuresBalance;
        this.futuresBalanceNotional = builder.futuresBalanceNotional;
        this.baseRequirement = builder.baseRequirement;
        this.totalPositionMargin = builder.totalPositionMargin;
        this.basisCredit = builder.basisCredit;
        this.futuresNettedNotional = builder.futuresNettedNotional;
        this.futuresNettingMargin = builder.futuresNettingMargin;
        this.longAmount = builder.longAmount;
        this.shortAmount = builder.shortAmount;
        this.volatilityAddon = builder.volatilityAddon;
        this.liquidityAddon = builder.liquidityAddon;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(String marketPrice) {
        this.marketPrice = marketPrice;
    }
    public String getSpotBalance() {
        return spotBalance;
    }

    public void setSpotBalance(String spotBalance) {
        this.spotBalance = spotBalance;
    }
    public String getSpotBalanceNotional() {
        return spotBalanceNotional;
    }

    public void setSpotBalanceNotional(String spotBalanceNotional) {
        this.spotBalanceNotional = spotBalanceNotional;
    }
    public String getFuturesBalance() {
        return futuresBalance;
    }

    public void setFuturesBalance(String futuresBalance) {
        this.futuresBalance = futuresBalance;
    }
    public String getFuturesBalanceNotional() {
        return futuresBalanceNotional;
    }

    public void setFuturesBalanceNotional(String futuresBalanceNotional) {
        this.futuresBalanceNotional = futuresBalanceNotional;
    }
    public String getBaseRequirement() {
        return baseRequirement;
    }

    public void setBaseRequirement(String baseRequirement) {
        this.baseRequirement = baseRequirement;
    }
    public String getTotalPositionMargin() {
        return totalPositionMargin;
    }

    public void setTotalPositionMargin(String totalPositionMargin) {
        this.totalPositionMargin = totalPositionMargin;
    }
    public String getBasisCredit() {
        return basisCredit;
    }

    public void setBasisCredit(String basisCredit) {
        this.basisCredit = basisCredit;
    }
    public String getFuturesNettedNotional() {
        return futuresNettedNotional;
    }

    public void setFuturesNettedNotional(String futuresNettedNotional) {
        this.futuresNettedNotional = futuresNettedNotional;
    }
    public String getFuturesNettingMargin() {
        return futuresNettingMargin;
    }

    public void setFuturesNettingMargin(String futuresNettingMargin) {
        this.futuresNettingMargin = futuresNettingMargin;
    }
    public String getLongAmount() {
        return longAmount;
    }

    public void setLongAmount(String longAmount) {
        this.longAmount = longAmount;
    }
    public String getShortAmount() {
        return shortAmount;
    }

    public void setShortAmount(String shortAmount) {
        this.shortAmount = shortAmount;
    }
    public String getVolatilityAddon() {
        return volatilityAddon;
    }

    public void setVolatilityAddon(String volatilityAddon) {
        this.volatilityAddon = volatilityAddon;
    }
    public String getLiquidityAddon() {
        return liquidityAddon;
    }

    public void setLiquidityAddon(String liquidityAddon) {
        this.liquidityAddon = liquidityAddon;
    }
    public static class Builder {
        private String currency;

        private String marketPrice;

        private String spotBalance;

        private String spotBalanceNotional;

        private String futuresBalance;

        private String futuresBalanceNotional;

        private String baseRequirement;

        private String totalPositionMargin;

        private String basisCredit;

        private String futuresNettedNotional;

        private String futuresNettingMargin;

        private String longAmount;

        private String shortAmount;

        private String volatilityAddon;

        private String liquidityAddon;

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder marketPrice(String marketPrice) {
            this.marketPrice = marketPrice;
            return this;
        }

        public Builder spotBalance(String spotBalance) {
            this.spotBalance = spotBalance;
            return this;
        }

        public Builder spotBalanceNotional(String spotBalanceNotional) {
            this.spotBalanceNotional = spotBalanceNotional;
            return this;
        }

        public Builder futuresBalance(String futuresBalance) {
            this.futuresBalance = futuresBalance;
            return this;
        }

        public Builder futuresBalanceNotional(String futuresBalanceNotional) {
            this.futuresBalanceNotional = futuresBalanceNotional;
            return this;
        }

        public Builder baseRequirement(String baseRequirement) {
            this.baseRequirement = baseRequirement;
            return this;
        }

        public Builder totalPositionMargin(String totalPositionMargin) {
            this.totalPositionMargin = totalPositionMargin;
            return this;
        }

        public Builder basisCredit(String basisCredit) {
            this.basisCredit = basisCredit;
            return this;
        }

        public Builder futuresNettedNotional(String futuresNettedNotional) {
            this.futuresNettedNotional = futuresNettedNotional;
            return this;
        }

        public Builder futuresNettingMargin(String futuresNettingMargin) {
            this.futuresNettingMargin = futuresNettingMargin;
            return this;
        }

        public Builder longAmount(String longAmount) {
            this.longAmount = longAmount;
            return this;
        }

        public Builder shortAmount(String shortAmount) {
            this.shortAmount = shortAmount;
            return this;
        }

        public Builder volatilityAddon(String volatilityAddon) {
            this.volatilityAddon = volatilityAddon;
            return this;
        }

        public Builder liquidityAddon(String liquidityAddon) {
            this.liquidityAddon = liquidityAddon;
            return this;
        }

        public CrossMarginPrimeXMPosition build() {
            return new CrossMarginPrimeXMPosition(this);
        }
    }
}

