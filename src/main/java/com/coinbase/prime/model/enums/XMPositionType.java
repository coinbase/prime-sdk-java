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
 * Type of a position leg within a per-asset XM row. New values may be added as more legs are broken
 * out. - XM_POSITION_TYPE_UNSPECIFIED: The position type is unavailable, or is not recognized by
 * this API version. - XM_POSITION_TYPE_SPOT_TRADING: Spot balance held outside Prime Vault (trading
 * balance, including Prime Loans). - XM_POSITION_TYPE_SPOT_VAULT: Spot balance held in Prime Vault.
 */
public enum XMPositionType {
  /** Spot balance held outside Prime Vault (trading balance, including Prime Loans). */
  XM_POSITION_TYPE_SPOT_TRADING,
  /** Spot balance held in Prime Vault. */
  XM_POSITION_TYPE_SPOT_VAULT
}
