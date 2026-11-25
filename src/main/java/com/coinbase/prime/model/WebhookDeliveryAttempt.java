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

/** WebhookDeliveryAttempt records the outcome and timing of an individual delivery attempt. */
public class WebhookDeliveryAttempt {
  @JsonProperty("delivery_attempt_id")
  private String deliveryAttemptId;

  @JsonProperty("delivery_id")
  private String deliveryId;

  @JsonProperty("attempt_number")
  private Integer attemptNumber;

  @JsonProperty("status_code")
  private Integer statusCode;

  @JsonProperty("latency_ms")
  private Integer latencyMs;

  /** WebhookErrorType classifies the outcome of a webhook delivery attempt. */
  @JsonProperty("error_type")
  private WebhookErrorType errorType;

  @JsonProperty("error_message")
  private String errorMessage;

  @JsonProperty("attempted_at")
  private OffsetDateTime attemptedAt;

  public WebhookDeliveryAttempt() {}

  public WebhookDeliveryAttempt(Builder builder) {
    this.deliveryAttemptId = builder.deliveryAttemptId;
    this.deliveryId = builder.deliveryId;
    this.attemptNumber = builder.attemptNumber;
    this.statusCode = builder.statusCode;
    this.latencyMs = builder.latencyMs;
    this.errorType = builder.errorType;
    this.errorMessage = builder.errorMessage;
    this.attemptedAt = builder.attemptedAt;
  }

  public String getDeliveryAttemptId() {
    return deliveryAttemptId;
  }

  public void setDeliveryAttemptId(String deliveryAttemptId) {
    this.deliveryAttemptId = deliveryAttemptId;
  }

  public String getDeliveryId() {
    return deliveryId;
  }

  public void setDeliveryId(String deliveryId) {
    this.deliveryId = deliveryId;
  }

  public Integer getAttemptNumber() {
    return attemptNumber;
  }

  public void setAttemptNumber(Integer attemptNumber) {
    this.attemptNumber = attemptNumber;
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

  public OffsetDateTime getAttemptedAt() {
    return attemptedAt;
  }

  public void setAttemptedAt(OffsetDateTime attemptedAt) {
    this.attemptedAt = attemptedAt;
  }

  public static class Builder {
    private String deliveryAttemptId;

    private String deliveryId;

    private Integer attemptNumber;

    private Integer statusCode;

    private Integer latencyMs;

    private WebhookErrorType errorType;

    private String errorMessage;

    private OffsetDateTime attemptedAt;

    public Builder deliveryAttemptId(String deliveryAttemptId) {
      this.deliveryAttemptId = deliveryAttemptId;
      return this;
    }

    public Builder deliveryId(String deliveryId) {
      this.deliveryId = deliveryId;
      return this;
    }

    public Builder attemptNumber(Integer attemptNumber) {
      this.attemptNumber = attemptNumber;
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

    public Builder errorType(WebhookErrorType errorType) {
      this.errorType = errorType;
      return this;
    }

    public Builder errorMessage(String errorMessage) {
      this.errorMessage = errorMessage;
      return this;
    }

    public Builder attemptedAt(OffsetDateTime attemptedAt) {
      this.attemptedAt = attemptedAt;
      return this;
    }

    public WebhookDeliveryAttempt build() {
      return new WebhookDeliveryAttempt(this);
    }
  }
}
