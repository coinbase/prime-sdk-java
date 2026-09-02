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

/** ComboLeg describes a single leg within an options combo order. */
public class ComboLeg {
  /** The product identifier for the options instrument (e.g., \"BTC-28MAR25-50000-C\"). */
  @JsonProperty("product_id")
  private String productId;

  private String quantity;

  /** - UNKNOWN_ORDER_SIDE: nil value - BUY: Buy order - SELL: Sell order */
  private OrderSide side;

  public ComboLeg() {}

  public ComboLeg(Builder builder) {
    this.productId = builder.productId;
    this.quantity = builder.quantity;
    this.side = builder.side;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getQuantity() {
    return quantity;
  }

  public void setQuantity(String quantity) {
    this.quantity = quantity;
  }

  public OrderSide getSide() {
    return side;
  }

  public void setSide(OrderSide side) {
    this.side = side;
  }

  public static class Builder {
    private String productId;

    private String quantity;

    private OrderSide side;

    public Builder productId(String productId) {
      this.productId = productId;
      return this;
    }

    public Builder quantity(String quantity) {
      this.quantity = quantity;
      return this;
    }

    public Builder side(OrderSide side) {
      this.side = side;
      return this;
    }

    public ComboLeg build() {
      return new ComboLeg(this);
    }
  }
}
