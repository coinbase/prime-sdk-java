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

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.errors.CoinbasePrimeException;

public interface WebhooksService {
  /**
   * Get Webhook Event Types.
   *
   * <p>Returns the registered webhook event types available to subscribe to
   *
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetWebhookEventTypesResponse getWebhookEventTypes()
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Webhook Owner Stats.
   *
   * <p>Returns a dashboard-level rollup of delivery statistics across all subscriptions for the
   * portfolio in the URL path
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetWebhookOwnerStatsResponse getWebhookOwnerStats(GetWebhookOwnerStatsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * List Webhook Subscriptions.
   *
   * <p>Returns cursor-paginated webhook subscriptions for the portfolio in the URL path
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListWebhookSubscriptionsResponse listWebhookSubscriptions(ListWebhookSubscriptionsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Webhook Subscription.
   *
   * <p>Returns a single webhook subscription by ID. Auth secrets are never returned
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetWebhookSubscriptionResponse getWebhookSubscription(GetWebhookSubscriptionRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Webhook Delivery Logs.
   *
   * <p>Returns cursor-paginated delivery logs for a single subscription. Delivery history is
   * retained for up to 30 days from each attempt's `attempted_at` timestamp; older entries may be
   * unavailable
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetWebhookDeliveryLogsResponse getWebhookDeliveryLogs(GetWebhookDeliveryLogsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Webhook Subscription Stats.
   *
   * <p>Returns pre-aggregated delivery statistics for a single subscription over a fixed time
   * window
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetWebhookSubscriptionStatsResponse getWebhookSubscriptionStats(
      GetWebhookSubscriptionStatsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Webhook Delivery Attempts.
   *
   * <p>Returns all HTTP POST attempts for a specific (event_id, subscription_id) pair. Attempt
   * history is retained for up to 30 days from each attempt's `attempted_at` timestamp; older
   * records may be unavailable
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetWebhookDeliveryAttemptsResponse getWebhookDeliveryAttempts(
      GetWebhookDeliveryAttemptsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Create Webhook Subscription.
   *
   * <p>Creates a new webhook subscription for the portfolio in the URL path. Returns the HMAC
   * signing secret exactly once; store it securely
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  CreateWebhookSubscriptionResponse createWebhookSubscription(
      CreateWebhookSubscriptionRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Replay Webhook Event.
   *
   * <p>Replays a previously published event for the portfolio in the URL path. When subscription_id
   * is set the event is re-delivered to that single subscription; otherwise it is re-delivered to
   * every matching subscription owned by the path owner
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ReplayWebhookEventResponse replayWebhookEvent(ReplayWebhookEventRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Send Webhook Simulation.
   *
   * <p>Fires a synthetic simulation event to the subscription's endpoint for integration testing
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  SendWebhookSimulationResponse sendWebhookSimulation(SendWebhookSimulationRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Update Webhook Subscription.
   *
   * <p>Applies a partial patch to a webhook subscription using FieldMask semantics
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  UpdateWebhookSubscriptionResponse updateWebhookSubscription(
      UpdateWebhookSubscriptionRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Delete Webhook Subscription.
   *
   * <p>Soft-deletes a webhook subscription. The subscription stops matching events immediately but
   * audit history is retained
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  DeleteWebhookSubscriptionResponse deleteWebhookSubscription(
      DeleteWebhookSubscriptionRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Rotate Webhook HMAC Secret.
   *
   * <p>Issues a new HMAC signing secret for the subscription and returns the plaintext exactly
   * once. By default the previous secret remains valid for a server-configured grace window; set
   * revoke_previous_immediately to invalidate it at once
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  RotateWebhookHmacSecretResponse rotateWebhookHmacSecret(RotateWebhookHmacSecretRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;
}
