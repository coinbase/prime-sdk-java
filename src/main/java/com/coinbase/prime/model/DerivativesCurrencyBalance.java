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
import com.coinbase.prime.model.enums.FcmMarginHealthState;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Balances for a single settlement currency within an international derivatives portfolio. */
public class DerivativesCurrencyBalance {
    /**
     * Settlement currency
     */
    private String currency;

    /**
     * Cash balance for the currency
     */
    private String balance;

    /**
     * Unrealized PNL
     */
    @JsonProperty("unrealized_pnl")
    private String unrealizedPnl;

    /**
     * Realized PNL
     */
    @JsonProperty("realized_pnl")
    private String realizedPnl;

    /**
     * Initial margin
     */
    @JsonProperty("initial_margin")
    private String initialMargin;

    /**
     * Maintenance margin
     */
    @JsonProperty("maintenance_margin")
    private String maintenanceMargin;

    /**
     * Margin balance for the currency
     */
    @JsonProperty("margin_balance")
    private String marginBalance;

    /**
     * Option value for the currency
     */
    @JsonProperty("option_value")
    private String optionValue;

    /**
     * Margin excess for the currency (negative indicates a margin deficit)
     */
    @JsonProperty("margin_excess")
    private String marginExcess;

    /**
     * Margin utilization for the currency, as a percentage
     */
    @JsonProperty("margin_utilization")
    private String marginUtilization;

    /**
     * The margin health state of an FCM account.
     * - FCM_MARGIN_HEALTH_STATE_UNSPECIFIED: Unspecified margin health state.
     * - FCM_MARGIN_HEALTH_STATE_HEALTHY: Account margin is healthy.
     * - FCM_MARGIN_HEALTH_STATE_RESTRICTED: Account margin is restricted.
     * - FCM_MARGIN_HEALTH_STATE_PRE_LIQUIDATION: Account is approaching liquidation.
     * - FCM_MARGIN_HEALTH_STATE_LIQUIDATION: Account is in liquidation.
     */
    @JsonProperty("margin_health_state")
    private FcmMarginHealthState marginHealthState;

    public DerivativesCurrencyBalance() {
    }

    public DerivativesCurrencyBalance(Builder builder) {
        this.currency = builder.currency;
        this.balance = builder.balance;
        this.unrealizedPnl = builder.unrealizedPnl;
        this.realizedPnl = builder.realizedPnl;
        this.initialMargin = builder.initialMargin;
        this.maintenanceMargin = builder.maintenanceMargin;
        this.marginBalance = builder.marginBalance;
        this.optionValue = builder.optionValue;
        this.marginExcess = builder.marginExcess;
        this.marginUtilization = builder.marginUtilization;
        this.marginHealthState = builder.marginHealthState;
    }
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
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
    public String getInitialMargin() {
        return initialMargin;
    }

    public void setInitialMargin(String initialMargin) {
        this.initialMargin = initialMargin;
    }
    public String getMaintenanceMargin() {
        return maintenanceMargin;
    }

    public void setMaintenanceMargin(String maintenanceMargin) {
        this.maintenanceMargin = maintenanceMargin;
    }
    public String getMarginBalance() {
        return marginBalance;
    }

    public void setMarginBalance(String marginBalance) {
        this.marginBalance = marginBalance;
    }
    public String getOptionValue() {
        return optionValue;
    }

    public void setOptionValue(String optionValue) {
        this.optionValue = optionValue;
    }
    public String getMarginExcess() {
        return marginExcess;
    }

    public void setMarginExcess(String marginExcess) {
        this.marginExcess = marginExcess;
    }
    public String getMarginUtilization() {
        return marginUtilization;
    }

    public void setMarginUtilization(String marginUtilization) {
        this.marginUtilization = marginUtilization;
    }
    public FcmMarginHealthState getMarginHealthState() {
        return marginHealthState;
    }

    public void setMarginHealthState(FcmMarginHealthState marginHealthState) {
        this.marginHealthState = marginHealthState;
    }
    public static class Builder {
        private String currency;

        private String balance;

        private String unrealizedPnl;

        private String realizedPnl;

        private String initialMargin;

        private String maintenanceMargin;

        private String marginBalance;

        private String optionValue;

        private String marginExcess;

        private String marginUtilization;

        private FcmMarginHealthState marginHealthState;

        public Builder currency(String currency) {
            this.currency = currency;
            return this;
        }

        public Builder balance(String balance) {
            this.balance = balance;
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

        public Builder initialMargin(String initialMargin) {
            this.initialMargin = initialMargin;
            return this;
        }

        public Builder maintenanceMargin(String maintenanceMargin) {
            this.maintenanceMargin = maintenanceMargin;
            return this;
        }

        public Builder marginBalance(String marginBalance) {
            this.marginBalance = marginBalance;
            return this;
        }

        public Builder optionValue(String optionValue) {
            this.optionValue = optionValue;
            return this;
        }

        public Builder marginExcess(String marginExcess) {
            this.marginExcess = marginExcess;
            return this;
        }

        public Builder marginUtilization(String marginUtilization) {
            this.marginUtilization = marginUtilization;
            return this;
        }

        public Builder marginHealthState(FcmMarginHealthState marginHealthState) {
            this.marginHealthState = marginHealthState;
            return this;
        }

        public DerivativesCurrencyBalance build() {
            return new DerivativesCurrencyBalance(this);
        }
    }
}

