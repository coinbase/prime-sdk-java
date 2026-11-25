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
import com.coinbase.prime.model.AllocationLeg;
import com.coinbase.prime.model.enums.AllocationSizeType;

/**
 * Create Portfolio Net Allocations
 *
 * @deprecated Use createPortfolioNetAllocations instead.
 */
@Deprecated
public class CreateNetAllocationRequest extends CreatePortfolioNetAllocationsRequest {
  public CreateNetAllocationRequest() {}

  public CreateNetAllocationRequest(CreatePortfolioNetAllocationsRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use createPortfolioNetAllocations instead.
   */
  @Deprecated
  public static class Builder extends CreatePortfolioNetAllocationsRequest.Builder {
    @Override
    public Builder sourcePortfolioId(String sourcePortfolioId) {
      super.sourcePortfolioId(sourcePortfolioId);
      return this;
    }

    @Override
    public Builder productId(String productId) {
      super.productId(productId);
      return this;
    }

    @Override
    public Builder orderIds(String[] orderIds) {
      super.orderIds(orderIds);
      return this;
    }

    @Override
    public Builder allocationLegs(AllocationLeg[] allocationLegs) {
      super.allocationLegs(allocationLegs);
      return this;
    }

    @Override
    public Builder sizeType(AllocationSizeType sizeType) {
      super.sizeType(sizeType);
      return this;
    }

    @Override
    public Builder remainderDestinationPortfolio(String remainderDestinationPortfolio) {
      super.remainderDestinationPortfolio(remainderDestinationPortfolio);
      return this;
    }

    @Override
    public Builder nettingId(String nettingId) {
      super.nettingId(nettingId);
      return this;
    }

    @Override
    public CreateNetAllocationRequest build() throws CoinbaseClientException {
      super.validate();
      return new CreateNetAllocationRequest(this);
    }
  }
}
