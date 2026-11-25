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

package com.coinbase.prime.model.enums;

/**
 * WebhookAuthType is how Prime authenticates to your endpoint, on top of the signatures. Every
 * delivery is signed twice, whatever the auth type: with HMAC-SHA256 in X-Coinbase-Signature, keyed
 * by the subscription's hmac_secret, and with Ed25519 in X-Coinbase-Signature-Ed25519, verifiable
 * with the public key named by X-Coinbase-Signature-Key-Id at /.well-known/jwks.json. Verify either
 * one. - WEBHOOK_AUTH_TYPE_SIGNATURE_ONLY: Signatures only. Used when a subscription is created
 * without auth_type. - WEBHOOK_AUTH_TYPE_OAUTH: Signatures plus an OAuth 2.0 bearer token, fetched
 * with oauth_config, in the Authorization header. - WEBHOOK_AUTH_TYPE_MTLS: Signatures plus a
 * Coinbase client certificate (mutual TLS). - WEBHOOK_AUTH_TYPE_OAUTH_MTLS: Signatures plus both an
 * OAuth 2.0 bearer token and a client certificate.
 */
public enum WebhookAuthType {
  /** Signatures only. Used when a subscription is created without auth_type. */
  WEBHOOK_AUTH_TYPE_SIGNATURE_ONLY,
  /** Signatures plus an OAuth 2.0 bearer token, fetched with oauth_config, in */
  WEBHOOK_AUTH_TYPE_OAUTH,
  /** Signatures plus a Coinbase client certificate (mutual TLS). */
  WEBHOOK_AUTH_TYPE_MTLS,
  /** Signatures plus both an OAuth 2.0 bearer token and a client certificate. */
  WEBHOOK_AUTH_TYPE_OAUTH_MTLS
}
