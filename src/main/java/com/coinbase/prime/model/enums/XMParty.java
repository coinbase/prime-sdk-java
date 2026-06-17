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
 * - CBE: Coinbase Exchange, trading venue that can receive the XM loan
 * - FCM: Coinbase’s Futures Commission Merchant, trading venue that can receive the XM loan
 */
public enum XMParty {
  XM_PARTY_UNSPECIFIED,
  /**
   * Coinbase Exchange, trading venue that can receive the XM loan
   */
  CBE,
  /**
   * Coinbase’s Futures Commission Merchant, trading venue that can receive the XM loan
   */
  FCM
}

