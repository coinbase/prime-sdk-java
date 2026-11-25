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

import com.coinbase.prime.model.enums.WebhookAuthType;
import com.coinbase.prime.model.enums.WebhookHealthStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Map;

/**
 * WebhookSubscription is the customer-facing representation of a webhook subscription. auth secrets
 * (hmac_secret, oauth client_secret) are never included; hmac_secret is returned exactly once in
 * CreateWebhookSubscriptionResponse.
 */
public class WebhookSubscription {
  @JsonProperty("subscription_id")
  private String subscriptionId;

  @JsonProperty("event_types")
  private List<String> eventTypes;

  @JsonProperty("target_url")
  private String targetUrl;

  /**
   * WebhookAuthType is how Prime authenticates to your endpoint, on top of the signatures. Every
   * delivery is signed twice, whatever the auth type: with HMAC-SHA256 in X-Coinbase-Signature,
   * keyed by the subscription's hmac_secret, and with Ed25519 in X-Coinbase-Signature-Ed25519,
   * verifiable with the public key named by X-Coinbase-Signature-Key-Id at /.well-known/jwks.json.
   * Verify either one. - WEBHOOK_AUTH_TYPE_SIGNATURE_ONLY: Signatures only. Used when a
   * subscription is created without auth_type. - WEBHOOK_AUTH_TYPE_OAUTH: Signatures plus an OAuth
   * 2.0 bearer token, fetched with oauth_config, in the Authorization header. -
   * WEBHOOK_AUTH_TYPE_MTLS: Signatures plus a Coinbase client certificate (mutual TLS). -
   * WEBHOOK_AUTH_TYPE_OAUTH_MTLS: Signatures plus both an OAuth 2.0 bearer token and a client
   * certificate.
   */
  @JsonProperty("auth_type")
  private WebhookAuthType authType;

  /**
   * WebhookOAuthConfig describes a subscription's OAuth configuration without its client secret.
   */
  @JsonProperty("oauth_config")
  private WebhookOAuthConfig oauthConfig;

  @JsonProperty("custom_headers")
  private Map<String, String> customHeaders;

  @JsonProperty("is_enabled")
  private boolean isEnabled;

  private String description;

  @JsonProperty("created_by")
  private String createdBy;

  @JsonProperty("updated_by")
  private String updatedBy;

  @JsonProperty("created_at")
  private OffsetDateTime createdAt;

  @JsonProperty("updated_at")
  private OffsetDateTime updatedAt;

  /** WebhookHealthStatus describes the current delivery health of a subscription. */
  @JsonProperty("health_status")
  private WebhookHealthStatus healthStatus;

  private String version;

  public WebhookSubscription() {}

  public WebhookSubscription(Builder builder) {
    this.subscriptionId = builder.subscriptionId;
    this.eventTypes = builder.eventTypes;
    this.targetUrl = builder.targetUrl;
    this.authType = builder.authType;
    this.oauthConfig = builder.oauthConfig;
    this.customHeaders = builder.customHeaders;
    this.isEnabled = builder.isEnabled;
    this.description = builder.description;
    this.createdBy = builder.createdBy;
    this.updatedBy = builder.updatedBy;
    this.createdAt = builder.createdAt;
    this.updatedAt = builder.updatedAt;
    this.healthStatus = builder.healthStatus;
    this.version = builder.version;
  }

  public String getSubscriptionId() {
    return subscriptionId;
  }

  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  public List<String> getEventTypes() {
    return eventTypes;
  }

  public void setEventTypes(List<String> eventTypes) {
    this.eventTypes = eventTypes;
  }

  public String getTargetUrl() {
    return targetUrl;
  }

  public void setTargetUrl(String targetUrl) {
    this.targetUrl = targetUrl;
  }

  public WebhookAuthType getAuthType() {
    return authType;
  }

  public void setAuthType(WebhookAuthType authType) {
    this.authType = authType;
  }

  public WebhookOAuthConfig getOauthConfig() {
    return oauthConfig;
  }

  public void setOauthConfig(WebhookOAuthConfig oauthConfig) {
    this.oauthConfig = oauthConfig;
  }

  public Map<String, String> getCustomHeaders() {
    return customHeaders;
  }

  public void setCustomHeaders(Map<String, String> customHeaders) {
    this.customHeaders = customHeaders;
  }

  public boolean getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getCreatedBy() {
    return createdBy;
  }

  public void setCreatedBy(String createdBy) {
    this.createdBy = createdBy;
  }

  public String getUpdatedBy() {
    return updatedBy;
  }

  public void setUpdatedBy(String updatedBy) {
    this.updatedBy = updatedBy;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }

  public OffsetDateTime getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(OffsetDateTime updatedAt) {
    this.updatedAt = updatedAt;
  }

  public WebhookHealthStatus getHealthStatus() {
    return healthStatus;
  }

  public void setHealthStatus(WebhookHealthStatus healthStatus) {
    this.healthStatus = healthStatus;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public static class Builder {
    private String subscriptionId;

    private List<String> eventTypes;

    private String targetUrl;

    private WebhookAuthType authType;

    private WebhookOAuthConfig oauthConfig;

    private Map<String, String> customHeaders;

    private boolean isEnabled;

    private String description;

    private String createdBy;

    private String updatedBy;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

    private WebhookHealthStatus healthStatus;

    private String version;

    public Builder subscriptionId(String subscriptionId) {
      this.subscriptionId = subscriptionId;
      return this;
    }

    public Builder eventTypes(List<String> eventTypes) {
      this.eventTypes = eventTypes;
      return this;
    }

    public Builder targetUrl(String targetUrl) {
      this.targetUrl = targetUrl;
      return this;
    }

    public Builder authType(WebhookAuthType authType) {
      this.authType = authType;
      return this;
    }

    public Builder oauthConfig(WebhookOAuthConfig oauthConfig) {
      this.oauthConfig = oauthConfig;
      return this;
    }

    public Builder customHeaders(Map<String, String> customHeaders) {
      this.customHeaders = customHeaders;
      return this;
    }

    public Builder isEnabled(boolean isEnabled) {
      this.isEnabled = isEnabled;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder createdBy(String createdBy) {
      this.createdBy = createdBy;
      return this;
    }

    public Builder updatedBy(String updatedBy) {
      this.updatedBy = updatedBy;
      return this;
    }

    public Builder createdAt(OffsetDateTime createdAt) {
      this.createdAt = createdAt;
      return this;
    }

    public Builder updatedAt(OffsetDateTime updatedAt) {
      this.updatedAt = updatedAt;
      return this;
    }

    public Builder healthStatus(WebhookHealthStatus healthStatus) {
      this.healthStatus = healthStatus;
      return this;
    }

    public Builder version(String version) {
      this.version = version;
      return this;
    }

    public WebhookSubscription build() {
      return new WebhookSubscription(this);
    }
  }
}
