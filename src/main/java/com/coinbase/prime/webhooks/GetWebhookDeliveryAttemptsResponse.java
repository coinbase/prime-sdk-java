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

import com.coinbase.prime.model.WebhookDeliveryAttempt;
import com.coinbase.prime.model.enums.WebhookDeliveryStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Get Webhook Delivery Attempts */
public class GetWebhookDeliveryAttemptsResponse {
  @JsonProperty("event_id")
  private String eventId;

  @JsonProperty("event_type")
  private String eventType;

  @JsonProperty("subscription_id")
  private String subscriptionId;

  @JsonProperty("delivery_status")
  private WebhookDeliveryStatus deliveryStatus;

  @JsonProperty("attempts")
  private WebhookDeliveryAttempt[] attempts;

  /** event_payload is the raw JSON bytes delivered to the subscriber. */
  @JsonProperty("event_payload")
  private String eventPayload;

  public GetWebhookDeliveryAttemptsResponse() {}

  public String getEventId() {
    return eventId;
  }

  public void setEventId(String eventId) {
    this.eventId = eventId;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getSubscriptionId() {
    return subscriptionId;
  }

  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  public WebhookDeliveryStatus getDeliveryStatus() {
    return deliveryStatus;
  }

  public void setDeliveryStatus(WebhookDeliveryStatus deliveryStatus) {
    this.deliveryStatus = deliveryStatus;
  }

  public WebhookDeliveryAttempt[] getAttempts() {
    return attempts;
  }

  public void setAttempts(WebhookDeliveryAttempt[] attempts) {
    this.attempts = attempts;
  }

  public String getEventPayload() {
    return eventPayload;
  }

  public void setEventPayload(String eventPayload) {
    this.eventPayload = eventPayload;
  }
}
