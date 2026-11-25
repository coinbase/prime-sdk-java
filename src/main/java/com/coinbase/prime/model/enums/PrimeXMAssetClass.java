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
 * Asset class of the XM position underlier. - PRIME_XM_ASSET_CLASS_UNSPECIFIED: The asset class is
 * unavailable for this position, or is not recognized by this API version. -
 * PRIME_XM_ASSET_CLASS_CASH: USD and USDC. - PRIME_XM_ASSET_CLASS_FX: Non-USD fiat currencies
 * recognized as FX in the margin model, such as EUR, GBP, and CHF. -
 * PRIME_XM_ASSET_CLASS_STABLECOIN: Non-USDC stablecoins recognized as cash equivalents in the
 * margin model, such as EURC and USDT. - PRIME_XM_ASSET_CLASS_CRYPTO: Crypto assets (e.g. BTC,
 * ETH).
 */
public enum PrimeXMAssetClass {
  /** The asset class is unavailable for this position, or is not recognized by this API version. */
  PRIME_XM_ASSET_CLASS_UNSPECIFIED,
  /** USD and USDC. */
  PRIME_XM_ASSET_CLASS_CASH,
  /** Non-USD fiat currencies recognized as FX in the margin model, such as EUR, GBP, and CHF. */
  PRIME_XM_ASSET_CLASS_FX,
  /**
   * Non-USDC stablecoins recognized as cash equivalents in the margin model, such as EURC and USDT.
   */
  PRIME_XM_ASSET_CLASS_STABLECOIN,
  /** Crypto assets (e.g. BTC, ETH). */
  PRIME_XM_ASSET_CLASS_CRYPTO
}
