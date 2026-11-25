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

import com.fasterxml.jackson.annotation.JsonProperty;

/** Replay Webhook Event */
public class ReplayWebhookEventResponse {
  @JsonProperty("deliveries_queued")
  private Integer deliveriesQueued;

  @JsonProperty("delivery_ids")
  private String[] deliveryIds;

  @JsonProperty("delivery_ids_truncated")
  private Boolean deliveryIdsTruncated;

  public ReplayWebhookEventResponse() {}

  public Integer getDeliveriesQueued() {
    return deliveriesQueued;
  }

  public void setDeliveriesQueued(Integer deliveriesQueued) {
    this.deliveriesQueued = deliveriesQueued;
  }

  public String[] getDeliveryIds() {
    return deliveryIds;
  }

  public void setDeliveryIds(String[] deliveryIds) {
    this.deliveryIds = deliveryIds;
  }

  public Boolean getDeliveryIdsTruncated() {
    return deliveryIdsTruncated;
  }

  public void setDeliveryIdsTruncated(Boolean deliveryIdsTruncated) {
    this.deliveryIdsTruncated = deliveryIdsTruncated;
  }
}
