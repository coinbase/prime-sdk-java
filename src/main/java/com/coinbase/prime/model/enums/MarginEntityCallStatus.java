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
 * MarginEntityCallStatus summarizes the entity's open margin calls. When several calls are open, it
 * reflects the highest-priority one: aged, then urgent, then standard, then debit. `call_status`
 * describes outstanding calls. Use `margin_level` for calculated margin condition and
 * `control_status` for trading and withdrawal permissions. - MARGIN_ENTITY_CALL_STATUS_UNSPECIFIED:
 * No call status is provided. Don't infer that the entity has no margin calls from this value. -
 * MARGIN_ENTITY_CALL_STATUS_NO_CALL: The entity has no open margin calls. -
 * MARGIN_ENTITY_CALL_STATUS_OPEN_STANDARD_CALL: The entity has an open standard margin call and no
 * urgent or aged calls. - MARGIN_ENTITY_CALL_STATUS_OPEN_URGENT_CALL: The entity has an open urgent
 * margin call and no aged calls. - MARGIN_ENTITY_CALL_STATUS_AGED_CALL: At least one open margin
 * call is past its due time. This status alone does not indicate a contractual Event of Default or
 * that liquidation has begun. - MARGIN_ENTITY_CALL_STATUS_OPEN_DEBIT_CALL: The entity has an open
 * debit call and no standard, urgent, or aged calls.
 */
public enum MarginEntityCallStatus {
  /** The entity has no open margin calls. */
  MARGIN_ENTITY_CALL_STATUS_NO_CALL,
  /** The entity has an open standard margin call and no urgent or aged calls. */
  MARGIN_ENTITY_CALL_STATUS_OPEN_STANDARD_CALL,
  /** The entity has an open urgent margin call and no aged calls. */
  MARGIN_ENTITY_CALL_STATUS_OPEN_URGENT_CALL,
  /**
   * At least one open margin call is past its due time. This status alone does not indicate a
   * contractual Event of Default or that liquidation has begun.
   */
  MARGIN_ENTITY_CALL_STATUS_AGED_CALL,
  /** The entity has an open debit call and no standard, urgent, or aged calls. */
  MARGIN_ENTITY_CALL_STATUS_OPEN_DEBIT_CALL
}
