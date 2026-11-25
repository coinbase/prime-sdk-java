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

package com.coinbase.prime.model.enums;

/**
 * LimitPriceType indicates the unit `limit_price` is denominated in. Only meaningful for Deribit
 * options - leave unspecified for every other order type and product, which are always priced in
 * the quote currency. - UNKNOWN_LIMIT_PRICE_TYPE: nil value - `limit_price` is interpreted in the
 * product's default/native unit - LIMIT_PRICE_TYPE_BASE_ASSET: `limit_price` is denominated in the
 * underlying base asset (e.g. BTC for a BTC option). Only valid for Deribit options settled in the
 * base asset. - LIMIT_PRICE_TYPE_QUOTE_ASSET: `limit_price` is denominated in the quote asset (e.g.
 * USD/USDC). - LIMIT_PRICE_TYPE_IMPLIED_VOLATILITY: `limit_price` is an implied volatility
 * percentage rather than a monetary amount (e.g. "65.5" means 65.5% implied volatility).
 */
public enum LimitPriceType {
  /** `limit_price` is denominated in the underlying base asset (e.g. BTC for a BTC option). */
  LIMIT_PRICE_TYPE_BASE_ASSET,
  /** `limit_price` is denominated in the quote asset (e.g. USD/USDC). */
  LIMIT_PRICE_TYPE_QUOTE_ASSET,
  /** `limit_price` is an implied volatility percentage rather than a monetary amount */
  LIMIT_PRICE_TYPE_IMPLIED_VOLATILITY
}
