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
import com.coinbase.prime.model.PriceIncrementStep;
import com.coinbase.prime.model.enums.SettlementModel;
import com.coinbase.prime.model.enums.SettlementPeriod;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;

/** OptionProductDetails contains details specific to option products */
public class OptionProductDetails {
    /**
     * The type of an option position.
     * - OPTION_TYPE_UNSPECIFIED: Unspecified option type.
     * - OPTION_TYPE_CALL: Call option.
     * - OPTION_TYPE_PUT: Put option.
     */
    @JsonProperty("option_type")
    private OptionType optionType;

    /**
     * Strike price of the option
     */
    private String strike;

    /**
     * Contract root unit (underlying asset)
     */
    @JsonProperty("contract_root_unit")
    private String contractRootUnit;

    /**
     * Currency the option settles in
     */
    @JsonProperty("settlement_currency")
    private String settlementCurrency;

    /**
     * Contract code identifier
     */
    @JsonProperty("contract_code")
    private String contractCode;

    /**
     * Descriptive name for the product group
     */
    @JsonProperty("group_description")
    private String groupDescription;

    /**
     * Contract size
     */
    @JsonProperty("contract_size")
    private String contractSize;

    /**
     * Contract expiry timestamp
     */
    @JsonProperty("contract_expiry")
    private OffsetDateTime contractExpiry;

    /**
     * - SETTLEMENT_PERIOD_UNSPECIFIED: Unspecified settlement period
     * - SETTLEMENT_PERIOD_PERPETUAL: Contract never expires
     * - SETTLEMENT_PERIOD_DAY: Contract expires daily
     * - SETTLEMENT_PERIOD_WEEK: Contract expires weekly
     * - SETTLEMENT_PERIOD_MONTH: Contract expires monthly
     */
    @JsonProperty("settlement_period")
    private SettlementPeriod settlementPeriod;

    /**
     * - SETTLEMENT_MODEL_UNSPECIFIED: Unspecified settlement model
     * - SETTLEMENT_MODEL_LINEAR: Settles in the quote currency
     * - SETTLEMENT_MODEL_INVERSE: Settles in the base currency
     */
    @JsonProperty("settlement_model")
    private SettlementModel settlementModel;

    /**
     * Currency the option is quoted against
     */
    @JsonProperty("counter_currency")
    private String counterCurrency;

    /**
     * Smallest tradable quantity of the contract
     */
    @JsonProperty("lot_size")
    private String lotSize;

    /**
     * Tiered price increments that override price_increment above a threshold; empty when a single price_increment applies
     */
    @JsonProperty("price_increment_steps")
    private List<PriceIncrementStep> priceIncrementSteps;

    public OptionProductDetails() {
    }

    public OptionProductDetails(Builder builder) {
        this.optionType = builder.optionType;
        this.strike = builder.strike;
        this.contractRootUnit = builder.contractRootUnit;
        this.settlementCurrency = builder.settlementCurrency;
        this.contractCode = builder.contractCode;
        this.groupDescription = builder.groupDescription;
        this.contractSize = builder.contractSize;
        this.contractExpiry = builder.contractExpiry;
        this.settlementPeriod = builder.settlementPeriod;
        this.settlementModel = builder.settlementModel;
        this.counterCurrency = builder.counterCurrency;
        this.lotSize = builder.lotSize;
        this.priceIncrementSteps = builder.priceIncrementSteps;
    }
    public OptionType getOptionType() {
        return optionType;
    }

    public void setOptionType(OptionType optionType) {
        this.optionType = optionType;
    }
    public String getStrike() {
        return strike;
    }

    public void setStrike(String strike) {
        this.strike = strike;
    }
    public String getContractRootUnit() {
        return contractRootUnit;
    }

    public void setContractRootUnit(String contractRootUnit) {
        this.contractRootUnit = contractRootUnit;
    }
    public String getSettlementCurrency() {
        return settlementCurrency;
    }

    public void setSettlementCurrency(String settlementCurrency) {
        this.settlementCurrency = settlementCurrency;
    }
    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }
    public String getGroupDescription() {
        return groupDescription;
    }

    public void setGroupDescription(String groupDescription) {
        this.groupDescription = groupDescription;
    }
    public String getContractSize() {
        return contractSize;
    }

    public void setContractSize(String contractSize) {
        this.contractSize = contractSize;
    }
    public OffsetDateTime getContractExpiry() {
        return contractExpiry;
    }

    public void setContractExpiry(OffsetDateTime contractExpiry) {
        this.contractExpiry = contractExpiry;
    }
    public SettlementPeriod getSettlementPeriod() {
        return settlementPeriod;
    }

    public void setSettlementPeriod(SettlementPeriod settlementPeriod) {
        this.settlementPeriod = settlementPeriod;
    }
    public SettlementModel getSettlementModel() {
        return settlementModel;
    }

    public void setSettlementModel(SettlementModel settlementModel) {
        this.settlementModel = settlementModel;
    }
    public String getCounterCurrency() {
        return counterCurrency;
    }

    public void setCounterCurrency(String counterCurrency) {
        this.counterCurrency = counterCurrency;
    }
    public String getLotSize() {
        return lotSize;
    }

    public void setLotSize(String lotSize) {
        this.lotSize = lotSize;
    }
    public List<PriceIncrementStep> getPriceIncrementSteps() {
        return priceIncrementSteps;
    }

    public void setPriceIncrementSteps(List<PriceIncrementStep> priceIncrementSteps) {
        this.priceIncrementSteps = priceIncrementSteps;
    }
    public static class Builder {
        private OptionType optionType;

        private String strike;

        private String contractRootUnit;

        private String settlementCurrency;

        private String contractCode;

        private String groupDescription;

        private String contractSize;

        private OffsetDateTime contractExpiry;

        private SettlementPeriod settlementPeriod;

        private SettlementModel settlementModel;

        private String counterCurrency;

        private String lotSize;

        private List<PriceIncrementStep> priceIncrementSteps;

        public Builder optionType(OptionType optionType) {
            this.optionType = optionType;
            return this;
        }

        public Builder strike(String strike) {
            this.strike = strike;
            return this;
        }

        public Builder contractRootUnit(String contractRootUnit) {
            this.contractRootUnit = contractRootUnit;
            return this;
        }

        public Builder settlementCurrency(String settlementCurrency) {
            this.settlementCurrency = settlementCurrency;
            return this;
        }

        public Builder contractCode(String contractCode) {
            this.contractCode = contractCode;
            return this;
        }

        public Builder groupDescription(String groupDescription) {
            this.groupDescription = groupDescription;
            return this;
        }

        public Builder contractSize(String contractSize) {
            this.contractSize = contractSize;
            return this;
        }

        public Builder contractExpiry(OffsetDateTime contractExpiry) {
            this.contractExpiry = contractExpiry;
            return this;
        }

        public Builder settlementPeriod(SettlementPeriod settlementPeriod) {
            this.settlementPeriod = settlementPeriod;
            return this;
        }

        public Builder settlementModel(SettlementModel settlementModel) {
            this.settlementModel = settlementModel;
            return this;
        }

        public Builder counterCurrency(String counterCurrency) {
            this.counterCurrency = counterCurrency;
            return this;
        }

        public Builder lotSize(String lotSize) {
            this.lotSize = lotSize;
            return this;
        }

        public Builder priceIncrementSteps(List<PriceIncrementStep> priceIncrementSteps) {
            this.priceIncrementSteps = priceIncrementSteps;
            return this;
        }

        public OptionProductDetails build() {
            return new OptionProductDetails(this);
        }
    }
}

