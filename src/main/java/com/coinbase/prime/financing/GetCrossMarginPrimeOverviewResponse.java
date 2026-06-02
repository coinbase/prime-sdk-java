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

package com.coinbase.prime.financing;

import com.coinbase.prime.model.CrossMarginPrimeMarginSummary;
import com.coinbase.prime.model.enums.PrimeXMControlStatus;
import com.coinbase.prime.model.enums.PrimeXMMarginLevel;
import com.fasterxml.jackson.annotation.JsonProperty;

    /**
     * Get Exchange Cross Margin Overview
     */
public class GetCrossMarginPrimeOverviewResponse {
    /**
     * - TRADES_AND_WITHDRAWALS: Allowed to trade and withdraw. See XM Margin Methodology for full description of when trading and withdrawals are enabled or disabled. - TRADES_ONLY: Allowed to trade but not withdraw. See XM Margin Methodology for full description of when trading and withdrawals are enabled or disabled. - SESSION_LOCKED: Not allowed to trade or withdraw. See XM Margin Methodology for full description of when trading and withdrawals are enabled or disabled.
     */
    @JsonProperty("control_status")
    private PrimeXMControlStatus controlStatus;

    /**
     * - HEALTHY_THRESHOLD: Margin level is healthy - DEFICIT_THRESHOLD: Margin level is breaching the deficit threshold (DT) which will result in the issuance of a Margin Call if this is still the case by the scheduled next Margin Call time (as defined in the margin methodology) - WARNING_THRESHOLD: Margin level is breaching the warning threshold (WT) which will result in the issuance of a Margin Call if this is still the case by the scheduled next Margin Call (as defined in the margin methodology). WT is differentiated from DT in that it means margin health is approaching the UMCT - URGENT_MARGIN_CALL_THRESHOLD: Margin level is breaching the UMCT and, as defined in the margin methodology, this will trigger an urgent margin call - LIQUIDATION_THRESHOLD: Margin level is breaching the liquidation threshold (LT) and, as defined in the margin methodology, this will trigger the SESSION_LOCKED control status and liquidation may commence.
     */
    @JsonProperty("margin_level")
    private PrimeXMMarginLevel marginLevel;

        /**
     * When margin metrics were evaluated.
     */
    @JsonProperty("evaluated_at")
    private String evaluatedAt;

    /**
     * Cross-margin account summary and nested breakdowns.
     */
    @JsonProperty("margin_summary")
    private CrossMarginPrimeMarginSummary marginSummary;

    public GetCrossMarginPrimeOverviewResponse() {
    }

    public PrimeXMControlStatus getControlStatus() {
        return controlStatus;
    }

    public void setControlStatus(PrimeXMControlStatus controlStatus) {
        this.controlStatus = controlStatus;
    }

    public PrimeXMMarginLevel getMarginLevel() {
        return marginLevel;
    }

    public void setMarginLevel(PrimeXMMarginLevel marginLevel) {
        this.marginLevel = marginLevel;
    }

    public String getEvaluatedAt() {
        return evaluatedAt;
    }

    public void setEvaluatedAt(String evaluatedAt) {
        this.evaluatedAt = evaluatedAt;
    }

    public CrossMarginPrimeMarginSummary getMarginSummary() {
        return marginSummary;
    }

    public void setMarginSummary(CrossMarginPrimeMarginSummary marginSummary) {
        this.marginSummary = marginSummary;
    }
}
