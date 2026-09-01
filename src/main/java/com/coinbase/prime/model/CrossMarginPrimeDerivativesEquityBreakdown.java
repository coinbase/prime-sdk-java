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

/** Breakdown of the components of derivatives equity. */
public class CrossMarginPrimeDerivativesEquityBreakdown {
    /**
     * Derivatives cash balance component.
     */
    @JsonProperty("cash_balance")
    private String cashBalance;

    /**
     * Unrealized PnL component of derivatives equity.
     */
    @JsonProperty("unrealized_pnl")
    private String unrealizedPnl;

    /**
     * Realized PnL component of derivatives equity.
     */
    @JsonProperty("realized_pnl")
    private String realizedPnl;

    /**
     * Accrued funding PnL component of derivatives equity.
     */
    @JsonProperty("accrued_funding_pnl")
    private String accruedFundingPnl;

    public CrossMarginPrimeDerivativesEquityBreakdown() {
    }

    public CrossMarginPrimeDerivativesEquityBreakdown(Builder builder) {
        this.cashBalance = builder.cashBalance;
        this.unrealizedPnl = builder.unrealizedPnl;
        this.realizedPnl = builder.realizedPnl;
        this.accruedFundingPnl = builder.accruedFundingPnl;
    }
    public String getCashBalance() {
        return cashBalance;
    }

    public void setCashBalance(String cashBalance) {
        this.cashBalance = cashBalance;
    }
    public String getUnrealizedPnl() {
        return unrealizedPnl;
    }

    public void setUnrealizedPnl(String unrealizedPnl) {
        this.unrealizedPnl = unrealizedPnl;
    }
    public String getRealizedPnl() {
        return realizedPnl;
    }

    public void setRealizedPnl(String realizedPnl) {
        this.realizedPnl = realizedPnl;
    }
    public String getAccruedFundingPnl() {
        return accruedFundingPnl;
    }

    public void setAccruedFundingPnl(String accruedFundingPnl) {
        this.accruedFundingPnl = accruedFundingPnl;
    }
    public static class Builder {
        private String cashBalance;

        private String unrealizedPnl;

        private String realizedPnl;

        private String accruedFundingPnl;

        public Builder cashBalance(String cashBalance) {
            this.cashBalance = cashBalance;
            return this;
        }

        public Builder unrealizedPnl(String unrealizedPnl) {
            this.unrealizedPnl = unrealizedPnl;
            return this;
        }

        public Builder realizedPnl(String realizedPnl) {
            this.realizedPnl = realizedPnl;
            return this;
        }

        public Builder accruedFundingPnl(String accruedFundingPnl) {
            this.accruedFundingPnl = accruedFundingPnl;
            return this;
        }

        public CrossMarginPrimeDerivativesEquityBreakdown build() {
            return new CrossMarginPrimeDerivativesEquityBreakdown(this);
        }
    }
}

