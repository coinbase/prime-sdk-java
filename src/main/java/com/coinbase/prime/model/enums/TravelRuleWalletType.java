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
 * - TRAVEL_RULE_WALLET_TYPE_UNSPECIFIED: Default unspecified wallet type
 * - TRAVEL_RULE_WALLET_TYPE_VASP: Centralized exchange wallet
 * - TRAVEL_RULE_WALLET_TYPE_SELF_CUSTODIED: Self-hosted/custodial wallet
 */
public enum TravelRuleWalletType {
  /**
   * Default unspecified wallet type
   */
  TRAVEL_RULE_WALLET_TYPE_UNSPECIFIED,
  /**
   * Centralized exchange wallet
   */
  TRAVEL_RULE_WALLET_TYPE_VASP,
  /**
   * Self-hosted/custodial wallet
   */
  TRAVEL_RULE_WALLET_TYPE_SELF_CUSTODIED
}

