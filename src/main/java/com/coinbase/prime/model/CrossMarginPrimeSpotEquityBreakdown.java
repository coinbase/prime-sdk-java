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

/** Breakdown of the components of spot equity. */
public class CrossMarginPrimeSpotEquityBreakdown {
    /**
     * PM cash balance component of spot equity.
     */
    @JsonProperty("cash_balance")
    private String cashBalance;

    /**
     * Long market value component of spot equity.
     */
    @JsonProperty("long_market_value")
    private String longMarketValue;

    /**
     * Short market value component of spot equity.
     */
    @JsonProperty("short_market_value")
    private String shortMarketValue;

    /**
     * Short collateral component of spot equity.
     */
    @JsonProperty("short_collateral")
    private String shortCollateral;

    /**
     * Pending transfers affecting spot equity.
     */
    @JsonProperty("pending_transfers")
    private String pendingTransfers;

    public CrossMarginPrimeSpotEquityBreakdown() {
    }

    public CrossMarginPrimeSpotEquityBreakdown(Builder builder) {
        this.cashBalance = builder.cashBalance;
        this.longMarketValue = builder.longMarketValue;
        this.shortMarketValue = builder.shortMarketValue;
        this.shortCollateral = builder.shortCollateral;
        this.pendingTransfers = builder.pendingTransfers;
    }
    public String getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(String cashBalance) {
        this.cashBalance = cashBalance;
    }
    public String getLongMarketValue() {
        return longMarketValue;
    }

    public void setLongMarketValue(String longMarketValue) {
        this.longMarketValue = longMarketValue;
    }
    public String getShortMarketValue() {
        return shortMarketValue;
    }

    public void setShortMarketValue(String shortMarketValue) {
        this.shortMarketValue = shortMarketValue;
    }
    public String getShortCollateral() {
        return shortCollateral;
    }

    public void setShortCollateral(String shortCollateral) {
        this.shortCollateral = shortCollateral;
    }
    public String getPendingTransfers() {
        return pendingTransfers;
    }

    public void setPendingTransfers(String pendingTransfers) {
        this.pendingTransfers = pendingTransfers;
    }
    public static class Builder {
        private String cashBalance;

        private String longMarketValue;

        private String shortMarketValue;

        private String shortCollateral;

        private String pendingTransfers;

        public Builder cashBalance(String cashBalance) {
            this.cashBalance = cashBalance;
            return this;
        }

        public Builder longMarketValue(String longMarketValue) {
            this.longMarketValue = longMarketValue;
            return this;
        }

        public Builder shortMarketValue(String shortMarketValue) {
            this.shortMarketValue = shortMarketValue;
            return this;
        }

        public Builder shortCollateral(String shortCollateral) {
            this.shortCollateral = shortCollateral;
            return this;
        }

        public Builder pendingTransfers(String pendingTransfers) {
            this.pendingTransfers = pendingTransfers;
            return this;
        }

        public CrossMarginPrimeSpotEquityBreakdown build() {
            return new CrossMarginPrimeSpotEquityBreakdown(this);
        }
    }
}

