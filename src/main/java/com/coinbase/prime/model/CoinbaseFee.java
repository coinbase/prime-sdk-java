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

import com.fasterxml.jackson.annotation.JsonProperty;

/** A Coinbase fee charged on an Advanced Transfer. */
public class CoinbaseFee {
  /** The fee amount in whole units of the fee symbol */
  private String amount;

  /** The asset symbol in which the fee was charged */
  private String symbol;

  /** The wallet that paid the fee */
  @JsonProperty("wallet_id")
  private String walletId;

  public CoinbaseFee() {}

  public CoinbaseFee(Builder builder) {
    this.amount = builder.amount;
    this.symbol = builder.symbol;
    this.walletId = builder.walletId;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getWalletId() {
    return walletId;
  }

  public void setWalletId(String walletId) {
    this.walletId = walletId;
  }

  public static class Builder {
    private String amount;

    private String symbol;

    private String walletId;

    public Builder amount(String amount) {
      this.amount = amount;
      return this;
    }

    public Builder symbol(String symbol) {
      this.symbol = symbol;
      return this;
    }

    public Builder walletId(String walletId) {
      this.walletId = walletId;
      return this;
    }

    public CoinbaseFee build() {
      return new CoinbaseFee(this);
    }
  }
}
