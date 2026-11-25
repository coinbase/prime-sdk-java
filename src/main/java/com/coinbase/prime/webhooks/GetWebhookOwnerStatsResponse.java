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

import com.coinbase.prime.model.WebhookRecentFailure;
import com.coinbase.prime.model.WebhookSubscriptionSummary;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Get Webhook Owner Stats */
public class GetWebhookOwnerStatsResponse {
  @JsonProperty("total_subscriptions")
  private String totalSubscriptions;

  @JsonProperty("active_subscriptions")
  private String activeSubscriptions;

  @JsonProperty("total_events")
  private String totalEvents;

  @JsonProperty("overall_success_rate")
  private Double overallSuccessRate;

  @JsonProperty("avg_latency_ms")
  private String avgLatencyMs;

  @JsonProperty("subscriptions")
  private WebhookSubscriptionSummary[] subscriptions;

  @JsonProperty("recent_failures")
  private WebhookRecentFailure[] recentFailures;

  public GetWebhookOwnerStatsResponse() {}

  public String getTotalSubscriptions() {
    return totalSubscriptions;
  }

  public void setTotalSubscriptions(String totalSubscriptions) {
    this.totalSubscriptions = totalSubscriptions;
  }

  public String getActiveSubscriptions() {
    return activeSubscriptions;
  }

  public void setActiveSubscriptions(String activeSubscriptions) {
    this.activeSubscriptions = activeSubscriptions;
  }

  public String getTotalEvents() {
    return totalEvents;
  }

  public void setTotalEvents(String totalEvents) {
    this.totalEvents = totalEvents;
  }

  public Double getOverallSuccessRate() {
    return overallSuccessRate;
  }

  public void setOverallSuccessRate(Double overallSuccessRate) {
    this.overallSuccessRate = overallSuccessRate;
  }

  public String getAvgLatencyMs() {
    return avgLatencyMs;
  }

  public void setAvgLatencyMs(String avgLatencyMs) {
    this.avgLatencyMs = avgLatencyMs;
  }

  public WebhookSubscriptionSummary[] getSubscriptions() {
    return subscriptions;
  }

  public void setSubscriptions(WebhookSubscriptionSummary[] subscriptions) {
    this.subscriptions = subscriptions;
  }

  public WebhookRecentFailure[] getRecentFailures() {
    return recentFailures;
  }

  public void setRecentFailures(WebhookRecentFailure[] recentFailures) {
    this.recentFailures = recentFailures;
  }
}
