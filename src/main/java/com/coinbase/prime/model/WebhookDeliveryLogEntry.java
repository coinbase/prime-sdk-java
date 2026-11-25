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

import com.coinbase.prime.model.enums.WebhookDeliveryStatus;
import com.coinbase.prime.model.enums.WebhookErrorType;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;

/** WebhookDeliveryLogEntry summarizes a webhook event delivery and its latest attempt. */
public class WebhookDeliveryLogEntry {
  @JsonProperty("delivery_id")
  private String deliveryId;

  @JsonProperty("event_id")
  private String eventId;

  @JsonProperty("event_type")
  private String eventType;

  /** WebhookDeliveryStatus describes the delivery state of a webhook event. */
  @JsonProperty("delivery_status")
  private WebhookDeliveryStatus deliveryStatus;

  @JsonProperty("status_code")
  private Integer statusCode;

  @JsonProperty("latency_ms")
  private Integer latencyMs;

  @JsonProperty("attempt_number")
  private Integer attemptNumber;

  @JsonProperty("max_retries")
  private Integer maxRetries;

  /** WebhookErrorType classifies the outcome of a webhook delivery attempt. */
  @JsonProperty("error_type")
  private WebhookErrorType errorType;

  @JsonProperty("error_message")
  private String errorMessage;

  @JsonProperty("is_test")
  private boolean isTest;

  @JsonProperty("is_replay")
  private boolean isReplay;

  @JsonProperty("occurred_at")
  private OffsetDateTime occurredAt;

  @JsonProperty("attempted_at")
  private OffsetDateTime attemptedAt;

  public WebhookDeliveryLogEntry() {}

  public WebhookDeliveryLogEntry(Builder builder) {
    this.deliveryId = builder.deliveryId;
    this.eventId = builder.eventId;
    this.eventType = builder.eventType;
    this.deliveryStatus = builder.deliveryStatus;
    this.statusCode = builder.statusCode;
    this.latencyMs = builder.latencyMs;
    this.attemptNumber = builder.attemptNumber;
    this.maxRetries = builder.maxRetries;
    this.errorType = builder.errorType;
    this.errorMessage = builder.errorMessage;
    this.isTest = builder.isTest;
    this.isReplay = builder.isReplay;
    this.occurredAt = builder.occurredAt;
    this.attemptedAt = builder.attemptedAt;
  }

  public String getDeliveryId() {
    return deliveryId;
  }

  public void setDeliveryId(String deliveryId) {
    this.deliveryId = deliveryId;
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

  public WebhookDeliveryStatus getDeliveryStatus() {
    return deliveryStatus;
  }

  public void setDeliveryStatus(WebhookDeliveryStatus deliveryStatus) {
    this.deliveryStatus = deliveryStatus;
  }

  public Integer getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(Integer statusCode) {
    this.statusCode = statusCode;
  }

  public Integer getLatencyMs() {
    return latencyMs;
  }

  public void setLatencyMs(Integer latencyMs) {
    this.latencyMs = latencyMs;
  }

  public Integer getAttemptNumber() {
    return attemptNumber;
  }

  public void setAttemptNumber(Integer attemptNumber) {
    this.attemptNumber = attemptNumber;
  }

  public Integer getMaxRetries() {
    return maxRetries;
  }

  public void setMaxRetries(Integer maxRetries) {
    this.maxRetries = maxRetries;
  }

  public WebhookErrorType getErrorType() {
    return errorType;
  }

  public void setErrorType(WebhookErrorType errorType) {
    this.errorType = errorType;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public boolean getIsTest() {
    return isTest;
  }

  public void setIsTest(boolean isTest) {
    this.isTest = isTest;
  }

  public boolean getIsReplay() {
    return isReplay;
  }

  public void setIsReplay(boolean isReplay) {
    this.isReplay = isReplay;
  }

  public OffsetDateTime getOccurredAt() {
    return occurredAt;
  }

  public void setOccurredAt(OffsetDateTime occurredAt) {
    this.occurredAt = occurredAt;
  }

  public OffsetDateTime getAttemptedAt() {
    return attemptedAt;
  }

  public void setAttemptedAt(OffsetDateTime attemptedAt) {
    this.attemptedAt = attemptedAt;
  }

  public static class Builder {
    private String deliveryId;

    private String eventId;

    private String eventType;

    private WebhookDeliveryStatus deliveryStatus;

    private Integer statusCode;

    private Integer latencyMs;

    private Integer attemptNumber;

    private Integer maxRetries;

    private WebhookErrorType errorType;

    private String errorMessage;

    private boolean isTest;

    private boolean isReplay;

    private OffsetDateTime occurredAt;

    private OffsetDateTime attemptedAt;

    public Builder deliveryId(String deliveryId) {
      this.deliveryId = deliveryId;
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

    public Builder deliveryStatus(WebhookDeliveryStatus deliveryStatus) {
      this.deliveryStatus = deliveryStatus;
      return this;
    }

    public Builder statusCode(Integer statusCode) {
      this.statusCode = statusCode;
      return this;
    }

    public Builder latencyMs(Integer latencyMs) {
      this.latencyMs = latencyMs;
      return this;
    }

    public Builder attemptNumber(Integer attemptNumber) {
      this.attemptNumber = attemptNumber;
      return this;
    }

    public Builder maxRetries(Integer maxRetries) {
      this.maxRetries = maxRetries;
      return this;
    }

    public Builder errorType(WebhookErrorType errorType) {
      this.errorType = errorType;
      return this;
    }

    public Builder errorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
      return this;
    }

    public Builder isTest(boolean isTest) {
      this.isTest = isTest;
      return this;
    }

    public Builder isReplay(boolean isReplay) {
      this.isReplay = isReplay;
      return this;
    }

    public Builder occurredAt(OffsetDateTime occurredAt) {
      this.occurredAt = occurredAt;
      return this;
    }

    public Builder attemptedAt(OffsetDateTime attemptedAt) {
      this.attemptedAt = attemptedAt;
      return this;
    }

    public WebhookDeliveryLogEntry build() {
      return new WebhookDeliveryLogEntry(this);
    }
  }
}
