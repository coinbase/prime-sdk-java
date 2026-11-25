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

package com.coinbase.prime.orders;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.model.SpreadConfig;
import com.coinbase.prime.model.enums.LimitPriceType;
import com.coinbase.prime.model.enums.OrderSide;
import com.coinbase.prime.model.enums.OrderType;
import com.coinbase.prime.model.enums.PegOffsetType;
import com.coinbase.prime.model.enums.TimeInForceType;

/**
 * Get Order Preview
 *
 * @deprecated Use createOrderPreview instead.
 */
@Deprecated
public class GetOrderPreviewRequest extends CreateOrderPreviewRequest {
  public GetOrderPreviewRequest() {}

  public GetOrderPreviewRequest(CreateOrderPreviewRequest.Builder builder) {
    super(builder);
  }

  /**
   * @deprecated Use createOrderPreview instead.
   */
  @Deprecated
  public static class Builder extends CreateOrderPreviewRequest.Builder {
    @Override
    public Builder portfolioId(String portfolioId) {
      super.portfolioId(portfolioId);
      return this;
    }

    @Override
    public Builder productId(String productId) {
      super.productId(productId);
      return this;
    }

    @Override
    public Builder side(OrderSide side) {
      super.side(side);
      return this;
    }

    @Override
    public Builder type(OrderType type) {
      super.type(type);
      return this;
    }

    @Override
    public Builder baseQuantity(String baseQuantity) {
      super.baseQuantity(baseQuantity);
      return this;
    }

    @Override
    public Builder quoteValue(String quoteValue) {
      super.quoteValue(quoteValue);
      return this;
    }

    @Override
    public Builder limitPrice(String limitPrice) {
      super.limitPrice(limitPrice);
      return this;
    }

    @Override
    public Builder startTime(String startTime) {
      super.startTime(startTime);
      return this;
    }

    @Override
    public Builder expiryTime(String expiryTime) {
      super.expiryTime(expiryTime);
      return this;
    }

    @Override
    public Builder timeInForce(TimeInForceType timeInForce) {
      super.timeInForce(timeInForce);
      return this;
    }

    @Override
    public Builder isRaiseExact(Boolean isRaiseExact) {
      super.isRaiseExact(isRaiseExact);
      return this;
    }

    @Override
    public Builder isBuyExact(Boolean isBuyExact) {
      super.isBuyExact(isBuyExact);
      return this;
    }

    @Override
    public Builder historicalPov(String historicalPov) {
      super.historicalPov(historicalPov);
      return this;
    }

    @Override
    public Builder stopPrice(String stopPrice) {
      super.stopPrice(stopPrice);
      return this;
    }

    @Override
    public Builder settlCurrency(String settlCurrency) {
      super.settlCurrency(settlCurrency);
      return this;
    }

    @Override
    public Builder postOnly(Boolean postOnly) {
      super.postOnly(postOnly);
      return this;
    }

    @Override
    public Builder displayQuoteSize(String displayQuoteSize) {
      super.displayQuoteSize(displayQuoteSize);
      return this;
    }

    @Override
    public Builder displayBaseSize(String displayBaseSize) {
      super.displayBaseSize(displayBaseSize);
      return this;
    }

    @Override
    public Builder pegOffsetType(PegOffsetType pegOffsetType) {
      super.pegOffsetType(pegOffsetType);
      return this;
    }

    @Override
    public Builder offset(String offset) {
      super.offset(offset);
      return this;
    }

    @Override
    public Builder wigLevel(String wigLevel) {
      super.wigLevel(wigLevel);
      return this;
    }

    @Override
    public Builder limitPriceType(LimitPriceType limitPriceType) {
      super.limitPriceType(limitPriceType);
      return this;
    }

    @Override
    public Builder spreadConfig(SpreadConfig spreadConfig) {
      super.spreadConfig(spreadConfig);
      return this;
    }

    @Override
    public GetOrderPreviewRequest build() throws CoinbaseClientException {
      super.validate();
      return new GetOrderPreviewRequest(this);
    }
  }
}
