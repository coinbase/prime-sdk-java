/*
 * Copyright 2025-present Coinbase Global, Inc.
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

import com.coinbase.prime.model.enums.XMControlStatus;
import com.coinbase.prime.model.enums.XMEntityCallStatus;
import com.coinbase.prime.model.enums.XMMarginLevel;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class CrossMarginOverview {
  /**
   * - TRADES_AND_WITHDRAWALS: Allowed to trade and withdraw. See XM Margin Methodology for full
   * description of when trading and withdrawals are enabled or disabled. - TRADES_ONLY: Allowed to
   * trade but not withdraw. See XM Margin Methodology for full description of when trading and
   * withdrawals are enabled or disabled. - SESSION_LOCKED: Not allowed to trade or withdraw. See XM
   * Margin Methodology for full description of when trading and withdrawals are enabled or
   * disabled.
   */
  @JsonProperty("control_status")
  private XMControlStatus controlStatus;

  /**
   * XMEntityCallStatus summarizes the state of open margin calls or debit calls. When multiple
   * calls exist, the status reflects the highest priority call type. Priority order (highest to
   * lowest): aged > urgent > standard > debit. - ENTITY_NO_CALL: There are no margin calls or debit
   * calls. - ENTITY_OPEN_STANDARD_CALL: There is a standard margin call. There may also be debit
   * calls, but there are no urgent margin calls or expired calls.. - ENTITY_OPEN_URGENT_CALL: There
   * is an urgent margin call. There may also be standard margin calls or debit calls, but there are
   * no expired calls. - ENTITY_AGED_CALL: At least one open margin call (standard or urgent) or
   * debit call is aged. This will trigger the SESSION_LOCKED control status. -
   * ENTITY_OPEN_DEBIT_CALL: There is a debit call. There are no standard margin calls, urgent
   * margin calls, or expired calls.
   */
  @JsonProperty("call_status")
  private XMEntityCallStatus callStatus;

  /**
   * - HEALTHY_THRESHOLD: Margin level is healthy - DEFICIT_THRESHOLD: Margin level is breaching the
   * deficit threshold (DT) which will result in the issuance of a Margin Call if this is still the
   * case by the scheduled next Margin Call time (as defined in the margin methodology) -
   * WARNING_THRESHOLD: Margin level is breaching the warning threshold (WT) which will result in
   * the issuance of a Margin Call if this is still the case by the scheduled next Margin Call (as
   * defined in the margin methodology). WT is differentiated from DT in that it means margin health
   * is approaching the UMCT - URGENT_MARGIN_CALL_THRESHOLD: Margin level is breaching the UMCT and,
   * as defined in the margin methodology, this will trigger an urgent margin call -
   * LIQUIDATION_THRESHOLD: Margin level is breaching the liquidation threshold (LT) and, as defined
   * in the margin methodology, this will trigger the SESSION_LOCKED control status and liquidation
   * may commence.
   */
  @JsonProperty("margin_level")
  private XMMarginLevel marginLevel;

  /** XMSummary is the realtime evaluated XM margin model, containing positions and netting info */
  @JsonProperty("margin_summary")
  private XMSummary marginSummary;

  /** List of active XM margin calls */
  @JsonProperty("active_margin_calls")
  private List<XMMarginCall> activeMarginCalls;

  /** List of active XM loans */
  @JsonProperty("active_loans")
  private List<XMLoan> activeLoans;

  /** ActiveLiquidationSummary provides a summary of the active or most recent XM liquidation */
  @JsonProperty("active_liquidation")
  private ActiveLiquidationSummary activeLiquidation;

  public CrossMarginOverview() {}

  public CrossMarginOverview(Builder builder) {
    this.controlStatus = builder.controlStatus;
    this.callStatus = builder.callStatus;
    this.marginLevel = builder.marginLevel;
    this.marginSummary = builder.marginSummary;
    this.activeMarginCalls = builder.activeMarginCalls;
    this.activeLoans = builder.activeLoans;
    this.activeLiquidation = builder.activeLiquidation;
  }

  public XMControlStatus getControlStatus() {
    return controlStatus;
  }

  public void setControlStatus(XMControlStatus controlStatus) {
    this.controlStatus = controlStatus;
  }

  public XMEntityCallStatus getCallStatus() {
    return callStatus;
  }

  public void setCallStatus(XMEntityCallStatus callStatus) {
    this.callStatus = callStatus;
  }

  public XMMarginLevel getMarginLevel() {
    return marginLevel;
  }

  public void setMarginLevel(XMMarginLevel marginLevel) {
    this.marginLevel = marginLevel;
  }

  public XMSummary getMarginSummary() {
    return marginSummary;
  }

  public void setMarginSummary(XMSummary marginSummary) {
    this.marginSummary = marginSummary;
  }

  public List<XMMarginCall> getActiveMarginCalls() {
    return activeMarginCalls;
  }

  public void setActiveMarginCalls(List<XMMarginCall> activeMarginCalls) {
    this.activeMarginCalls = activeMarginCalls;
  }

  public List<XMLoan> getActiveLoans() {
    return activeLoans;
  }

  public void setActiveLoans(List<XMLoan> activeLoans) {
    this.activeLoans = activeLoans;
  }

  public ActiveLiquidationSummary getActiveLiquidation() {
    return activeLiquidation;
  }

  public void setActiveLiquidation(ActiveLiquidationSummary activeLiquidation) {
    this.activeLiquidation = activeLiquidation;
  }

  public static class Builder {
    private XMControlStatus controlStatus;

    private XMEntityCallStatus callStatus;

    private XMMarginLevel marginLevel;

    private XMSummary marginSummary;

    private List<XMMarginCall> activeMarginCalls;

    private List<XMLoan> activeLoans;

    private ActiveLiquidationSummary activeLiquidation;

    public Builder controlStatus(XMControlStatus controlStatus) {
      this.controlStatus = controlStatus;
      return this;
    }

    public Builder callStatus(XMEntityCallStatus callStatus) {
      this.callStatus = callStatus;
      return this;
    }

    public Builder marginLevel(XMMarginLevel marginLevel) {
      this.marginLevel = marginLevel;
      return this;
    }

    public Builder marginSummary(XMSummary marginSummary) {
      this.marginSummary = marginSummary;
      return this;
    }

    public Builder activeMarginCalls(List<XMMarginCall> activeMarginCalls) {
      this.activeMarginCalls = activeMarginCalls;
      return this;
    }

    public Builder activeLoans(List<XMLoan> activeLoans) {
      this.activeLoans = activeLoans;
      return this;
    }

    public Builder activeLiquidation(ActiveLiquidationSummary activeLiquidation) {
      this.activeLiquidation = activeLiquidation;
      return this;
    }

    public CrossMarginOverview build() {
      return new CrossMarginOverview(this);
    }
  }
}
