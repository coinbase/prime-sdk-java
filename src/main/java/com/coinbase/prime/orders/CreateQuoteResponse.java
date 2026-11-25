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

import com.fasterxml.jackson.annotation.JsonProperty;

/** Create Quote Request */
public class CreateQuoteResponse {
  @JsonProperty("quote_id")
  private String quoteId;

  @JsonProperty("expiration_time")
  private String expirationTime;

  @JsonProperty("best_price")
  private String bestPrice;

  @JsonProperty("order_total")
  private String orderTotal;

  @JsonProperty("price_inclusive_of_fees")
  private String priceInclusiveOfFees;

  /**
   * Echo of the quote_duration_ms supplied in the request. 0 if the client did not supply a value,
   * in which case the server applies the default of 3000 ms (3 seconds).
   */
  @JsonProperty("quote_duration_ms")
  private String quoteDurationMs;

  /** Buy Exact order flag */
  @JsonProperty("is_buy_exact")
  private Boolean isBuyExact;

  public CreateQuoteResponse() {}

  public String getQuoteId() {
    return quoteId;
  }

  public void setQuoteId(String quoteId) {
    this.quoteId = quoteId;
  }

  public String getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(String expirationTime) {
    this.expirationTime = expirationTime;
  }

  public String getBestPrice() {
    return bestPrice;
  }

  public void setBestPrice(String bestPrice) {
    this.bestPrice = bestPrice;
  }

  public String getOrderTotal() {
    return orderTotal;
  }

  public void setOrderTotal(String orderTotal) {
    this.orderTotal = orderTotal;
  }

  public String getPriceInclusiveOfFees() {
    return priceInclusiveOfFees;
  }

  public void setPriceInclusiveOfFees(String priceInclusiveOfFees) {
    this.priceInclusiveOfFees = priceInclusiveOfFees;
  }

  public String getQuoteDurationMs() {
    return quoteDurationMs;
  }

  public void setQuoteDurationMs(String quoteDurationMs) {
    this.quoteDurationMs = quoteDurationMs;
  }

  public Boolean getIsBuyExact() {
    return isBuyExact;
  }

  public void setIsBuyExact(Boolean isBuyExact) {
    this.isBuyExact = isBuyExact;
  }
}
