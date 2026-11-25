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

package com.coinbase.prime.model;

import com.coinbase.prime.model.enums.WebhookErrorType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/** WebhookRecentFailure identifies a recent failed delivery attempt. */
public class WebhookRecentFailure {
  @JsonProperty("delivery_id")
  private String deliveryId;

  @JsonProperty("subscription_id")
  private String subscriptionId;

  @JsonProperty("event_id")
  private String eventId;

  @JsonProperty("event_type")
  private String eventType;

  /** WebhookErrorType classifies the outcome of a webhook delivery attempt. */
  @JsonProperty("error_type")
  private WebhookErrorType errorType;

  @JsonProperty("attempt_number")
  private Integer attemptNumber;

  @JsonProperty("attempted_at")
  private OffsetDateTime attemptedAt;

  public WebhookRecentFailure() {}

  public WebhookRecentFailure(Builder builder) {
    this.deliveryId = builder.deliveryId;
    this.subscriptionId = builder.subscriptionId;
    this.eventId = builder.eventId;
    this.eventType = builder.eventType;
    this.errorType = builder.errorType;
    this.attemptNumber = builder.attemptNumber;
    this.attemptedAt = builder.attemptedAt;
  }

  public String getDeliveryId() {
    return deliveryId;
  }

  public void setDeliveryId(String deliveryId) {
    this.deliveryId = deliveryId;
  }

  public String getSubscriptionId() {
    return subscriptionId;
  }

  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

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

  public WebhookErrorType getErrorType() {
    return errorType;
  }

  public void setErrorType(WebhookErrorType errorType) {
    this.errorType = errorType;
  }

  public Integer getAttemptNumber() {
    return attemptNumber;
  }

  public void setAttemptNumber(Integer attemptNumber) {
    this.attemptNumber = attemptNumber;
  }

  public OffsetDateTime getAttemptedAt() {
    return attemptedAt;
  }

  public void setAttemptedAt(OffsetDateTime attemptedAt) {
    this.attemptedAt = attemptedAt;
  }

  public static class Builder {
    private String deliveryId;

    private String subscriptionId;

    private String eventId;

    private String eventType;

    private WebhookErrorType errorType;

    private Integer attemptNumber;

    private OffsetDateTime attemptedAt;

    public Builder deliveryId(String deliveryId) {
      this.deliveryId = deliveryId;
      return this;
    }

    public Builder subscriptionId(String subscriptionId) {
      this.subscriptionId = subscriptionId;
      return this;
    }

    public Builder eventId(String eventId) {
      this.eventId = eventId;
      return this;
    }

    public Builder eventType(String eventType) {
      this.eventType = eventType;
      return this;
    }

    public Builder errorType(WebhookErrorType errorType) {
      this.errorType = errorType;
      return this;
    }

    public Builder attemptNumber(Integer attemptNumber) {
      this.attemptNumber = attemptNumber;
      return this;
    }

    public Builder attemptedAt(OffsetDateTime attemptedAt) {
      this.attemptedAt = attemptedAt;
      return this;
    }

    public WebhookRecentFailure build() {
      return new WebhookRecentFailure(this);
    }
  }
}
