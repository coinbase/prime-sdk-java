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
 * - HEALTHY_THRESHOLD: Margin level is healthy
 * - DEFICIT_THRESHOLD: Margin level is breaching the deficit threshold (DT) which will result in the issuance of a Margin Call if this is still the case by the scheduled next Margin Call time (as defined in the margin methodology)
 * - WARNING_THRESHOLD: Margin level is breaching the warning threshold (WT) which will result in the issuance of a Margin Call if this is still the case by the scheduled next Margin Call (as defined in the margin methodology). WT is differentiated from DT in that it means margin health is approaching the UMCT
 * - URGENT_MARGIN_CALL_THRESHOLD: Margin level is breaching the UMCT and, as defined in the margin methodology, this will trigger an urgent margin call
 * - LIQUIDATION_THRESHOLD: Margin level is breaching the liquidation threshold (LT) and, as defined in the margin methodology, this will trigger the SESSION_LOCKED control status and liquidation may commence.
 */
public enum PrimeXMMarginLevel {
  XM_MARGIN_LEVEL_UNSPECIFIED,
  /**
   * Margin level is healthy
   */
  HEALTHY_THRESHOLD,
  /**
   * Margin level is breaching the deficit threshold (DT) which will result in the issuance of a Margin Call if this is still the case by the scheduled next Margin Call time (as defined in the margin methodology)
   */
  DEFICIT_THRESHOLD,
  /**
   * Margin level is breaching the warning threshold (WT) which will result in the issuance of a Margin Call if this is still the case by the scheduled next Margin Call (as defined in the margin methodology). WT is differentiated from DT in that it means margin health is approaching the UMCT
   */
  WARNING_THRESHOLD,
  /**
   * Margin level is breaching the UMCT and, as defined in the margin methodology, this will trigger an urgent margin call
   */
  URGENT_MARGIN_CALL_THRESHOLD,
  /**
   * Margin level is breaching the liquidation threshold (LT) and, as defined in the margin methodology, this will trigger the SESSION_LOCKED control status and liquidation may commence.
   */
  LIQUIDATION_THRESHOLD
}

