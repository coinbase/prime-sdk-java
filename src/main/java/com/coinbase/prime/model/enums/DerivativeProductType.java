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
 * The general type of a derivative product.
 * - DERIVATIVE_PRODUCT_TYPE_UNSPECIFIED: Unknown product type.
 * - DERIVATIVE_PRODUCT_TYPE_SPOT: Spot product.
 * - DERIVATIVE_PRODUCT_TYPE_FUTURE: Future product.
 * - DERIVATIVE_PRODUCT_TYPE_EQUITY: Equity product.
 * - DERIVATIVE_PRODUCT_TYPE_PREDICTION_MARKET: Prediction market product.
 * - DERIVATIVE_PRODUCT_TYPE_OPTION: Option product.
 * - DERIVATIVE_PRODUCT_TYPE_BASIS: Basis product.
 * - DERIVATIVE_PRODUCT_TYPE_EQUITY_OPTION: Equity option product.
 * - DERIVATIVE_PRODUCT_TYPE_FUTURE_COMBO: Future combo product.
 * - DERIVATIVE_PRODUCT_TYPE_OPTION_COMBO: Option combo product.
 */
public enum DerivativeProductType {
  /**
   * Spot product.
   */
  DERIVATIVE_PRODUCT_TYPE_SPOT,
  /**
   * Future product.
   */
  DERIVATIVE_PRODUCT_TYPE_FUTURE,
  /**
   * Equity product.
   */
  DERIVATIVE_PRODUCT_TYPE_EQUITY,
  /**
   * Prediction market product.
   */
  DERIVATIVE_PRODUCT_TYPE_PREDICTION_MARKET,
  /**
   * Option product.
   */
  DERIVATIVE_PRODUCT_TYPE_OPTION,
  /**
   * Basis product.
   */
  DERIVATIVE_PRODUCT_TYPE_BASIS,
  /**
   * Equity option product.
   */
  DERIVATIVE_PRODUCT_TYPE_EQUITY_OPTION,
  /**
   * Future combo product.
   */
  DERIVATIVE_PRODUCT_TYPE_FUTURE_COMBO,
  /**
   * Option combo product.
   */
  DERIVATIVE_PRODUCT_TYPE_OPTION_COMBO
}

