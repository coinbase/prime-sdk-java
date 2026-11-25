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

package com.coinbase.prime.staking;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.model.enums.SortDirection;

/**
 * List Transaction Validators
 *
 * @deprecated Use queryTransactionValidators instead.
 */
@Deprecated
public class ListTransactionValidatorsRequest extends QueryTransactionValidatorsRequest {
  public ListTransactionValidatorsRequest() {}

  public ListTransactionValidatorsRequest(QueryTransactionValidatorsRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use queryTransactionValidators instead.
   */
  @Deprecated
  public static class Builder extends QueryTransactionValidatorsRequest.Builder {
    @Override
    public Builder portfolioId(String portfolioId) {
      super.portfolioId(portfolioId);
      return this;
    }

    @Override
    public Builder transactionIds(String[] transactionIds) {
      super.transactionIds(transactionIds);
      return this;
    }

    @Override
    public Builder cursor(String cursor) {
      super.cursor(cursor);
      return this;
    }

    @Override
    public Builder limit(Integer limit) {
      super.limit(limit);
      return this;
    }

    @Override
    public Builder sortDirection(SortDirection sortDirection) {
      super.sortDirection(sortDirection);
      return this;
    }

    @Override
    public ListTransactionValidatorsRequest build() throws CoinbaseClientException {
      super.validate();
      return new ListTransactionValidatorsRequest(this);
    }
  }
}
