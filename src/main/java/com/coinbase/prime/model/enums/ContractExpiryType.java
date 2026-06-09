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
 * - CONTRACT_EXPIRY_TYPE_UNSPECIFIED: Unspecified contract expiry type -
 * CONTRACT_EXPIRY_TYPE_EXPIRING: Expiring futures contract - CONTRACT_EXPIRY_TYPE_PERPETUAL:
 * Perpetual futures contract (no expiry)
 */
public enum ContractExpiryType {
  /** Unspecified contract expiry type */
  CONTRACT_EXPIRY_TYPE_UNSPECIFIED,
  /** Expiring futures contract */
  CONTRACT_EXPIRY_TYPE_EXPIRING,
  /** Perpetual futures contract (no expiry) */
  CONTRACT_EXPIRY_TYPE_PERPETUAL
}
