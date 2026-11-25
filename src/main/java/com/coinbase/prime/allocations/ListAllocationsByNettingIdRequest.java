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

package com.coinbase.prime.allocations;

import com.coinbase.core.errors.CoinbaseClientException;

/**
 * Get Net Allocations by Netting ID
 *
 * @deprecated Use getAllocationsByClientNettingId instead.
 */
@Deprecated
public class ListAllocationsByNettingIdRequest extends GetAllocationsByClientNettingIdRequest {
  public ListAllocationsByNettingIdRequest() {}

  public ListAllocationsByNettingIdRequest(GetAllocationsByClientNettingIdRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use getAllocationsByClientNettingId instead.
   */
  @Deprecated
  public static class Builder extends GetAllocationsByClientNettingIdRequest.Builder {
    @Override
    public Builder portfolioId(String portfolioId) {
      super.portfolioId(portfolioId);
      return this;
    }

    @Override
    public Builder nettingId(String nettingId) {
      super.nettingId(nettingId);
      return this;
    }

    @Override
    public ListAllocationsByNettingIdRequest build() throws CoinbaseClientException {
      super.validate();
      return new ListAllocationsByNettingIdRequest(this);
    }
  }
}
