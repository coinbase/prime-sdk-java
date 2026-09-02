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

import com.coinbase.prime.model.enums.DerivativeProductType;
import com.coinbase.prime.model.enums.FcmPositionSide;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/** A single derivative position across all derivative product types. */
public class DerivativePosition {
  /** Product ID */
  @JsonProperty("product_id")
  private String productId;

  private FcmPositionSide side;

  /** Number of contracts */
  @JsonProperty("number_of_contracts")
  private String numberOfContracts;

  /** Daily realized PNL */
  @JsonProperty("daily_realized_pnl")
  private String dailyRealizedPnl;

  /** Unrealized PNL */
  @JsonProperty("unrealized_pnl")
  private String unrealizedPnl;

  /** Current price of position */
  @JsonProperty("current_price")
  private String currentPrice;

  /** Average entry price */
  @JsonProperty("avg_entry_price")
  private String avgEntryPrice;

  /** Expiration time of position */
  @JsonProperty("expiration_time")
  private OffsetDateTime expirationTime;

  /**
   * The general type of a derivative product. - DERIVATIVE_PRODUCT_TYPE_UNSPECIFIED: Unknown
   * product type. - DERIVATIVE_PRODUCT_TYPE_SPOT: Spot product. - DERIVATIVE_PRODUCT_TYPE_FUTURE:
   * Future product. - DERIVATIVE_PRODUCT_TYPE_EQUITY: Equity product. -
   * DERIVATIVE_PRODUCT_TYPE_PREDICTION_MARKET: Prediction market product. -
   * DERIVATIVE_PRODUCT_TYPE_OPTION: Option product. - DERIVATIVE_PRODUCT_TYPE_BASIS: Basis product.
   * - DERIVATIVE_PRODUCT_TYPE_EQUITY_OPTION: Equity option product. -
   * DERIVATIVE_PRODUCT_TYPE_FUTURE_COMBO: Future combo product. -
   * DERIVATIVE_PRODUCT_TYPE_OPTION_COMBO: Option combo product.
   */
  @JsonProperty("product_type")
  private DerivativeProductType productType;

  /** Settlement currency */
  private String currency;

  /** Options-specific details for a derivative position, including greeks. */
  @JsonProperty("options_details")
  private OptionsDetails optionsDetails;

  /** Venue ID of the position */
  @JsonProperty("venue_id")
  private String venueId;

  public DerivativePosition() {}

  public DerivativePosition(Builder builder) {
    this.productId = builder.productId;
    this.side = builder.side;
    this.numberOfContracts = builder.numberOfContracts;
    this.dailyRealizedPnl = builder.dailyRealizedPnl;
    this.unrealizedPnl = builder.unrealizedPnl;
    this.currentPrice = builder.currentPrice;
    this.avgEntryPrice = builder.avgEntryPrice;
    this.expirationTime = builder.expirationTime;
    this.productType = builder.productType;
    this.currency = builder.currency;
    this.optionsDetails = builder.optionsDetails;
    this.venueId = builder.venueId;
  }

  public String getProductId() {
    return productId;
  }

  public void setProductId(String productId) {
    this.productId = productId;
  }

  public FcmPositionSide getSide() {
    return side;
  }

  public void setSide(FcmPositionSide side) {
    this.side = side;
  }

  public String getNumberOfContracts() {
    return numberOfContracts;
  }

  public void setNumberOfContracts(String numberOfContracts) {
    this.numberOfContracts = numberOfContracts;
  }

  public String getDailyRealizedPnl() {
    return dailyRealizedPnl;
  }

  public void setDailyRealizedPnl(String dailyRealizedPnl) {
    this.dailyRealizedPnl = dailyRealizedPnl;
  }

  public String getUnrealizedPnl() {
    return unrealizedPnl;
  }

  public void setUnrealizedPnl(String unrealizedPnl) {
    this.unrealizedPnl = unrealizedPnl;
  }

  public String getCurrentPrice() {
    return currentPrice;
  }

  public void setCurrentPrice(String currentPrice) {
    this.currentPrice = currentPrice;
  }

  public String getAvgEntryPrice() {
    return avgEntryPrice;
  }

  public void setAvgEntryPrice(String avgEntryPrice) {
    this.avgEntryPrice = avgEntryPrice;
  }

  public OffsetDateTime getExpirationTime() {
    return expirationTime;
  }

  public void setExpirationTime(OffsetDateTime expirationTime) {
    this.expirationTime = expirationTime;
  }

  public DerivativeProductType getProductType() {
    return productType;
  }

  public void setProductType(DerivativeProductType productType) {
    this.productType = productType;
  }

  public String getCurrency() {
    return currency;
  }

  public void setCurrency(String currency) {
    this.currency = currency;
  }

  public OptionsDetails getOptionsDetails() {
    return optionsDetails;
  }

  public void setOptionsDetails(OptionsDetails optionsDetails) {
    this.optionsDetails = optionsDetails;
  }

  public String getVenueId() {
    return venueId;
  }

  public void setVenueId(String venueId) {
    this.venueId = venueId;
  }

  public static class Builder {
    private String productId;

    private FcmPositionSide side;

    private String numberOfContracts;

    private String dailyRealizedPnl;

    private String unrealizedPnl;

    private String currentPrice;

    private String avgEntryPrice;

    private OffsetDateTime expirationTime;

    private DerivativeProductType productType;

    private String currency;

    private OptionsDetails optionsDetails;

    private String venueId;

    public Builder productId(String productId) {
      this.productId = productId;
      return this;
    }

    public Builder side(FcmPositionSide side) {
      this.side = side;
      return this;
    }

    public Builder numberOfContracts(String numberOfContracts) {
      this.numberOfContracts = numberOfContracts;
      return this;
    }

    public Builder dailyRealizedPnl(String dailyRealizedPnl) {
      this.dailyRealizedPnl = dailyRealizedPnl;
      return this;
    }

    public Builder unrealizedPnl(String unrealizedPnl) {
      this.unrealizedPnl = unrealizedPnl;
      return this;
    }

    public Builder currentPrice(String currentPrice) {
      this.currentPrice = currentPrice;
      return this;
    }

    public Builder avgEntryPrice(String avgEntryPrice) {
      this.avgEntryPrice = avgEntryPrice;
      return this;
    }

    public Builder expirationTime(OffsetDateTime expirationTime) {
      this.expirationTime = expirationTime;
      return this;
    }

    public Builder productType(DerivativeProductType productType) {
      this.productType = productType;
      return this;
    }

    public Builder currency(String currency) {
      this.currency = currency;
      return this;
    }

    public Builder optionsDetails(OptionsDetails optionsDetails) {
      this.optionsDetails = optionsDetails;
      return this;
    }

    public Builder venueId(String venueId) {
      this.venueId = venueId;
      return this;
    }

    public DerivativePosition build() {
      return new DerivativePosition(this);
    }
  }
}
