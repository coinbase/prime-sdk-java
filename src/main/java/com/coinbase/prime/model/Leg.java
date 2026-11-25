/*
 * Copyright 2026-present Coinbase Global, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *  Do not edit the class manually.
 */

package com.coinbase.prime.model;

import com.coinbase.prime.model.enums.OrderSide;
import com.fasterxml.jackson.annotation.JsonProperty;

/** One leg of a SPREAD order */
public class Leg {
  /** Product ID of the leg, e.g. BTC-USD for spot or BIP-20DEC30-CDE for futures */
  @JsonProperty("product_id")
  private String productId;

  /**
   * - UNKNOWN_ORDER_SIDE: nil value - BUY: Buy order - SELL: Sell order - MULTIPLE: Response only:
   * a SPREAD order, whose legs carry their own sides (see `spread_details`); not accepted in
   * requests
   */
  private OrderSide side;

  /** Whether this leg may rest passively on the book. At least one leg must be true. */
  @JsonProperty("support_passive_order")
  private boolean supportPassiveOrder;

  public Leg() {}

  public Leg(Builder builder) {
    this.productId = builder.productId;
    this.side = builder.side;
    this.supportPassiveOrder = builder.supportPassiveOrder;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public OrderSide getSide() {
    return side;
  }

  public void setSide(OrderSide side) {
    this.side = side;
  }

  public boolean getSupportPassiveOrder() {
    return supportPassiveOrder;
  }

  public void setSupportPassiveOrder(boolean supportPassiveOrder) {
    this.supportPassiveOrder = supportPassiveOrder;
  }

  public static class Builder {
    private String productId;

    private OrderSide side;

    private boolean supportPassiveOrder;

    public Builder productId(String productId) {
      this.productId = productId;
      return this;
    }

    public Builder side(OrderSide side) {
      this.side = side;
      return this;
    }

    public Builder supportPassiveOrder(boolean supportPassiveOrder) {
      this.supportPassiveOrder = supportPassiveOrder;
      return this;
    }

    public Leg build() {
      return new Leg(this);
    }
  }
}
