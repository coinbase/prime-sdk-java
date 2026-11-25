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

import com.coinbase.core.common.HttpMethod;
import com.coinbase.core.service.CoinbaseServiceImpl;
import com.coinbase.prime.client.CoinbasePrimeClient;
import com.coinbase.prime.errors.CoinbasePrimeException;
import com.fasterxml.jackson.core.type.TypeReference;
import java.util.List;

public class WebhooksServiceImpl extends CoinbaseServiceImpl implements WebhooksService {
  public WebhooksServiceImpl(CoinbasePrimeClient client) {
    super(client);
  }

  @Override
  public GetWebhookEventTypesResponse getWebhookEventTypes() throws CoinbasePrimeException {
    return this.request(
        HttpMethod.GET,
        "/webhooks/event-types",
        null,
        List.of(200),
        new TypeReference<GetWebhookEventTypesResponse>() {});
  }

  @Override
  public GetWebhookOwnerStatsResponse getWebhookOwnerStats(GetWebhookOwnerStatsRequest request)
      throws CoinbasePrimeException {
    return this.request(
        HttpMethod.GET,
        String.format("/portfolios/%s/webhooks/stats", request.getPortfolioId()),
        request,
        List.of(200),
        new TypeReference<GetWebhookOwnerStatsResponse>() {});
  }

  @Override
  public ListWebhookSubscriptionsResponse listWebhookSubscriptions(
      ListWebhookSubscriptionsRequest request) throws CoinbasePrimeException {
    return this.request(
        HttpMethod.GET,
        String.format("/portfolios/%s/webhooks/subscriptions", request.getPortfolioId()),
        request,
        List.of(200),
        new TypeReference<ListWebhookSubscriptionsResponse>() {});
  }

  @Override
  public GetWebhookSubscriptionResponse getWebhookSubscription(
      GetWebhookSubscriptionRequest request) throws CoinbasePrimeException {
    return this.request(
        HttpMethod.GET,
        String.format(
            "/portfolios/%s/webhooks/subscriptions/%s",
            request.getPortfolioId(), request.getSubscriptionId()),
        request,
        List.of(200),
        new TypeReference<GetWebhookSubscriptionResponse>() {});
  }

  @Override
  public GetWebhookDeliveryLogsResponse getWebhookDeliveryLogs(
      GetWebhookDeliveryLogsRequest request) throws CoinbasePrimeException {
    return this.request(
        HttpMethod.GET,
        String.format(
            "/portfolios/%s/webhooks/subscriptions/%s/deliveries",
            request.getPortfolioId(), request.getSubscriptionId()),
        request,
        List.of(200),
        new TypeReference<GetWebhookDeliveryLogsResponse>() {});
  }

  @Override
  public GetWebhookSubscriptionStatsResponse getWebhookSubscriptionStats(
      GetWebhookSubscriptionStatsRequest request) throws CoinbasePrimeException {
    return this.request(
        HttpMethod.GET,
        String.format(
            "/portfolios/%s/webhooks/subscriptions/%s/stats",
            request.getPortfolioId(), request.getSubscriptionId()),
        request,
        List.of(200),
        new TypeReference<GetWebhookSubscriptionStatsResponse>() {});
  }

  @Override
  public GetWebhookDeliveryAttemptsResponse getWebhookDeliveryAttempts(
      GetWebhookDeliveryAttemptsRequest request) throws CoinbasePrimeException {
    return this.request(
        HttpMethod.GET,
        String.format(
            "/portfolios/%s/webhooks/subscriptions/%s/deliveries/%s/attempts",
            request.getPortfolioId(), request.getSubscriptionId(), request.getEventId()),
        request,
        List.of(200),
        new TypeReference<GetWebhookDeliveryAttemptsResponse>() {});
  }

  @Override
  public CreateWebhookSubscriptionResponse createWebhookSubscription(
      CreateWebhookSubscriptionRequest request) throws CoinbasePrimeException {
    return this.request(
        HttpMethod.POST,
        String.format("/portfolios/%s/webhooks/subscriptions", request.getPortfolioId()),
        request,
        List.of(200),
        new TypeReference<CreateWebhookSubscriptionResponse>() {});
  }

  @Override
  public ReplayWebhookEventResponse replayWebhookEvent(ReplayWebhookEventRequest request)
      throws CoinbasePrimeException {
    return this.request(
        HttpMethod.POST,
        String.format(
            "/portfolios/%s/webhooks/events/%s/replay",
            request.getPortfolioId(), request.getEventId()),
        request,
        List.of(200),
        new TypeReference<ReplayWebhookEventResponse>() {});
  }

  @Override
  public SendWebhookSimulationResponse sendWebhookSimulation(SendWebhookSimulationRequest request)
      throws CoinbasePrimeException {
    return this.request(
        HttpMethod.POST,
        String.format(
            "/portfolios/%s/webhooks/subscriptions/%s/simulate",
            request.getPortfolioId(), request.getSubscriptionId()),
        request,
        List.of(200),
        new TypeReference<SendWebhookSimulationResponse>() {});
  }

  @Override
  public UpdateWebhookSubscriptionResponse updateWebhookSubscription(
      UpdateWebhookSubscriptionRequest request) throws CoinbasePrimeException {
    return this.request(
        HttpMethod.PATCH,
        String.format(
            "/portfolios/%s/webhooks/subscriptions/%s",
            request.getPortfolioId(), request.getSubscriptionId()),
        request,
        List.of(200),
        new TypeReference<UpdateWebhookSubscriptionResponse>() {});
  }

  @Override
  public DeleteWebhookSubscriptionResponse deleteWebhookSubscription(
      DeleteWebhookSubscriptionRequest request) throws CoinbasePrimeException {
    return this.request(
        HttpMethod.DELETE,
        String.format(
            "/portfolios/%s/webhooks/subscriptions/%s",
            request.getPortfolioId(), request.getSubscriptionId()),
        request,
        List.of(200),
        new TypeReference<DeleteWebhookSubscriptionResponse>() {});
  }

  @Override
  public RotateWebhookHmacSecretResponse rotateWebhookHmacSecret(
      RotateWebhookHmacSecretRequest request) throws CoinbasePrimeException {
    return this.request(
        HttpMethod.POST,
        String.format(
            "/portfolios/%s/webhooks/subscriptions/%s/rotate-hmac-secret",
            request.getPortfolioId(), request.getSubscriptionId()),
        request,
        List.of(200),
        new TypeReference<RotateWebhookHmacSecretResponse>() {});
  }
}
