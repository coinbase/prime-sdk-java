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

package com.coinbase.prime.futures;

import com.coinbase.core.errors.CoinbaseClientException;

/**
 * Get Entity Positions
 *
 * @deprecated Use getEntityPositions instead.
 */
@Deprecated
public class GetPositionsRequest extends GetEntityPositionsRequest {
  public GetPositionsRequest() {}

  public GetPositionsRequest(GetEntityPositionsRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use getEntityPositions instead.
   */
  @Deprecated
  public static class Builder extends GetEntityPositionsRequest.Builder {
    @Override
    public Builder entityId(String entityId) {
      super.entityId(entityId);
      return this;
    }

    @Override
    public Builder productId(String productId) {
      super.productId(productId);
      return this;
    }

    @Override
    public GetPositionsRequest build() throws CoinbaseClientException {
      super.validate();
      return new GetPositionsRequest(this);
    }
  }
}
