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
import com.coinbase.prime.model.enums.XMLiquidationStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

/** ActiveLiquidationSummary provides a summary of the active or most recent XM liquidation */
public class ActiveLiquidationSummary {
    /**
     * Financing liquidation UUID
     */
    @JsonProperty("liquidation_id")
    private String liquidationId;

    /**
     * - XM_LIQUIDATION_STATUS_PRE_LIQUIDATION: Liquidation is in the pre-liquidation phase
     * - XM_LIQUIDATION_STATUS_LIQUIDATING: Liquidation is actively in progress
     * - XM_LIQUIDATION_STATUS_LIQUIDATED: Liquidation has completed successfully
     * - XM_LIQUIDATION_STATUS_CANCELED: Liquidation was canceled
     * - XM_LIQUIDATION_STATUS_FAILED: Liquidation failed
     */
    private XMLiquidationStatus status;

    /**
     * USD notional shortfall amount that triggered the liquidation
     */
    @JsonProperty("shortfall_amount")
    private String shortfallAmount;

    public ActiveLiquidationSummary() {
    }

    public ActiveLiquidationSummary(Builder builder) {
        this.liquidationId = builder.liquidationId;
        this.status = builder.status;
        this.shortfallAmount = builder.shortfallAmount;
    }
    public String getLiquidationId() {
        return liquidationId;
    }

    public void setLiquidationId(String liquidationId) {
        this.liquidationId = liquidationId;
    }
    public XMLiquidationStatus getStatus() {
        return status;
    }

    public void setStatus(XMLiquidationStatus status) {
        this.status = status;
    }
    public String getShortfallAmount() {
        return shortfallAmount;
    }

    public void setShortfallAmount(String shortfallAmount) {
        this.shortfallAmount = shortfallAmount;
    }
    public static class Builder {
        private String liquidationId;

        private XMLiquidationStatus status;

        private String shortfallAmount;

        public Builder liquidationId(String liquidationId) {
            this.liquidationId = liquidationId;
            return this;
        }

        public Builder status(XMLiquidationStatus status) {
            this.status = status;
            return this;
        }

        public Builder shortfallAmount(String shortfallAmount) {
            this.shortfallAmount = shortfallAmount;
            return this;
        }

        public ActiveLiquidationSummary build() {
            return new ActiveLiquidationSummary(this);
        }
    }
}

