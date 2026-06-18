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

import static com.coinbase.core.utils.Utils.isNullOrEmpty;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.model.enums.OrderSide;
import com.coinbase.prime.model.enums.OrderType;
import com.coinbase.prime.model.enums.PegOffsetType;
import com.coinbase.prime.model.enums.TimeInForceType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Create Order */
public class CreateOrderRequest {
  /** The ID of the portfolio that owns the order */
  @JsonProperty(required = true, value = "portfolio_id")
  @JsonIgnore
  private String portfolioId;

  /** The ID of the product being traded for the order (e.g. `BTC-USD`) */
  @JsonProperty("product_id")
  private String productId;

  /** - UNKNOWN_ORDER_SIDE: nil value - BUY: Buy order - SELL: Sell order */
  @JsonProperty("side")
  private OrderSide side;

  /** A client-generated order ID used for reference purposes */
  @JsonProperty("client_order_id")
  private String clientOrderId;

  /**
   * - UNKNOWN_ORDER_TYPE: nil value - MARKET: A [market
   * order](https://en.wikipedia.org/wiki/Order_(exchange)#Market_order) - LIMIT: A [limit
   * order](https://en.wikipedia.org/wiki/Order_(exchange)#Limit_order) - TWAP: A [time-weighted
   * average price order](https://en.wikipedia.org/wiki/Time-weighted_average_price) - BLOCK: A
   * [block trade](https://en.wikipedia.org/wiki/Block_trade) - VWAP: A [volume-weighted average
   * price order](https://en.wikipedia.org/wiki/Volume-weighted_average_price) - STOP_LIMIT: A
   * [conditional order combined of stop order and limit
   * order](https://en.wikipedia.org/wiki/Order_(exchange)#Stop-limit_order) - RFQ: A [request for
   * quote](https://en.wikipedia.org/wiki/Request_for_quote) - PEG: A pegged order that dynamically
   * adjust based on market conditions while maintaining execution discretion and avoiding adverse
   * selection
   */
  @JsonProperty("type")
  private OrderType type;

  /** Order size in base asset units (either `base_quantity` or `quote_value` is required) */
  @JsonProperty("base_quantity")
  private String baseQuantity;

  /**
   * Order size in quote asset units, i.e. the amount the user wants to spend (when buying) or
   * receive (when selling); the quantity in base units will be determined based on the market
   * liquidity and indicated `quote_value` (either `base_quantity` or `quote_value` is required)
   */
  @JsonProperty("quote_value")
  private String quoteValue;

  /** The limit price (required for TWAP, VWAP, LIMIT, and STOP_LIMIT orders) */
  @JsonProperty("limit_price")
  private String limitPrice;

  /** The start time of the order in UTC (TWAP only) */
  @JsonProperty("start_time")
  private String startTime;

  /** The expiry time of the order in UTC (TWAP, VWAP, LIMIT, and STOP_LIMIT GTD only) */
  @JsonProperty("expiry_time")
  private String expiryTime;

  /**
   * - UNKNOWN_TIME_IN_FORCE: nil value - GOOD_UNTIL_DATE_TIME: Expires at a certain date/time -
   * GOOD_UNTIL_CANCELLED: Order stays on the books until cancelled - IMMEDIATE_OR_CANCEL: Order is
   * executed immediately at submission or is cancelled - FILL_OR_KILL: Order is executed
   * immediately and fully at submission or is cancelled
   */
  @JsonProperty("time_in_force")
  private TimeInForceType timeInForce;

  /**
   * An optional self trade prevention id (in the form of a UUID). The value is only honored for
   * certain clients who are permitted to specify a custom self trade prevention id
   */
  @JsonProperty("stp_id")
  private String stpId;

  /**
   * Optionally specify a display size. This is the maximum order size that will show up on venue
   * order books. Specifying a value here effectively makes a LIMIT order into an "iceberg" style
   * order. This property only applies to LIMIT orders and will be ignored for other order types.
   */
  @JsonProperty("display_quote_size")
  private String displayQuoteSize;

  @JsonProperty("display_base_size")
  private String displayBaseSize;

  @JsonProperty("is_raise_exact")
  private boolean isRaiseExact;

  /** Historical percentage of volume */
  @JsonProperty("historical_pov")
  private String historicalPov;

