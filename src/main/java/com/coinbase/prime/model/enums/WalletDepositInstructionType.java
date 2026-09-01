/*
 * Copyright 2025-present Coinbase Global, Inc.
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
 * - UNKNOWN_WALLET_DEPOSIT_TYPE: nil value - CRYPTO: A cryptocurrency deposit - WIRE: A wire
 * deposit - SEN: DEPRECATED. A Silvergate Exchange Network deposit - SWIFT: A SWIFT deposit - SEPA:
 * A SEPA deposit (Single Euro Payments Area)
 */
public enum WalletDepositInstructionType {
  /** A cryptocurrency deposit */
  CRYPTO,
  /** A wire deposit */
  WIRE,
  /** DEPRECATED. A Silvergate Exchange Network deposit */
  SEN,
  /** A SWIFT deposit */
  SWIFT,
  /** A SEPA deposit (Single Euro Payments Area) */
  SEPA
}
