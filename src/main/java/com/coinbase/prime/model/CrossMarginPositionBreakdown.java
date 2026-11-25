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

import com.coinbase.prime.model.enums.XMPositionType;
import com.fasterxml.jackson.annotation.JsonProperty;

/** A single typed position leg of a per-asset XM row. */
public class CrossMarginPositionBreakdown {
  /**
   * Type of a position leg within a per-asset XM row. New values may be added as more legs are
   * broken out. - XM_POSITION_TYPE_UNSPECIFIED: The position type is unavailable, or is not
   * recognized by this API version. - XM_POSITION_TYPE_SPOT_TRADING: Spot balance held outside
   * Prime Vault (trading balance, including Prime Loans). - XM_POSITION_TYPE_SPOT_VAULT: Spot
   * balance held in Prime Vault.
   */
  @JsonProperty("position_type")
  private XMPositionType positionType;

  /**
   * Signed balance of this leg in the position currency, after pending withdrawals and before
   * collateral caps and haircuts.
   */
  private String balance;

  /**
   * Signed USD notional of this leg, after pending withdrawals and before collateral caps and
   * haircuts.
   */
  @JsonProperty("notional_balance")
  private String notionalBalance;

  public CrossMarginPositionBreakdown() {}

  public CrossMarginPositionBreakdown(Builder builder) {
    this.positionType = builder.positionType;
    this.balance = builder.balance;
    this.notionalBalance = builder.notionalBalance;
  }

  public XMPositionType getPositionType() {
    return positionType;
  }

  public void setPositionType(XMPositionType positionType) {
    this.positionType = positionType;
  }

  public String getBalance() {
    return balance;
  }

  public void setBalance(String balance) {
    this.balance = balance;
  }

  public String getNotionalBalance() {
    return notionalBalance;
  }

  public void setNotionalBalance(String notionalBalance) {
    this.notionalBalance = notionalBalance;
  }

  public static class Builder {
    private XMPositionType positionType;

    private String balance;

    private String notionalBalance;

    public Builder positionType(XMPositionType positionType) {
      this.positionType = positionType;
      return this;
    }

    public Builder balance(String balance) {
      this.balance = balance;
      return this;
    }

    public Builder notionalBalance(String notionalBalance) {
      this.notionalBalance = notionalBalance;
      return this;
    }

    public CrossMarginPositionBreakdown build() {
      return new CrossMarginPositionBreakdown(this);
    }
  }
}
