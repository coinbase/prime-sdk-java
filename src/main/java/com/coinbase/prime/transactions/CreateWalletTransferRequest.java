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

import com.coinbase.core.errors.CoinbaseClientException;

/**
 * Create Transfer
 *
 * @deprecated Use createTransfer instead.
 */
@Deprecated
public class CreateWalletTransferRequest extends CreateTransferRequest {
  public CreateWalletTransferRequest() {}

  public CreateWalletTransferRequest(CreateTransferRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use createTransfer instead.
   */
  @Deprecated
  public static class Builder extends CreateTransferRequest.Builder {
    @Override
    public Builder portfolioId(String portfolioId) {
      super.portfolioId(portfolioId);
      return this;
    }

    @Override
    public Builder walletId(String walletId) {
      super.walletId(walletId);
      return this;
    }

    @Override
    public Builder amount(String amount) {
      super.amount(amount);
      return this;
    }

    @Override
    public Builder destination(String destination) {
      super.destination(destination);
      return this;
    }

    @Override
    public Builder idempotencyKey(String idempotencyKey) {
      super.idempotencyKey(idempotencyKey);
      return this;
    }

    @Override
    public Builder currencySymbol(String currencySymbol) {
      super.currencySymbol(currencySymbol);
      return this;
    }

    @Override
    public CreateWalletTransferRequest build() throws CoinbaseClientException {
      super.validate();
      return new CreateWalletTransferRequest(this);
    }
  }
}
