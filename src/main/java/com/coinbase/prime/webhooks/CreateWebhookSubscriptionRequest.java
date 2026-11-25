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

/** Create Webhook Subscription */
public class CreateWebhookSubscriptionRequest {
  /** Populated from the URL path. */
  @JsonProperty(required = true, value = "portfolio_id")
  @JsonIgnore
  private String portfolioId;

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

  @JsonProperty("idempotency_key")
  private String idempotencyKey;

  public CreateWebhookSubscriptionRequest() {}

  public CreateWebhookSubscriptionRequest(Builder builder) {
    this.portfolioId = builder.portfolioId;
    this.eventTypes = builder.eventTypes;
    this.targetUrl = builder.targetUrl;
    this.authType = builder.authType;
    this.oauthConfig = builder.oauthConfig;
    this.customHeaders = builder.customHeaders;
    this.description = builder.description;
    this.idempotencyKey = builder.idempotencyKey;
  }

  public String getPortfolioId() {
    return portfolioId;
  }

  public void setPortfolioId(String portfolioId) {
    this.portfolioId = portfolioId;
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

  public String getIdempotencyKey() {
    return idempotencyKey;
  }

  public void setIdempotencyKey(String idempotencyKey) {
    this.idempotencyKey = idempotencyKey;
  }

  public static class Builder {
    private String portfolioId;
    private String[] eventTypes;
    private String targetUrl;
    private WebhookAuthType authType;
    private WebhookOAuthConfigInput oauthConfig;
    private Map<String, String> customHeaders;
    private String description;
    private String idempotencyKey;

    public Builder() {}

    public Builder portfolioId(String portfolioId) {
      this.portfolioId = portfolioId;
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

    public Builder idempotencyKey(String idempotencyKey) {
      this.idempotencyKey = idempotencyKey;
      return this;
    }

    public CreateWebhookSubscriptionRequest build() throws CoinbaseClientException {
      validate();
      return new CreateWebhookSubscriptionRequest(this);
    }

    private void validate() throws CoinbaseClientException {
      if (isNullOrEmpty(this.portfolioId)) {
        throw new CoinbaseClientException("PortfolioId is required");
      }
    }
  }
}
