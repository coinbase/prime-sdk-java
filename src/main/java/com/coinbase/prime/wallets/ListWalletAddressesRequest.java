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
import com.coinbase.prime.common.Pagination;
import com.coinbase.prime.common.PrimeListRequest;
import com.coinbase.prime.model.enums.SortDirection;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** List Wallet Addresses */
public class ListWalletAddressesRequest extends PrimeListRequest {
  /** The portfolio ID associated with the wallet */
  @JsonProperty(required = true, value = "portfolio_id")
  @JsonIgnore
  private String portfolioId;

  /** The wallet ID for which to retrieve all deposit addresses */
  @JsonProperty(required = true, value = "wallet_id")
  @JsonIgnore
  private String walletId;

  /**
   * The blockchain network name and type, provide an empty network to retrieve addresses across all
   * networks for this wallet
   */
  @JsonProperty("network_id")
  private String networkId;

  public ListWalletAddressesRequest() {}

  public ListWalletAddressesRequest(Builder builder) {
    super(builder.cursor, builder.sortDirection, builder.limit);
    this.portfolioId = builder.portfolioId;
    this.walletId = builder.walletId;
    this.networkId = builder.networkId;
  }

  public String getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(String portfolioId) {
    this.portfolioId = portfolioId;
  }

  public String getWalletId() {
    return walletId;
  }

  public void setWalletId(String walletId) {
    this.walletId = walletId;
  }

  public String getNetworkId() {
    return networkId;
  }

  public void setNetworkId(String networkId) {
    this.networkId = networkId;
  }

  public static class Builder {
    private String portfolioId;
    private String walletId;
    private String networkId;
    private String cursor;
    private SortDirection sortDirection;
    private Integer limit;

    public Builder() {}

    public Builder portfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
      return this;
    }

    public Builder walletId(String walletId) {
      this.walletId = walletId;
      return this;
    }

    public Builder networkId(String networkId) {
      this.networkId = networkId;
      return this;
    }

    public Builder limit(Integer limit) {
      this.limit = limit;
      return this;
    }

    public Builder pagination(Pagination pagination) {
      this.cursor = pagination.getNextCursor();
      this.sortDirection = pagination.getSortDirection();
      return this;
    }

    public ListWalletAddressesRequest build() throws CoinbaseClientException {
      validate();
      return new ListWalletAddressesRequest(this);
    }

    private void validate() throws CoinbaseClientException {
      if (isNullOrEmpty(this.portfolioId)) {
        throw new CoinbaseClientException("PortfolioId is required");
      }
      if (isNullOrEmpty(this.walletId)) {
        throw new CoinbaseClientException("WalletId is required");
      }
    }
  }
}
