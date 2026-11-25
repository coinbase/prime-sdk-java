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

package com.coinbase.prime.addressbook;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.common.Pagination;

/**
 * Get Address Book
 *
 * @deprecated Use getAddressBook instead.
 */
@Deprecated
public class ListAddressBookRequest extends GetAddressBookRequest {
  public ListAddressBookRequest() {}

  public ListAddressBookRequest(GetAddressBookRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use getAddressBook instead.
   */
  @Deprecated
  public static class Builder extends GetAddressBookRequest.Builder {
    @Override
    public Builder portfolioId(String portfolioId) {
      super.portfolioId(portfolioId);
      return this;
    }

    @Override
    public Builder currencySymbol(String currencySymbol) {
      super.currencySymbol(currencySymbol);
      return this;
    }

    @Override
    public Builder search(String search) {
      super.search(search);
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
    public ListAddressBookRequest build() throws CoinbaseClientException {
      super.validate();
      return new ListAddressBookRequest(this);
    }
  }
}
