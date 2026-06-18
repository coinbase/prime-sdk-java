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
 * - FCM_TRADING_SESSION_STATE_UNDEFINED: Undefined session state -
 * FCM_TRADING_SESSION_STATE_PRE_OPEN: Pre-open state, orders can be placed and cancelled -
 * FCM_TRADING_SESSION_STATE_PRE_OPEN_NO_CANCEL: Pre-open state, orders cannot be cancelled -
 * FCM_TRADING_SESSION_STATE_OPEN: Trading session is open - FCM_TRADING_SESSION_STATE_CLOSE:
 * Trading session is closed - FCM_TRADING_SESSION_STATE_HALTED: Trading session is halted
 */
public enum FcmTradingSessionState {
  /** Undefined session state */
  FCM_TRADING_SESSION_STATE_UNDEFINED,
  /** Pre-open state, orders can be placed and cancelled */
  FCM_TRADING_SESSION_STATE_PRE_OPEN,
  /** Pre-open state, orders cannot be cancelled */
  FCM_TRADING_SESSION_STATE_PRE_OPEN_NO_CANCEL,
  /** Trading session is open */
  FCM_TRADING_SESSION_STATE_OPEN,
  /** Trading session is closed */
  FCM_TRADING_SESSION_STATE_CLOSE,
  /** Trading session is halted */
  FCM_TRADING_SESSION_STATE_HALTED
}
