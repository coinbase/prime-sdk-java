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
 * - UNKNOWN_ORDER_STATUS: nil value
 * - OPEN: The order is open but unfilled
 * - FILLED: The order was filled
 * - CANCELLED: The order was cancelled
 * - EXPIRED: The order has expired
 * - FAILED: Order submission failed
 * - PENDING: The order has been sent but is not yet confirmed
 */
public enum OrderStatus {
  /**
   * The order is open but unfilled
   */
  OPEN,
  /**
   * The order was filled
   */
  FILLED,
  /**
   * The order was cancelled
   */
  CANCELLED,
  /**
   * The order has expired
   */
  EXPIRED,
  /**
   * Order submission failed
   */
  FAILED,
  /**
   * The order has been sent but is not yet confirmed
   */
  PENDING
}

