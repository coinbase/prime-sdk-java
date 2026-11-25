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

/** WebhookErrorType classifies the outcome of a webhook delivery attempt. */
public enum WebhookErrorType {
  WEBHOOK_ERROR_TYPE_SUCCESS,
  WEBHOOK_ERROR_TYPE_HTTP_4_XX,
  WEBHOOK_ERROR_TYPE_HTTP_5_XX,
  WEBHOOK_ERROR_TYPE_TIMEOUT,
  WEBHOOK_ERROR_TYPE_CONNECTION_ERROR,
  WEBHOOK_ERROR_TYPE_HTTP_3_XX,
  WEBHOOK_ERROR_TYPE_INVALID_EVENT,
  WEBHOOK_ERROR_TYPE_CANCELED
}
