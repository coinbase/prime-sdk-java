/*
 * Copyright 2026-present Coinbase Global, Inc.
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

import com.coinbase.prime.model.OptionGreeks;
import com.coinbase.prime.model.SpreadPreview;
import com.coinbase.prime.model.enums.OrderSide;
import com.coinbase.prime.model.enums.OrderType;
import com.coinbase.prime.model.enums.TimeInForceType;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Get Order Preview */
public class CreateOrderPreviewResponse {
  @JsonProperty("portfolio_id")
  private String portfolioId;

  @JsonProperty("product_id")
  private String productId;

  @JsonProperty("side")
  private OrderSide side;

  @JsonProperty("type")
  private OrderType type;

  @JsonProperty("base_quantity")
  private String baseQuantity;

  @JsonProperty("quote_value")
  private String quoteValue;

  @JsonProperty("limit_price")
  private String limitPrice;

  @JsonProperty("start_time")
  private String startTime;

  @JsonProperty("expiry_time")
  private String expiryTime;

  @JsonProperty("time_in_force")
  private TimeInForceType timeInForce;

  @JsonProperty("commission")
  private String commission;

  @JsonProperty("slippage")
  private String slippage;

  @JsonProperty("best_bid")
  private String bestBid;

  @JsonProperty("best_ask")
  private String bestAsk;

  @JsonProperty("average_filled_price")
  private String averageFilledPrice;

  @JsonProperty("order_total")
  private String orderTotal;

  @JsonProperty("historical_pov")
  private String historicalPov;

  @JsonProperty("is_raise_exact")
  private Boolean isRaiseExact;

  /** Buy Exact order flag */
  @JsonProperty("is_buy_exact")
  private Boolean isBuyExact;

  @JsonProperty("stop_price")
  private String stopPrice;

  @JsonProperty("display_size")
  private String displaySize;

  @JsonProperty("display_quote_size")
  private String displayQuoteSize;

  @JsonProperty("display_base_size")
  private String displayBaseSize;

  @JsonProperty("option_greeks")
  private OptionGreeks optionGreeks;

  @JsonProperty("spread_preview")
  private SpreadPreview spreadPreview;

  /**
   * Futures and SPREAD orders only: amount of the portfolio's derivatives margin limit used before
   * this order. For cross-margin portfolios, the cross-margin limit consumed.
   */
  @JsonProperty("current_margin_utilization")
  private String currentMarginUtilization;

  /**
   * Futures and SPREAD orders only: `current_margin_utilization` as a percentage of the margin
   * limit (0-100, e.g. "1.2" means 1.2%, not a 0-1 decimal)
   */
  @JsonProperty("current_margin_utilization_ratio")
  private String currentMarginUtilizationRatio;

  /**
   * Futures and SPREAD orders only: amount of the portfolio's derivatives margin limit used if this
   * order fills. For cross-margin portfolios, the cross-margin limit consumed.
   */
  @JsonProperty("projected_margin_utilization")
  private String projectedMarginUtilization;

  /**
   * Futures and SPREAD orders only: `projected_margin_utilization` as a percentage of the margin
   * limit (0-100, e.g. "3.6" means 3.6%, not a 0-1 decimal)
   */
  @JsonProperty("projected_margin_utilization_ratio")
  private String projectedMarginUtilizationRatio;

  /** The requested `settl_currency`, echoed */
  @JsonProperty("settl_currency")
  private String settlCurrency;

  public CreateOrderPreviewResponse() {}

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

  public String getCommission() {
    return commission;
  }

  public void setCommission(String commission) {
    this.commission = commission;
  }

  public String getSlippage() {
    return slippage;
  }

  public void setSlippage(String slippage) {
    this.slippage = slippage;
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

  public String getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(String orderTotal) {
    this.orderTotal = orderTotal;
  }

  public String getHistoricalPov() {
    return historicalPov;
  }

  public void setHistoricalPov(String historicalPov) {
    this.historicalPov = historicalPov;
  }

  public Boolean getIsRaiseExact() {
    return isRaiseExact;
  }

  public void setIsRaiseExact(Boolean isRaiseExact) {
    this.isRaiseExact = isRaiseExact;
  }

  public Boolean getIsBuyExact() {
    return isBuyExact;
  }

  public void setIsBuyExact(Boolean isBuyExact) {
    this.isBuyExact = isBuyExact;
  }

  public String getStopPrice() {
    return stopPrice;
  }

  public void setStopPrice(String stopPrice) {
    this.stopPrice = stopPrice;
  }

  public String getDisplaySize() {
    return displaySize;
  }

  public void setDisplaySize(String displaySize) {
    this.displaySize = displaySize;
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

  public OptionGreeks getOptionGreeks() {
    return optionGreeks;
  }

  public void setOptionGreeks(OptionGreeks optionGreeks) {
    this.optionGreeks = optionGreeks;
  }

  public SpreadPreview getSpreadPreview() {
    return spreadPreview;
  }

  public void setSpreadPreview(SpreadPreview spreadPreview) {
    this.spreadPreview = spreadPreview;
  }

  public String getCurrentMarginUtilization() {
    return currentMarginUtilization;
  }

  public void setCurrentMarginUtilization(String currentMarginUtilization) {
    this.currentMarginUtilization = currentMarginUtilization;
  }

  public String getCurrentMarginUtilizationRatio() {
    return currentMarginUtilizationRatio;
  }

  public void setCurrentMarginUtilizationRatio(String currentMarginUtilizationRatio) {
    this.currentMarginUtilizationRatio = currentMarginUtilizationRatio;
  }

  public String getProjectedMarginUtilization() {
    return projectedMarginUtilization;
  }

  public void setProjectedMarginUtilization(String projectedMarginUtilization) {
    this.projectedMarginUtilization = projectedMarginUtilization;
  }

  public String getProjectedMarginUtilizationRatio() {
    return projectedMarginUtilizationRatio;
  }

  public void setProjectedMarginUtilizationRatio(String projectedMarginUtilizationRatio) {
    this.projectedMarginUtilizationRatio = projectedMarginUtilizationRatio;
  }

  public String getSettlCurrency() {
    return settlCurrency;
  }

  public void setSettlCurrency(String settlCurrency) {
    this.settlCurrency = settlCurrency;
  }
}
