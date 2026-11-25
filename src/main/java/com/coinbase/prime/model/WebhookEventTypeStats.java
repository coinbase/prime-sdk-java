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

import com.fasterxml.jackson.annotation.JsonProperty;

/** WebhookEventTypeStats contains aggregated delivery statistics for one event type. */
public class WebhookEventTypeStats {
  @JsonProperty("event_type")
  private String eventType;

  private String total;

  private String successful;

  @JsonProperty("success_rate")
  private Double successRate;

  @JsonProperty("avg_latency_ms")
  private String avgLatencyMs;

  private String failed;

  @JsonProperty("in_progress")
  private String inProgress;

  public WebhookEventTypeStats() {}

  public WebhookEventTypeStats(Builder builder) {
    this.eventType = builder.eventType;
    this.total = builder.total;
    this.successful = builder.successful;
    this.successRate = builder.successRate;
    this.avgLatencyMs = builder.avgLatencyMs;
    this.failed = builder.failed;
    this.inProgress = builder.inProgress;
  }

  public String getEventType() {
    return eventType;
  }

  public void setEventType(String eventType) {
    this.eventType = eventType;
  }

  public String getTotal() {
    return total;
  }

  public void setTotal(String total) {
    this.total = total;
  }

  public String getSuccessful() {
    return successful;
  }

  public void setSuccessful(String successful) {
    this.successful = successful;
  }

  public Double getSuccessRate() {
    return successRate;
  }

  public void setSuccessRate(Double successRate) {
    this.successRate = successRate;
  }

  public String getAvgLatencyMs() {
    return avgLatencyMs;
  }

  public void setAvgLatencyMs(String avgLatencyMs) {
    this.avgLatencyMs = avgLatencyMs;
  }

  public String getFailed() {
    return failed;
  }

  public void setFailed(String failed) {
    this.failed = failed;
  }

  public String getInProgress() {
    return inProgress;
  }

  public void setInProgress(String inProgress) {
    this.inProgress = inProgress;
  }

  public static class Builder {
    private String eventType;

    private String total;

    private String successful;

    private Double successRate;

    private String avgLatencyMs;

    private String failed;

    private String inProgress;

    public Builder eventType(String eventType) {
      this.eventType = eventType;
      return this;
    }

    public Builder total(String total) {
      this.total = total;
      return this;
    }

    public Builder successful(String successful) {
      this.successful = successful;
      return this;
    }

    public Builder successRate(Double successRate) {
      this.successRate = successRate;
      return this;
    }

    public Builder avgLatencyMs(String avgLatencyMs) {
      this.avgLatencyMs = avgLatencyMs;
      return this;
    }

    public Builder failed(String failed) {
      this.failed = failed;
      return this;
    }

    public Builder inProgress(String inProgress) {
      this.inProgress = inProgress;
      return this;
    }

    public WebhookEventTypeStats build() {
      return new WebhookEventTypeStats(this);
    }
  }
}
