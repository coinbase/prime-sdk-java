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

package com.coinbase.prime.staking;

import com.coinbase.prime.model.ValidatorUnstakePreview;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * PreviewUnstakeResponse contains the response data from previewing an unstaking operation.
 */
public class PreviewUnstakeResponse {
    /**
     * Estimated amount that would be unstaked
     */
    @JsonProperty("estimated_amount")
    private String estimatedAmount;

    /**
     * The wallet ID
     */
    @JsonProperty("wallet_id")
    private String walletId;

    /**
     * The blockchain address of the wallet
     */
    @JsonProperty("wallet_address")
    private String walletAddress;

    /**
     * Timestamp at which this preview was generated (ISO 8601)
     */
    @JsonProperty("current_timestamp")
    private String currentTimestamp;

    /**
     * Per-validator breakdown of the unstake simulation
     */
    @JsonProperty("validators")
    private ValidatorUnstakePreview[] validators;

    public PreviewUnstakeResponse() {
    }

    public String getEstimatedAmount() {
        return estimatedAmount;
    }

    public void setEstimatedAmount(String estimatedAmount) {
        this.estimatedAmount = estimatedAmount;
    }

    public String getWalletId() {
        return walletId;
    }

    public void setWalletId(String walletId) {
        this.walletId = walletId;
    }

    public String getWalletAddress() {
        return walletAddress;
    }

    public void setWalletAddress(String walletAddress) {
        this.walletAddress = walletAddress;
    }

    public String getCurrentTimestamp() {
        return currentTimestamp;
    }

    public void setCurrentTimestamp(String currentTimestamp) {
        this.currentTimestamp = currentTimestamp;
    }

    public ValidatorUnstakePreview[] getValidators() {
        return validators;
    }

    public void setValidators(ValidatorUnstakePreview[] validators) {
        this.validators = validators;
    }

}