  /**
   * Specifies the stop price at which the order activates. The order is activated if the last trade
   * price on Coinbase Exchange crosses the stop price specified on the order
   */
  @JsonProperty("stop_price")
  private String stopPrice;

  /** The currency in which the settlement will be made */
  @JsonProperty("settl_currency")
  private String settlCurrency;

  /**
   * Post-only flag - when true, the order will only be posted to the order book and not immediately
   * matched. Only applicable to LIMIT orders with GTC or GTD time in force.
   */
  @JsonProperty("post_only")
  private Boolean postOnly;

  /**
   * - UNKNOWN_PEG_OFFSET_TYPE: nil value - PEG_OFFSET_TYPE_PRICE: Offset specified in price units -
   * PEG_OFFSET_TYPE_BPS: Offset specified in basis points (BPS) - PEG_OFFSET_TYPE_DEPTH: Offset
   * specified in depth
   */
  @JsonProperty("peg_offset_type")
  private PegOffsetType pegOffsetType;

  /**
   * Offset value for PEG orders. 0 means peg to BBO. Only non-negative values are allowed (PEG
   * orders only)
   */
  @JsonProperty("offset")
  private String offset;

  /** next: 23 */
  @JsonProperty("wig_level")
  private String wigLevel;

  public CreateOrderRequest() {}

  public CreateOrderRequest(Builder builder) {
    this.portfolioId = builder.portfolioId;
    this.productId = builder.productId;
    this.side = builder.side;
    this.clientOrderId = builder.clientOrderId;
    this.type = builder.type;
    this.baseQuantity = builder.baseQuantity;
    this.quoteValue = builder.quoteValue;
    this.limitPrice = builder.limitPrice;
    this.startTime = builder.startTime;
    this.expiryTime = builder.expiryTime;
    this.timeInForce = builder.timeInForce;
    this.stpId = builder.stpId;
    this.displayQuoteSize = builder.displayQuoteSize;
    this.displayBaseSize = builder.displayBaseSize;
    this.isRaiseExact = builder.isRaiseExact;
    this.historicalPov = builder.historicalPov;
    this.stopPrice = builder.stopPrice;
    this.settlCurrency = builder.settlCurrency;
    this.postOnly = builder.postOnly;
    this.pegOffsetType = builder.pegOffsetType;
    this.offset = builder.offset;
    this.wigLevel = builder.wigLevel;
  }

