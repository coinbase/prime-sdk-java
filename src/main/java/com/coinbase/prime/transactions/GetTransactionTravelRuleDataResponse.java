/*
 * Copyright 2026-present Coinbase Global, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.coinbase.prime.transactions;

import com.coinbase.prime.model.TravelRuleParty;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Response containing fulfilled travel rule data for a transaction
 */
public class GetTransactionTravelRuleDataResponse {
    /**
     * Whether data requirements are fulfilled
     */
    @JsonProperty("fulfilled")
    private Boolean fulfilled;

    /**
     * Whether the transfer is to/from a self-owned wallet
     */
    @JsonProperty("is_self")
    private Boolean isSelf;

    /**
     * Represents a party in a travel rule transfer (originator or beneficiary).
     */
    @JsonProperty("originator")
    private TravelRuleParty originator;

    /**
     * Represents a party in a travel rule transfer (originator or beneficiary).
     */
    @JsonProperty("beneficiary")
    private TravelRuleParty beneficiary;

    /**
     * The crypto amount of the transaction (e.g. "1.23 BTC")
     */
    @JsonProperty("amount")
    private String amount;

    /**
     * The currency of the crypto amount
     */
    @JsonProperty("amount_currency")
    private String amountCurrency;

    /**
     * The fiat amount of the transaction (e.g. "123.45 USD")
     */
    @JsonProperty("fiat_amount")
    private String fiatAmount;

    /**
     * The currency of the fiat amount
     */
    @JsonProperty("fiat_amount_currency")
    private String fiatAmountCurrency;

    /**
     * The blockchain network for the transaction
     */
    @JsonProperty("blockchain_network")
    private String blockchainNetwork;

    public GetTransactionTravelRuleDataResponse() {
    }

    public Boolean getFulfilled() {
        return fulfilled;
    }

    public void setFulfilled(Boolean fulfilled) {
        this.fulfilled = fulfilled;
    }

    public Boolean getIsSelf() {
        return isSelf;
    }

    public void setIsSelf(Boolean isSelf) {
        this.isSelf = isSelf;
    }

    public TravelRuleParty getOriginator() {
        return originator;
    }

    public void setOriginator(TravelRuleParty originator) {
        this.originator = originator;
    }

    public TravelRuleParty getBeneficiary() {
        return beneficiary;
    }

    public void setBeneficiary(TravelRuleParty beneficiary) {
        this.beneficiary = beneficiary;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getAmountCurrency() {
        return amountCurrency;
    }

    public void setAmountCurrency(String amountCurrency) {
        this.amountCurrency = amountCurrency;
    }

    public String getFiatAmount() {
        return fiatAmount;
    }

    public void setFiatAmount(String fiatAmount) {
        this.fiatAmount = fiatAmount;
    }

    public String getFiatAmountCurrency() {
        return fiatAmountCurrency;
    }

    public void setFiatAmountCurrency(String fiatAmountCurrency) {
        this.fiatAmountCurrency = fiatAmountCurrency;
    }

    public String getBlockchainNetwork() {
        return blockchainNetwork;
    }

    public void setBlockchainNetwork(String blockchainNetwork) {
        this.blockchainNetwork = blockchainNetwork;
    }

}
