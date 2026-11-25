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

/** One leg of a SPREAD order as placed and executed */
public class SpreadLegDetails {
  /** Product ID of the leg, e.g. BTC-USD or BIP-20DEC30-CDE */
  @JsonProperty("product_id")
  private String productId;

  /**
   * - UNKNOWN_ORDER_SIDE: nil value - BUY: Buy order - SELL: Sell order - MULTIPLE: Response only:
   * a SPREAD order, whose legs carry their own sides (see `spread_details`); not accepted in
   * requests
   */
  private OrderSide side;

  /** Whether this leg may rest passively on the book */
  @JsonProperty("support_passive_order")
  private boolean supportPassiveOrder;

  /**
   * Quantity filled on this leg, in its units: contracts for a futures product, base units for spot
   */
  @JsonProperty("filled_quantity")
  private String filledQuantity;

  /** Average price of this leg's fills, per unit of the underlying */
  @JsonProperty("average_filled_price")
  private String averageFilledPrice;

  /** Commission charged on this leg so far */
  private String commission;

  @JsonProperty("commission_detail_total")
  private CommissionDetailTotal commissionDetailTotal;

  /**
   * Fee charged by the exchange on this leg for Cost Plus commission configurations; 0 for All In
   */
  @JsonProperty("exchange_fee")
  private String exchangeFee;

  /** Notional value of this leg's fills so far, in the quote currency */
  @JsonProperty("filled_value")
  private String filledValue;

  public SpreadLegDetails() {}

  public SpreadLegDetails(Builder builder) {
    this.productId = builder.productId;
    this.side = builder.side;
    this.supportPassiveOrder = builder.supportPassiveOrder;
    this.filledQuantity = builder.filledQuantity;
    this.averageFilledPrice = builder.averageFilledPrice;
    this.commission = builder.commission;
    this.commissionDetailTotal = builder.commissionDetailTotal;
    this.exchangeFee = builder.exchangeFee;
    this.filledValue = builder.filledValue;
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

  public String getFilledQuantity() {
    return filledQuantity;
  }

  public void setFilledQuantity(String filledQuantity) {
    this.filledQuantity = filledQuantity;
  }

  public String getAverageFilledPrice() {
    return averageFilledPrice;
  }

  public void setAverageFilledPrice(String averageFilledPrice) {
    this.averageFilledPrice = averageFilledPrice;
  }

  public String getCommission() {
    return commission;
  }

  public void setCommission(String commission) {
    this.commission = commission;
  }

  public CommissionDetailTotal getCommissionDetailTotal() {
    return commissionDetailTotal;
  }

  public void setCommissionDetailTotal(CommissionDetailTotal commissionDetailTotal) {
    this.commissionDetailTotal = commissionDetailTotal;
  }

  public String getExchangeFee() {
    return exchangeFee;
  }

  public void setExchangeFee(String exchangeFee) {
    this.exchangeFee = exchangeFee;
  }

  public String getFilledValue() {
    return filledValue;
  }

  public void setFilledValue(String filledValue) {
    this.filledValue = filledValue;
  }

  public static class Builder {
    private String productId;

    private OrderSide side;

    private boolean supportPassiveOrder;

    private String filledQuantity;

    private String averageFilledPrice;

    private String commission;

    private CommissionDetailTotal commissionDetailTotal;

    private String exchangeFee;

    private String filledValue;

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

    public Builder filledQuantity(String filledQuantity) {
      this.filledQuantity = filledQuantity;
      return this;
    }

    public Builder averageFilledPrice(String averageFilledPrice) {
      this.averageFilledPrice = averageFilledPrice;
      return this;
    }

    public Builder commission(String commission) {
      this.commission = commission;
      return this;
    }

    public Builder commissionDetailTotal(CommissionDetailTotal commissionDetailTotal) {
      this.commissionDetailTotal = commissionDetailTotal;
      return this;
    }

    public Builder exchangeFee(String exchangeFee) {
      this.exchangeFee = exchangeFee;
      return this;
    }

    public Builder filledValue(String filledValue) {
      this.filledValue = filledValue;
      return this;
    }

    public SpreadLegDetails build() {
      return new SpreadLegDetails(this);
    }
  }
}
