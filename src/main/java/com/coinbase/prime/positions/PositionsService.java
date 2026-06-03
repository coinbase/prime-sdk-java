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

package com.coinbase.prime.positions;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.errors.CoinbasePrimeException;

public interface PositionsService {

  /**
   * List Aggregate Entity Positions.
   *
   * <p>List paginated aggregate positions for a specific entity
   *
   * @deprecated Prefer {@link #listAggregateEntityPositions(ListAggregateEntityPositionsRequest)} —
   *     same REST route as the current spec.
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  @Deprecated
  ListAggregatePositionsResponse listAggregatePositions(ListAggregatePositionsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * List Aggregate Entity Positions.
   *
   * <p>List paginated aggregate positions for a specific entity
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListAggregateEntityPositionsResponse listAggregateEntityPositions(
      ListAggregateEntityPositionsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * List Entity Positions.
   *
   * <p>List paginated positions for a specific entity
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListEntityPositionsResponse listEntityPositions(ListEntityPositionsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * List Entity Positions.
   *
   * <p>List paginated positions for a specific entity
   *
   * @deprecated Prefer {@link #listEntityPositions(ListEntityPositionsRequest)} — same REST route
   *     as the current spec.
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  @Deprecated
  ListPositionsResponse listPositions(ListPositionsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;
}
