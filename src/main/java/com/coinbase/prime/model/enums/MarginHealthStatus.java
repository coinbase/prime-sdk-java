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
 * Recorded account health state maintained by the margin-management process. Persistence rules
 * filter out short-lived threshold changes. Margin-call and liquidation events also affect this
 * state. A change in the calculated `margin_level` may not immediately change this status. Use
 * `control_status` for trading and withdrawal permissions in live responses. Historical responses
 * contain the recorded health state at the historical evaluation. -
 * MARGIN_HEALTH_STATUS_UNSPECIFIED: No account health state is provided. Don't interpret this value
 * as healthy or as permission to trade or withdraw. - MARGIN_HEALTH_STATUS_HEALTHY: The account's
 * recorded health state is healthy. - MARGIN_HEALTH_STATUS_WARNING: The account's recorded health
 * state is warning. Margin-call issuance follows the applicable margin methodology. -
 * MARGIN_HEALTH_STATUS_CRITICAL: The account's recorded health state is critical, corresponding to
 * an urgent margin-call condition under the applicable margin methodology. -
 * MARGIN_HEALTH_STATUS_SUSPENDED: The account is recorded as suspended under the applicable margin
 * methodology. - MARGIN_HEALTH_STATUS_RESTRICTED: The account is recorded as restricted under the
 * applicable margin methodology. - MARGIN_HEALTH_STATUS_PRE_LIQUIDATION: The account is in a
 * pre-liquidation state. Liquidation may commence under the applicable margin methodology. -
 * MARGIN_HEALTH_STATUS_LIQUIDATING: Liquidation has commenced. - MARGIN_HEALTH_STATUS_IN_DEFICIT:
 * The account's recorded health state is in deficit. Margin-call issuance follows the applicable
 * margin methodology.
 */
public enum MarginHealthStatus {
  /** The account's recorded health state is healthy. */
  MARGIN_HEALTH_STATUS_HEALTHY,
  /**
   * The account's recorded health state is warning. Margin-call issuance follows the applicable
   * margin methodology.
   */
  MARGIN_HEALTH_STATUS_WARNING,
  /**
   * The account's recorded health state is critical, corresponding to an urgent margin-call
   * condition under the applicable margin methodology.
   */
  MARGIN_HEALTH_STATUS_CRITICAL,
  /** The account is recorded as suspended under the applicable margin methodology. */
  MARGIN_HEALTH_STATUS_SUSPENDED,
  /** The account is recorded as restricted under the applicable margin methodology. */
  MARGIN_HEALTH_STATUS_RESTRICTED,
  /**
   * The account is in a pre-liquidation state. Liquidation may commence under the applicable margin
   * methodology.
   */
  MARGIN_HEALTH_STATUS_PRE_LIQUIDATION,
  /** Liquidation has commenced. */
  MARGIN_HEALTH_STATUS_LIQUIDATING,
  /**
   * The account's recorded health state is in deficit. Margin-call issuance follows the applicable
   * margin methodology.
   */
  MARGIN_HEALTH_STATUS_IN_DEFICIT
}
