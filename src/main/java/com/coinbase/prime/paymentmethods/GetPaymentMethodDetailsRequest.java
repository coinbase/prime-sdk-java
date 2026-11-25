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

package com.coinbase.prime.paymentmethods;

import com.coinbase.core.errors.CoinbaseClientException;

/**
 * Get Entity Payment Method
 *
 * @deprecated Use getEntityPaymentMethod instead.
 */
@Deprecated
public class GetPaymentMethodDetailsRequest extends GetEntityPaymentMethodRequest {
  public GetPaymentMethodDetailsRequest() {}

  public GetPaymentMethodDetailsRequest(GetEntityPaymentMethodRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use getEntityPaymentMethod instead.
   */
  @Deprecated
  public static class Builder extends GetEntityPaymentMethodRequest.Builder {
    @Override
    public Builder entityId(String entityId) {
      super.entityId(entityId);
      return this;
    }

    @Override
    public Builder paymentMethodId(String paymentMethodId) {
      super.paymentMethodId(paymentMethodId);
      return this;
    }

    @Override
    public GetPaymentMethodDetailsRequest build() throws CoinbaseClientException {
      super.validate();
      return new GetPaymentMethodDetailsRequest(this);
    }
  }
}
