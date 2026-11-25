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
import java.util.List;

/** WebhookOAuthConfig describes a subscription's OAuth configuration without its client secret. */
public class WebhookOAuthConfig {
  @JsonProperty("token_url")
  private String tokenUrl;

  @JsonProperty("client_id")
  private String clientId;

  private List<String> scopes;

  public WebhookOAuthConfig() {}

  public WebhookOAuthConfig(Builder builder) {
    this.tokenUrl = builder.tokenUrl;
    this.clientId = builder.clientId;
    this.scopes = builder.scopes;
  }

  public String getTokenUrl() {
    return tokenUrl;
  }

  public void setTokenUrl(String tokenUrl) {
    this.tokenUrl = tokenUrl;
  }

  public String getClientId() {
    return clientId;
  }

  public void setClientId(String clientId) {
    this.clientId = clientId;
  }

  public List<String> getScopes() {
    return scopes;
  }

  public void setScopes(List<String> scopes) {
    this.scopes = scopes;
  }

  public static class Builder {
    private String tokenUrl;

    private String clientId;

    private List<String> scopes;

    public Builder tokenUrl(String tokenUrl) {
      this.tokenUrl = tokenUrl;
      return this;
    }

    public Builder clientId(String clientId) {
      this.clientId = clientId;
      return this;
    }

    public Builder scopes(List<String> scopes) {
      this.scopes = scopes;
      return this;
    }

    public WebhookOAuthConfig build() {
      return new WebhookOAuthConfig(this);
    }
  }
}
