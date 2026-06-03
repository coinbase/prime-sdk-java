/*
 * Copyright 2025-present Coinbase Global, Inc.
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

import com.coinbase.prime.model.BlockchainAddress;
import com.coinbase.prime.model.CounterpartyDestination;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Create Withdrawal
 */
public class CreateWalletWithdrawalResponse {
    /**
     * The activity ID associated with the withdrawal
     */
    @JsonProperty("activity_id")
    private String activityId;

    /**
     * A URL to the activity in the Prime application
     */
    @JsonProperty("approval_url")
    private String approvalUrl;

    /**
     * The currency symbol associated with the withdrawal
     */
    @JsonProperty("symbol")
    private String symbol;

    /**
     * The amount of the withdrawal
     */
    @JsonProperty("amount")
    private String amount;

    /**
     * The network fee associated with the withdrawal
     */
    @JsonProperty("fee")
    private String fee;

    /**
     * The destination type used for the withdrawal
     */
    @JsonProperty("destination_type")
    private String destinationType;

    /**
     * The source type used for the withdrawal
     */
    @JsonProperty("source_type")
    private String sourceType;

    @JsonProperty("blockchain_destination")
    private BlockchainAddress blockchainDestination;

    /**
     * Represents a destination for a counterparty payment
     */
    @JsonProperty("counterparty_destination")
    private CounterpartyDestination counterpartyDestination;

    @JsonProperty("blockchain_source")
    private BlockchainAddress blockchainSource;

    /**
     * The id of the just created transaction
     */
    @JsonProperty("transaction_id")
    private String transactionId;

    public CreateWalletWithdrawalResponse() {
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public String getApprovalUrl() {
        return approvalUrl;
    }

    public void setApprovalUrl(String approvalUrl) {
        this.approvalUrl = approvalUrl;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getFee() {
        return fee;
    }

    public void setFee(String fee) {
        this.fee = fee;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public void setDestinationType(String destinationType) {
        this.destinationType = destinationType;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public BlockchainAddress getBlockchainDestination() {
        return blockchainDestination;
    }

    public void setBlockchainDestination(BlockchainAddress blockchainDestination) {
        this.blockchainDestination = blockchainDestination;
    }

    public CounterpartyDestination getCounterpartyDestination() {
        return counterpartyDestination;
    }

    public void setCounterpartyDestination(CounterpartyDestination counterpartyDestination) {
        this.counterpartyDestination = counterpartyDestination;
    }

    public BlockchainAddress getBlockchainSource() {
        return blockchainSource;
    }

    public void setBlockchainSource(BlockchainAddress blockchainSource) {
        this.blockchainSource = blockchainSource;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

}
