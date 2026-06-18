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
 * - VAULT: A crypto vault - TRADING: A trading wallet - WALLET_TYPE_OTHER: Other wallet types (like
 * consumer, etc) - QC: A QC Wallet - ONCHAIN: An Onchain wallet
 */
public enum WalletType {
  /** A crypto vault */
  VAULT,
  /** A trading wallet */
  TRADING,
  /** Other wallet types (like consumer, etc) */
  WALLET_TYPE_OTHER,
  /** A QC Wallet */
  QC,
  /** An Onchain wallet */
  ONCHAIN
}
