/*
 * Copyright 2026-present Coinbase Global, Inc.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.coinbase.prime.webhooks;

import com.coinbase.prime.model.WebhookDeliveryLogEntry;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Get Webhook Delivery Logs */
public class GetWebhookDeliveryLogsResponse {
  @JsonProperty("entries")
  private WebhookDeliveryLogEntry[] entries;

  @JsonProperty("next_cursor")
  private String nextCursor;

  @JsonProperty("has_next")
  private Boolean hasNext;

  public GetWebhookDeliveryLogsResponse() {}

  public WebhookDeliveryLogEntry[] getEntries() {
    return entries;
  }

  public void setEntries(WebhookDeliveryLogEntry[] entries) {
    this.entries = entries;
  }

  public String getNextCursor() {
    return nextCursor;
  }

  public void setNextCursor(String nextCursor) {
    this.nextCursor = nextCursor;
  }

  public Boolean getHasNext() {
    return hasNext;
  }

  public void setHasNext(Boolean hasNext) {
    this.hasNext = hasNext;
  }
}
