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
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Edit Order (Beta) */
public class EditOrderRequest {
  /** The ID of the portfolio that owns the order */
  @JsonProperty(required = true, value = "portfolio_id")
  @JsonIgnore
  private String portfolioId;

  /** The ID of the order being edited */
  @JsonProperty(required = true, value = "order_id")
  @JsonIgnore
  private String orderId;

  /** Deprecated: The product ID of the order being edited */
  @JsonProperty("product_id")
  private String productId;

  /** The client order ID of the order being edited */
  @JsonProperty("orig_client_order_id")
  private String origClientOrderId;

  /** The updated version of the client order ID */
  @JsonProperty("client_order_id")
  private String clientOrderId;

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

  /** The expiry time of the order in UTC (TWAP, VWAP, LIMIT, and STOP_LIMIT GTD only) */
  @JsonProperty("expiry_time")
  private String expiryTime;

  /**
   * The maximum order size that will show up on venue order books. Specifying a value here
   * effectively makes a LIMIT order into an "iceberg" style order.
   */
  @JsonProperty("display_quote_size")
  private String displayQuoteSize;

  /**
   * The maximum order size that will show up on venue order books. Specifying a value here
   * effectively makes a LIMIT order into an "iceberg" style order.
   */
  @JsonProperty("display_base_size")
  private String displayBaseSize;

  /**
   * Specifies the stop price at which the order activates. The order is activated if the last trade
   * price on Coinbase Exchange crosses the stop price specified on the order
   */
  @JsonProperty("stop_price")
  private String stopPrice;

  /**
   * New peg offset value for PEG orders. 0 means peg to BBO. Only non-negative values are allowed.
   * The order's peg_offset_type is immutable and is reused server-side to interpret this value (PEG
   * orders only)
   */
  @JsonProperty("offset")
  private String offset;

  /**
   * New WIG (Would if Good) level for PEG orders - the best price a pegged order would be placed on
   * venues, opposite to limit_price. Cannot be added or removed post-placement (PEG orders only)
   */
  @JsonProperty("wig_level")
  private String wigLevel;

  public EditOrderRequest() {}

  public EditOrderRequest(Builder builder) {
    this.portfolioId = builder.portfolioId;
    this.orderId = builder.orderId;
    this.productId = builder.productId;
    this.origClientOrderId = builder.origClientOrderId;
    this.clientOrderId = builder.clientOrderId;
    this.baseQuantity = builder.baseQuantity;
    this.quoteValue = builder.quoteValue;
    this.limitPrice = builder.limitPrice;
    this.expiryTime = builder.expiryTime;
    this.displayQuoteSize = builder.displayQuoteSize;
    this.displayBaseSize = builder.displayBaseSize;
    this.stopPrice = builder.stopPrice;
    this.offset = builder.offset;
    this.wigLevel = builder.wigLevel;
  }

  public String getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(String portfolioId) {
    this.portfolioId = portfolioId;
  }

  public String getOrderId() {
    return orderId;
  }

  public void setOrderId(String orderId) {
    this.orderId = orderId;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public String getOrigClientOrderId() {
    return origClientOrderId;
  }

  public void setOrigClientOrderId(String origClientOrderId) {
    this.origClientOrderId = origClientOrderId;
  }

  public String getClientOrderId() {
    return clientOrderId;
  }

  public void setClientOrderId(String clientOrderId) {
    this.clientOrderId = clientOrderId;
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

  public String getExpiryTime() {
    return expiryTime;
  }

  public void setExpiryTime(String expiryTime) {
    this.expiryTime = expiryTime;
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

  public String getStopPrice() {
    return stopPrice;
  }

  public void setStopPrice(String stopPrice) {
    this.stopPrice = stopPrice;
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
    private String orderId;
    private String productId;
    private String origClientOrderId;
    private String clientOrderId;
    private String baseQuantity;
    private String quoteValue;
    private String limitPrice;
    private String expiryTime;
    private String displayQuoteSize;
    private String displayBaseSize;
    private String stopPrice;
    private String offset;
    private String wigLevel;

    public Builder() {}

    public Builder portfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
      return this;
    }

    public Builder orderId(String orderId) {
      this.orderId = orderId;
      return this;
    }

    public Builder productId(String productId) {
      this.productId = productId;
      return this;
    }

    public Builder origClientOrderId(String origClientOrderId) {
      this.origClientOrderId = origClientOrderId;
      return this;
    }

    public Builder clientOrderId(String clientOrderId) {
      this.clientOrderId = clientOrderId;
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

    public Builder expiryTime(String expiryTime) {
      this.expiryTime = expiryTime;
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

    public Builder stopPrice(String stopPrice) {
      this.stopPrice = stopPrice;
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

    public EditOrderRequest build() throws CoinbaseClientException {
      validate();
      return new EditOrderRequest(this);
    }

    private void validate() throws CoinbaseClientException {
      if (isNullOrEmpty(this.portfolioId)) {
        throw new CoinbaseClientException("PortfolioId is required");
      }
      if (isNullOrEmpty(this.orderId)) {
        throw new CoinbaseClientException("OrderId is required");
      }
    }
  }
}
