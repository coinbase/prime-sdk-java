/*
 * Copyright 2025-present Coinbase Global, Inc.
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

package com.coinbase.prime.futures;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.errors.CoinbasePrimeException;

public interface FuturesService {

  /**
   * Set Auto Sweep.
   *
   * <p>Set auto sweep for a given entity
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  SetAutoSweepResponse setAutoSweep(SetAutoSweepRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Entity FCM Balance.
   *
   * <p>Retrieve fcm balance for a given entity
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetEntityFcmBalanceResponse getEntityFcmBalance(GetEntityFcmBalanceRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get FCM Margin Call Details.
   *
   * <p>Retrieve the margin call details for a given entity
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetFcmMarginCallDetailsResponse getFcmMarginCallDetails(GetFcmMarginCallDetailsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Entity Positions.
   *
   * <p>Retrieve all active fcm positions for a given entity
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetPositionsResponse getPositions(GetPositionsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get FCM Risk Limits.
   *
   * <p>Retrieve the risk limits for a given entity
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetFcmRiskLimitsResponse getFcmRiskLimits(GetFcmRiskLimitsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get FCM Settings.
   *
   * <p>Get settings related to FCM
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetFcmSettingsResponse getFcmSettings(GetFcmSettingsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Set FCM Settings.
   *
   * <p>Update settings related to FCM
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  SetFcmSettingsResponse setFcmSettings(SetFcmSettingsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * List Entity Futures Sweeps.
   *
   * <p>Retrieve fcm sweeps in open status, including pending and processing sweeps
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListEntityFuturesSweepsResponse listEntityFuturesSweeps(ListEntityFuturesSweepsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Schedule Entity Futures Sweep.
   *
   * <p>Schedule a sweep for a given entity from FCM wallet to USD Spot wallet. Only one pending
   * sweep is allowed at a time per entity
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ScheduleEntityFuturesSweepResponse scheduleEntityFuturesSweep(
      ScheduleEntityFuturesSweepRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Cancel Entity Futures Sweep.
   *
   * <p>Cancel the pending sweep for a given entity. A user will only be able to have one pending
   * sweep at a time. If the sweep is not found, a 404 will be returned
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  CancelEntityFuturesSweepResponse cancelEntityFuturesSweep(CancelEntityFuturesSweepRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get FCM Equity.
   *
   * <p>Retrieve the equity data for a given entity
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetFcmEquityResponse getFcmEquity(GetFcmEquityRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;
}
