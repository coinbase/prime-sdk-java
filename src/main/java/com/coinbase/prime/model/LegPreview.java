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

/** Preview values for one leg of a SPREAD order */
public class LegPreview {
  @JsonProperty("product_id")
  private String productId;

  /**
   * - UNKNOWN_ORDER_SIDE: nil value - BUY: Buy order - SELL: Sell order - MULTIPLE: Response only:
   * a SPREAD order, whose legs carry their own sides (see `spread_details`); not accepted in
   * requests
   */
  private OrderSide side;

  @JsonProperty("support_passive_order")
  private boolean supportPassiveOrder;

  /** Estimated commission for this leg */
  @JsonProperty("leg_commission")
  private String legCommission;

  /** Estimated notional of this leg in quote currency */
  @JsonProperty("quote_value")
  private String quoteValue;

  /** Current best bid for this leg's order book */
  @JsonProperty("best_bid")
  private String bestBid;

  /** Current best ask for this leg's order book */
  @JsonProperty("best_ask")
  private String bestAsk;

  /** Estimated average fill price for this leg */
  @JsonProperty("average_filled_price")
  private String averageFilledPrice;

  /** Expected slippage for this leg */
  private String slippage;

  public LegPreview() {}

  public LegPreview(Builder builder) {
    this.productId = builder.productId;
    this.side = builder.side;
    this.supportPassiveOrder = builder.supportPassiveOrder;
    this.legCommission = builder.legCommission;
    this.quoteValue = builder.quoteValue;
    this.bestBid = builder.bestBid;
    this.bestAsk = builder.bestAsk;
    this.averageFilledPrice = builder.averageFilledPrice;
    this.slippage = builder.slippage;
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

  public String getLegCommission() {
    return legCommission;
  }

  public void setLegCommission(String legCommission) {
    this.legCommission = legCommission;
  }

  public String getQuoteValue() {
    return quoteValue;
  }

  public void setQuoteValue(String quoteValue) {
    this.quoteValue = quoteValue;
  }

  public String getBestBid() {
    return bestBid;
  }

  public void setBestBid(String bestBid) {
    this.bestBid = bestBid;
  }

  public String getBestAsk() {
    return bestAsk;
  }

  public void setBestAsk(String bestAsk) {
    this.bestAsk = bestAsk;
  }

  public String getAverageFilledPrice() {
    return averageFilledPrice;
  }

  public void setAverageFilledPrice(String averageFilledPrice) {
    this.averageFilledPrice = averageFilledPrice;
  }

  public String getSlippage() {
    return slippage;
  }

  public void setSlippage(String slippage) {
    this.slippage = slippage;
  }

  public static class Builder {
    private String productId;

    private OrderSide side;

    private boolean supportPassiveOrder;

    private String legCommission;

    private String quoteValue;

    private String bestBid;

    private String bestAsk;

    private String averageFilledPrice;

    private String slippage;

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

    public Builder legCommission(String legCommission) {
      this.legCommission = legCommission;
      return this;
    }

    public Builder quoteValue(String quoteValue) {
      this.quoteValue = quoteValue;
      return this;
    }

    public Builder bestBid(String bestBid) {
      this.bestBid = bestBid;
      return this;
    }

    public Builder bestAsk(String bestAsk) {
      this.bestAsk = bestAsk;
      return this;
    }

    public Builder averageFilledPrice(String averageFilledPrice) {
      this.averageFilledPrice = averageFilledPrice;
      return this;
    }

    public Builder slippage(String slippage) {
      this.slippage = slippage;
      return this;
    }

    public LegPreview build() {
      return new LegPreview(this);
    }
  }
}
