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

import com.coinbase.prime.model.enums.WebhookHealthStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

/** WebhookSubscriptionSummary summarizes a subscription's health and delivery statistics. */
public class WebhookSubscriptionSummary {
  @JsonProperty("subscription_id")
  private String subscriptionId;

  private String description;

  /** WebhookHealthStatus describes the current delivery health of a subscription. */
  @JsonProperty("health_status")
  private WebhookHealthStatus healthStatus;

  @JsonProperty("events_in_period")
  private String eventsInPeriod;

  @JsonProperty("success_rate")
  private Double successRate;

  public WebhookSubscriptionSummary() {}

  public WebhookSubscriptionSummary(Builder builder) {
    this.subscriptionId = builder.subscriptionId;
    this.description = builder.description;
    this.healthStatus = builder.healthStatus;
    this.eventsInPeriod = builder.eventsInPeriod;
    this.successRate = builder.successRate;
  }

  public String getSubscriptionId() {
    return subscriptionId;
  }

  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public WebhookHealthStatus getHealthStatus() {
    return healthStatus;
  }

  public void setHealthStatus(WebhookHealthStatus healthStatus) {
    this.healthStatus = healthStatus;
  }

  public String getEventsInPeriod() {
    return eventsInPeriod;
  }

  public void setEventsInPeriod(String eventsInPeriod) {
    this.eventsInPeriod = eventsInPeriod;
  }

  public Double getSuccessRate() {
    return successRate;
  }

  public void setSuccessRate(Double successRate) {
    this.successRate = successRate;
  }

  public static class Builder {
    private String subscriptionId;

    private String description;

    private WebhookHealthStatus healthStatus;

    private String eventsInPeriod;

    private Double successRate;

    public Builder subscriptionId(String subscriptionId) {
      this.subscriptionId = subscriptionId;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder healthStatus(WebhookHealthStatus healthStatus) {
      this.healthStatus = healthStatus;
      return this;
    }

    public Builder eventsInPeriod(String eventsInPeriod) {
      this.eventsInPeriod = eventsInPeriod;
      return this;
    }

    public Builder successRate(Double successRate) {
      this.successRate = successRate;
      return this;
    }

    public WebhookSubscriptionSummary build() {
      return new WebhookSubscriptionSummary(this);
    }
  }
}
