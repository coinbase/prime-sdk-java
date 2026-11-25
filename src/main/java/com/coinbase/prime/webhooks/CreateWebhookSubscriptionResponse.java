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

import com.coinbase.prime.model.WebhookSubscription;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Create Webhook Subscription */
public class CreateWebhookSubscriptionResponse {
  @JsonProperty("subscription")
  private WebhookSubscription subscription;

  /**
   * hmac_secret is the plaintext HMAC signing secret. Returned exactly once on creation; store it
   * securely — it is never surfaced again.
   */
  @JsonProperty("hmac_secret")
  private String hmacSecret;

  public CreateWebhookSubscriptionResponse() {}

  public WebhookSubscription getSubscription() {
    return subscription;
  }

  public void setSubscription(WebhookSubscription subscription) {
    this.subscription = subscription;
  }

  public String getHmacSecret() {
    return hmacSecret;
  }

  public void setHmacSecret(String hmacSecret) {
    this.hmacSecret = hmacSecret;
  }
}
