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

package com.coinbase.prime.products;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.common.Pagination;
import com.coinbase.prime.model.enums.ContractExpiryType;
import com.coinbase.prime.model.enums.ExpiringContractStatus;
import com.coinbase.prime.model.enums.ProductType;

/**
 * List Portfolio Products
 *
 * @deprecated Use listProducts instead.
 */
@Deprecated
public class ListPortfolioProductsRequest extends ListProductsRequest {
  public ListPortfolioProductsRequest() {}

  public ListPortfolioProductsRequest(ListProductsRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use listProducts instead.
   */
  @Deprecated
  public static class Builder extends ListProductsRequest.Builder {
    @Override
    public Builder portfolioId(String portfolioId) {
      super.portfolioId(portfolioId);
      return this;
    }

    @Override
    public Builder productType(ProductType productType) {
      super.productType(productType);
      return this;
    }

    @Override
    public Builder contractExpiryType(ContractExpiryType contractExpiryType) {
      super.contractExpiryType(contractExpiryType);
      return this;
    }

    @Override
    public Builder expiringContractStatus(ExpiringContractStatus expiringContractStatus) {
      super.expiringContractStatus(expiringContractStatus);
      return this;
    }

    @Override
    public Builder limit(Integer limit) {
      super.limit(limit);
      return this;
    }

    @Override
    public Builder pagination(Pagination pagination) {
      super.pagination(pagination);
      return this;
    }

    @Override
    public ListPortfolioProductsRequest build() throws CoinbaseClientException {
      super.validate();
      return new ListPortfolioProductsRequest(this);
    }
  }
}
