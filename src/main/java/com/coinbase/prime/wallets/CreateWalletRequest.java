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

package com.coinbase.prime.wallets;

import static com.coinbase.core.utils.Utils.isNullOrEmpty;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.model.Network;
import com.coinbase.prime.model.enums.NetworkFamily;
import com.coinbase.prime.model.enums.WalletType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Create Wallet */
public class CreateWalletRequest {
  /** Portfolio ID */
  @JsonProperty(required = true, value = "portfolio_id")
  @JsonIgnore
  private String portfolioId;

  /** The name of the wallet */
  @JsonProperty("name")
  private String name;

  /** The asset stored in the wallet. Should not be specified when wallet_type is ONCHAIN */
  @JsonProperty("symbol")
  private String symbol;

  /**
   * - VAULT: A crypto vault - TRADING: A trading wallet - WALLET_TYPE_OTHER: Other wallet types
   * (like consumer, etc) - QC: A QC Wallet - ONCHAIN: An Onchain wallet
   */
  @JsonProperty("wallet_type")
  private WalletType type;

  /** idem */
  @JsonProperty("idempotency_key")
  private String idempotencyKey;

  @JsonProperty("network_family")
  private NetworkFamily networkFamily;

  @JsonProperty("network")
  private Network network;

  public CreateWalletRequest() {}

  public CreateWalletRequest(Builder builder) {
    this.portfolioId = builder.portfolioId;
    this.name = builder.name;
    this.symbol = builder.symbol;
    this.type = builder.type;
    this.idempotencyKey = builder.idempotencyKey;
    this.networkFamily = builder.networkFamily;
    this.network = builder.network;
  }

  public String getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(String portfolioId) {
    this.portfolioId = portfolioId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public WalletType getType() {
    return type;
  }

  public void setType(WalletType type) {
    this.type = type;
  }

  public String getIdempotencyKey() {
    return idempotencyKey;
  }

  public void setIdempotencyKey(String idempotencyKey) {
    this.idempotencyKey = idempotencyKey;
  }

  public NetworkFamily getNetworkFamily() {
    return networkFamily;
  }

  public void setNetworkFamily(NetworkFamily networkFamily) {
    this.networkFamily = networkFamily;
  }

  public Network getNetwork() {
    return network;
  }

  public void setNetwork(Network network) {
    this.network = network;
  }

  public static class Builder {
    private String portfolioId;
    private String name;
    private String symbol;
    private WalletType type;
    private String idempotencyKey;
    private NetworkFamily networkFamily;
    private Network network;

    public Builder() {}

    public Builder portfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
      return this;
    }

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder symbol(String symbol) {
      this.symbol = symbol;
      return this;
    }

    public Builder type(WalletType type) {
      this.type = type;
      return this;
    }

    public Builder idempotencyKey(String idempotencyKey) {
      this.idempotencyKey = idempotencyKey;
      return this;
    }

    public Builder networkFamily(NetworkFamily networkFamily) {
      this.networkFamily = networkFamily;
      return this;
    }

    public Builder network(Network network) {
      this.network = network;
      return this;
    }

    public CreateWalletRequest build() throws CoinbaseClientException {
      validate();
      return new CreateWalletRequest(this);
    }

    private void validate() throws CoinbaseClientException {
      if (isNullOrEmpty(this.portfolioId)) {
        throw new CoinbaseClientException("PortfolioId is required");
      }
    }
  }
}
