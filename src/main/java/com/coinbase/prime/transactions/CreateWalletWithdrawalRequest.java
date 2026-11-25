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
import com.coinbase.prime.model.BlockchainAddress;
import com.coinbase.prime.model.CounterpartyDestination;
import com.coinbase.prime.model.PaymentMethodDestination;
import com.coinbase.prime.model.TravelRuleData;
import com.coinbase.prime.model.enums.DestinationType;

/**
 * Create Withdrawal
 *
 * @deprecated Use createWithdrawal instead.
 */
@Deprecated
public class CreateWalletWithdrawalRequest extends CreateWithdrawalRequest {
  public CreateWalletWithdrawalRequest() {}

  public CreateWalletWithdrawalRequest(CreateWithdrawalRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use createWithdrawal instead.
   */
  @Deprecated
  public static class Builder extends CreateWithdrawalRequest.Builder {
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
    public Builder destinationType(DestinationType destinationType) {
      super.destinationType(destinationType);
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
    public Builder paymentMethod(PaymentMethodDestination paymentMethod) {
      super.paymentMethod(paymentMethod);
      return this;
    }

    @Override
    public Builder blockchainAddress(BlockchainAddress blockchainAddress) {
      super.blockchainAddress(blockchainAddress);
      return this;
    }

    @Override
    public Builder counterparty(CounterpartyDestination counterparty) {
      super.counterparty(counterparty);
      return this;
    }

    @Override
    public Builder travelRuleData(TravelRuleData travelRuleData) {
      super.travelRuleData(travelRuleData);
      return this;
    }

    @Override
    public CreateWalletWithdrawalRequest build() throws CoinbaseClientException {
      super.validate();
      return new CreateWalletWithdrawalRequest(this);
    }
  }
}