  public String getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(String portfolioId) {
    this.portfolioId = portfolioId;
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

  public String getClientOrderId() {
    return clientOrderId;
  }

  public void setClientOrderId(String clientOrderId) {
    this.clientOrderId = clientOrderId;
  }

  public OrderType getType() {
    return type;
  }

  public void setType(OrderType type) {
    this.type = type;
  }

  public String getBaseQuantity() {
    return baseQuantity;
  }

  public void setBaseQuantity(String baseQuantity) {
    this.baseQuantity = baseQuantity;
  }

  public String getQuoteValue() {
    return quoteValue;
  }

  public void setQuoteValue(String quoteValue) {
    this.quoteValue = quoteValue;
  }

  public String getLimitPrice() {
    return limitPrice;
  }

  public void setLimitPrice(String limitPrice) {
    this.limitPrice = limitPrice;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(String expiryTime) {
    this.expiryTime = expiryTime;
  }

  public TimeInForceType getTimeInForce() {
    return timeInForce;
  }

  public void setTimeInForce(TimeInForceType timeInForce) {
    this.timeInForce = timeInForce;
  }

  public String getStpId() {
    return stpId;
  }

  public void setStpId(String stpId) {
    this.stpId = stpId;
  }

  public String getDisplayQuoteSize() {
    return displayQuoteSize;
  }

  public void setDisplayQuoteSize(String displayQuoteSize) {
    this.displayQuoteSize = displayQuoteSize;
  }

  public String getDisplayBaseSize() {
    return displayBaseSize;
  }

  public void setDisplayBaseSize(String displayBaseSize) {
    this.displayBaseSize = displayBaseSize;
  }

  public boolean isRaiseExact() {
    return isRaiseExact;
  }

  public void setRaiseExact(boolean isRaiseExact) {
    this.isRaiseExact = isRaiseExact;
  }

  public String getHistoricalPov() {
    return historicalPov;
  }

  public void setHistoricalPov(String historicalPov) {
    this.historicalPov = historicalPov;
  }

  public String getStopPrice() {
    return stopPrice;
  }

  public void setStopPrice(String stopPrice) {
    this.stopPrice = stopPrice;
  }

  public String getSettlCurrency() {
    return settlCurrency;
  }

  public void setSettlCurrency(String settlCurrency) {
    this.settlCurrency = settlCurrency;
  }

  public Boolean getPostOnly() {
    return postOnly;
  }

  public void setPostOnly(Boolean postOnly) {
    this.postOnly = postOnly;
  }

  public PegOffsetType getPegOffsetType() {
    return pegOffsetType;
  }

  public void setPegOffsetType(PegOffsetType pegOffsetType) {
    this.pegOffsetType = pegOffsetType;
  }

  public String getOffset() {
    return offset;
  }

  public void setOffset(String offset) {
    this.offset = offset;
  }

  public String getWigLevel() {
    return wigLevel;
  }

  public void setWigLevel(String wigLevel) {
    this.wigLevel = wigLevel;
  }

  public static class Builder {
    private String portfolioId;
    private String productId;
    private OrderSide side;
    private String clientOrderId;
    private OrderType type;
    private String baseQuantity;
    private String quoteValue;
    private String limitPrice;
    private String startTime;
    private String expiryTime;
    private TimeInForceType timeInForce;
    private String stpId;
    private String displayQuoteSize;
    private String displayBaseSize;
    private boolean isRaiseExact;
    private String historicalPov;
    private String stopPrice;
    private String settlCurrency;
    private Boolean postOnly;
    private PegOffsetType pegOffsetType;
    private String offset;
    private String wigLevel;

    public Builder() {}

    public Builder portfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
      return this;
    }

    public Builder productId(String productId) {
      this.productId = productId;
      return this;
    }

    public Builder side(OrderSide side) {
      this.side = side;
      return this;
    }

    public Builder clientOrderId(String clientOrderId) {
      this.clientOrderId = clientOrderId;
      return this;
    }

    public Builder type(OrderType type) {
      this.type = type;
      return this;
    }

    public Builder baseQuantity(String baseQuantity) {
      this.baseQuantity = baseQuantity;
      return this;
    }

    public Builder quoteValue(String quoteValue) {
      this.quoteValue = quoteValue;
      return this;
    }

    public Builder limitPrice(String limitPrice) {
      this.limitPrice = limitPrice;
      return this;
    }

    public Builder startTime(String startTime) {
      this.startTime = startTime;
      return this;
    }

    public Builder expiryTime(String expiryTime) {
      this.expiryTime = expiryTime;
      return this;
    }

    public Builder timeInForce(TimeInForceType timeInForce) {
      this.timeInForce = timeInForce;
      return this;
    }

    public Builder stpId(String stpId) {
      this.stpId = stpId;
      return this;
    }

    public Builder displayQuoteSize(String displayQuoteSize) {
      this.displayQuoteSize = displayQuoteSize;
      return this;
    }

    public Builder displayBaseSize(String displayBaseSize) {
      this.displayBaseSize = displayBaseSize;
      return this;
    }

    public Builder isRaiseExact(boolean isRaiseExact) {
      this.isRaiseExact = isRaiseExact;
      return this;
    }

    public Builder historicalPov(String historicalPov) {
      this.historicalPov = historicalPov;
      return this;
    }

    public Builder stopPrice(String stopPrice) {
      this.stopPrice = stopPrice;
      return this;
    }

    public Builder settlCurrency(String settlCurrency) {
      this.settlCurrency = settlCurrency;
      return this;
    }

    public Builder postOnly(Boolean postOnly) {
      this.postOnly = postOnly;
      return this;
    }

    public Builder pegOffsetType(PegOffsetType pegOffsetType) {
      this.pegOffsetType = pegOffsetType;
      return this;
    }

    public Builder offset(String offset) {
      this.offset = offset;
      return this;
    }

    public Builder wigLevel(String wigLevel) {
      this.wigLevel = wigLevel;
      return this;
    }

    public CreateOrderRequest build() throws CoinbaseClientException {
      validate();
      return new CreateOrderRequest(this);
    }

    private void validate() throws CoinbaseClientException {
      if (isNullOrEmpty(this.portfolioId)) {
        throw new CoinbaseClientException("PortfolioId is required");
      }
    }
  }
}
