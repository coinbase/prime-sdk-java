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

package com.coinbase.prime.futures;

import com.coinbase.prime.model.enums.FcmMarginHealthState;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Get FCM Risk Limits
 */
public class GetFcmRiskLimitsResponse {
    /**
     * Risk Limit set for a client
     */
    @JsonProperty("cfm_risk_limit")
    private String cfmRiskLimit;

    /**
     * Limit utilization calculated based on total margin and PnLs
     */
    @JsonProperty("cfm_risk_limit_utilization")
    private String cfmRiskLimitUtilization;

    /**
     * The total margin required for both positions and open orders
     */
    @JsonProperty("cfm_total_margin")
    private String cfmTotalMargin;

    /**
     * Open Trade Equity accrued during the current trading session
     */
    @JsonProperty("cfm_delta_ote")
    private String cfmDeltaOte;

    /**
     * Unsettled realized PNL for positions closed intraday
     */
    @JsonProperty("cfm_unsettled_realized_pnl")
    private String cfmUnsettledRealizedPnl;

    /**
     * Unsettled accrued funding PNL from the last settlement
     */
    @JsonProperty("cfm_unsettled_accrued_funding_pnl")
    private String cfmUnsettledAccruedFundingPnl;

    /**
     * Margin utilization as a decimal percentage between 0 and 1 (e.g. 0.5 means 50%)
     */
    @JsonProperty("margin_utilization_percent")
    private String marginUtilizationPercent;

    /**
     * The margin health state of an FCM account. - FCM_MARGIN_HEALTH_STATE_UNSPECIFIED: Unspecified margin health state. - FCM_MARGIN_HEALTH_STATE_HEALTHY: Account margin is healthy. - FCM_MARGIN_HEALTH_STATE_RESTRICTED: Account margin is restricted. - FCM_MARGIN_HEALTH_STATE_PRE_LIQUIDATION: Account is approaching liquidation. - FCM_MARGIN_HEALTH_STATE_LIQUIDATION: Account is in liquidation.
     */
    @JsonProperty("margin_health_state")
    private FcmMarginHealthState marginHealthState;

    public GetFcmRiskLimitsResponse() {
    }

    public String getCfmRiskLimit() {
        return cfmRiskLimit;
    }

    public void setCfmRiskLimit(String cfmRiskLimit) {
        this.cfmRiskLimit = cfmRiskLimit;
    }

    public String getCfmRiskLimitUtilization() {
        return cfmRiskLimitUtilization;
    }

    public void setCfmRiskLimitUtilization(String cfmRiskLimitUtilization) {
        this.cfmRiskLimitUtilization = cfmRiskLimitUtilization;
    }

    public String getCfmTotalMargin() {
        return cfmTotalMargin;
    }

    public void setCfmTotalMargin(String cfmTotalMargin) {
        this.cfmTotalMargin = cfmTotalMargin;
    }

    public String getCfmDeltaOte() {
        return cfmDeltaOte;
    }

    public void setCfmDeltaOte(String cfmDeltaOte) {
        this.cfmDeltaOte = cfmDeltaOte;
    }

    public String getCfmUnsettledRealizedPnl() {
        return cfmUnsettledRealizedPnl;
    }

    public void setCfmUnsettledRealizedPnl(String cfmUnsettledRealizedPnl) {
        this.cfmUnsettledRealizedPnl = cfmUnsettledRealizedPnl;
    }

    public String getCfmUnsettledAccruedFundingPnl() {
        return cfmUnsettledAccruedFundingPnl;
    }

    public void setCfmUnsettledAccruedFundingPnl(String cfmUnsettledAccruedFundingPnl) {
        this.cfmUnsettledAccruedFundingPnl = cfmUnsettledAccruedFundingPnl;
    }

    public String getMarginUtilizationPercent() {
        return marginUtilizationPercent;
    }

    public void setMarginUtilizationPercent(String marginUtilizationPercent) {
        this.marginUtilizationPercent = marginUtilizationPercent;
    }

    public FcmMarginHealthState getMarginHealthState() {
        return marginHealthState;
    }

    public void setMarginHealthState(FcmMarginHealthState marginHealthState) {
        this.marginHealthState = marginHealthState;
    }

}
