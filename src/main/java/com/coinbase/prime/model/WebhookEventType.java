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

/** WebhookEventType describes a registered webhook event customers can subscribe to. */
public class WebhookEventType {
  private String name;

  @JsonProperty("domain_object")
  private String domainObject;

  private String description;

  public WebhookEventType() {}

  public WebhookEventType(Builder builder) {
    this.name = builder.name;
    this.domainObject = builder.domainObject;
    this.description = builder.description;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDomainObject() {
    return domainObject;
  }

  public void setDomainObject(String domainObject) {
    this.domainObject = domainObject;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public static class Builder {
    private String name;

    private String domainObject;

    private String description;

    public Builder name(String name) {
      this.name = name;
      return this;
    }

    public Builder domainObject(String domainObject) {
      this.domainObject = domainObject;
      return this;
    }

    public Builder description(String description) {
      this.description = description;
      return this;
    }

    public WebhookEventType build() {
      return new WebhookEventType(this);
    }
  }
}
