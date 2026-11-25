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

import static com.coinbase.core.utils.Utils.isNullOrEmpty;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.model.WebhookOAuthConfigInput;
import com.coinbase.prime.model.enums.WebhookAuthType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;

/** Update Webhook Subscription */
public class UpdateWebhookSubscriptionRequest {
  @JsonProperty(required = true, value = "portfolio_id")
  @JsonIgnore
  private String portfolioId;

  @JsonProperty(required = true, value = "subscription_id")
  @JsonIgnore
  private String subscriptionId;

  @JsonProperty("event_types")
  private String[] eventTypes;

  @JsonProperty("target_url")
  private String targetUrl;

  @JsonProperty("auth_type")
  private WebhookAuthType authType;

  @JsonProperty("oauth_config")
  private WebhookOAuthConfigInput oauthConfig;

  @JsonProperty("custom_headers")
  private Map<String, String> customHeaders;

  @JsonProperty("description")
  private String description;

  @JsonProperty("is_enabled")
  private Boolean isEnabled;

  @JsonProperty("update_mask")
  private String updateMask;

  @JsonProperty("expected_version")
  private String expectedVersion;

  public UpdateWebhookSubscriptionRequest() {}

  public UpdateWebhookSubscriptionRequest(Builder builder) {
    this.portfolioId = builder.portfolioId;
    this.subscriptionId = builder.subscriptionId;
    this.eventTypes = builder.eventTypes;
    this.targetUrl = builder.targetUrl;
    this.authType = builder.authType;
    this.oauthConfig = builder.oauthConfig;
    this.customHeaders = builder.customHeaders;
    this.description = builder.description;
    this.isEnabled = builder.isEnabled;
    this.updateMask = builder.updateMask;
    this.expectedVersion = builder.expectedVersion;
  }

  public String getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(String portfolioId) {
    this.portfolioId = portfolioId;
  }

  public String getSubscriptionId() {
    return subscriptionId;
  }

  public void setSubscriptionId(String subscriptionId) {
    this.subscriptionId = subscriptionId;
  }

  public String[] getEventTypes() {
    return eventTypes;
  }

  public void setEventTypes(String[] eventTypes) {
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

  public WebhookOAuthConfigInput getOauthConfig() {
    return oauthConfig;
  }

  public void setOauthConfig(WebhookOAuthConfigInput oauthConfig) {
    this.oauthConfig = oauthConfig;
  }

  public Map<String, String> getCustomHeaders() {
    return customHeaders;
  }

  public void setCustomHeaders(Map<String, String> customHeaders) {
    this.customHeaders = customHeaders;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Boolean getIsEnabled() {
    return isEnabled;
  }

  public void setIsEnabled(Boolean isEnabled) {
    this.isEnabled = isEnabled;
  }

  public String getUpdateMask() {
    return updateMask;
  }

  public void setUpdateMask(String updateMask) {
    this.updateMask = updateMask;
  }

  public String getExpectedVersion() {
    return expectedVersion;
  }

  public void setExpectedVersion(String expectedVersion) {
    this.expectedVersion = expectedVersion;
  }

  public static class Builder {
    private String portfolioId;
    private String subscriptionId;
    private String[] eventTypes;
    private String targetUrl;
    private WebhookAuthType authType;
    private WebhookOAuthConfigInput oauthConfig;
    private Map<String, String> customHeaders;
    private String description;
    private Boolean isEnabled;
    private String updateMask;
    private String expectedVersion;

    public Builder() {}

    public Builder portfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
      return this;
    }

    public Builder subscriptionId(String subscriptionId) {
      this.subscriptionId = subscriptionId;
      return this;
    }

    public Builder eventTypes(String[] eventTypes) {
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

    public Builder oauthConfig(WebhookOAuthConfigInput oauthConfig) {
      this.oauthConfig = oauthConfig;
      return this;
    }

    public Builder customHeaders(Map<String, String> customHeaders) {
      this.customHeaders = customHeaders;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public Builder isEnabled(Boolean isEnabled) {
      this.isEnabled = isEnabled;
      return this;
    }

    public Builder updateMask(String updateMask) {
      this.updateMask = updateMask;
      return this;
    }

    public Builder expectedVersion(String expectedVersion) {
      this.expectedVersion = expectedVersion;
      return this;
    }

    public UpdateWebhookSubscriptionRequest build() throws CoinbaseClientException {
      validate();
      return new UpdateWebhookSubscriptionRequest(this);
    }

    private void validate() throws CoinbaseClientException {
      if (isNullOrEmpty(this.portfolioId)) {
        throw new CoinbaseClientException("PortfolioId is required");
      }
      if (isNullOrEmpty(this.subscriptionId)) {
        throw new CoinbaseClientException("SubscriptionId is required");
      }
    }
  }
}
