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

import com.coinbase.prime.model.WebhookEventTypeStats;
import com.coinbase.prime.model.WebhookRecentFailure;
import com.coinbase.prime.model.enums.WebhookHealthStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Get Webhook Subscription Stats */
public class GetWebhookSubscriptionStatsResponse {
  @JsonProperty("subscription_id")
  private String subscriptionId;

  @JsonProperty("health_status")
  private WebhookHealthStatus healthStatus;

  @JsonProperty("total_events")
  private String totalEvents;

  @JsonProperty("successful")
  private String successful;

  @JsonProperty("failed")
  private String failed;

  @JsonProperty("success_rate")
  private Double successRate;

  @JsonProperty("avg_latency_ms")
  private String avgLatencyMs;

  @JsonProperty("p95_latency_ms")
  private String p95LatencyMs;

  @JsonProperty("p99_latency_ms")
  private String p99LatencyMs;

  @JsonProperty("event_type_breakdown")
  private WebhookEventTypeStats[] eventTypeBreakdown;

  @JsonProperty("recent_failures")
  private WebhookRecentFailure[] recentFailures;

  @JsonProperty("in_progress")
  private String inProgress;

  public GetWebhookSubscriptionStatsResponse() {}

  public String getSubscriptionId() {
    return subscriptionId;
  }

  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  public WebhookHealthStatus getHealthStatus() {
    return healthStatus;
  }

  public void setHealthStatus(WebhookHealthStatus healthStatus) {
    this.healthStatus = healthStatus;
  }

  public String getTotalEvents() {
    return totalEvents;
  }

  public void setTotalEvents(String totalEvents) {
    this.totalEvents = totalEvents;
  }

  public String getSuccessful() {
    return successful;
  }

  public void setSuccessful(String successful) {
    this.successful = successful;
  }

  public String getFailed() {
    return failed;
  }

  public void setFailed(String failed) {
    this.failed = failed;
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

  public String getP95LatencyMs() {
    return p95LatencyMs;
  }

  public void setP95LatencyMs(String p95LatencyMs) {
    this.p95LatencyMs = p95LatencyMs;
  }

  public String getP99LatencyMs() {
    return p99LatencyMs;
  }

  public void setP99LatencyMs(String p99LatencyMs) {
    this.p99LatencyMs = p99LatencyMs;
  }

  public WebhookEventTypeStats[] getEventTypeBreakdown() {
    return eventTypeBreakdown;
  }

  public void setEventTypeBreakdown(WebhookEventTypeStats[] eventTypeBreakdown) {
    this.eventTypeBreakdown = eventTypeBreakdown;
  }

  public WebhookRecentFailure[] getRecentFailures() {
    return recentFailures;
  }

  public void setRecentFailures(WebhookRecentFailure[] recentFailures) {
    this.recentFailures = recentFailures;
  }

  public String getInProgress() {
    return inProgress;
  }

  public void setInProgress(String inProgress) {
    this.inProgress = inProgress;
  }
}
