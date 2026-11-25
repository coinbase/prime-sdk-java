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
 * Calculated margin level based on the account's equity, margin requirement, margin limit, and
 * configured thresholds. This reflects the current calculation for live responses or the recorded
 * calculation for historical responses. It can differ from `health_status`, which is maintained by
 * the margin-management process. The level alone doesn't determine whether a margin call is open or
 * whether trading and withdrawals are allowed. - MARGIN_LEVEL_UNSPECIFIED: No calculated margin
 * level is provided. - MARGIN_LEVEL_HEALTHY: Account equity is at or above the margin requirement.
 * - MARGIN_LEVEL_DEFICIT: The calculation places the account in the deficit band. Margin-call
 * issuance follows the applicable margin methodology. - MARGIN_LEVEL_WARNING: The calculation
 * places the account in the warning band, approaching the critical threshold. -
 * MARGIN_LEVEL_CRITICAL: The calculation places the account in the critical band. -
 * MARGIN_LEVEL_LIQUIDATION: The calculation places the account in the liquidation band. This value
 * alone doesn't indicate that liquidation has commenced.
 */
public enum MarginLevel {
  /** Account equity is at or above the margin requirement. */
  MARGIN_LEVEL_HEALTHY,
  /**
   * The calculation places the account in the deficit band. Margin-call issuance follows the
   * applicable margin methodology.
   */
  MARGIN_LEVEL_DEFICIT,
  /** The calculation places the account in the warning band, approaching the critical threshold. */
  MARGIN_LEVEL_WARNING,
  /** The calculation places the account in the critical band. */
  MARGIN_LEVEL_CRITICAL,
  /**
   * The calculation places the account in the liquidation band. This value alone doesn't indicate
   * that liquidation has commenced.
   */
  MARGIN_LEVEL_LIQUIDATION
}
