/*
 * Copyright 2024-present Coinbase Global, Inc.
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

package com.coinbase.prime.activities;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.errors.CoinbasePrimeException;

public interface ActivitiesService {
    /**
     * List Activities.
     * <p>
     * List all activities associated with a given portfolio
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    ListPortfolioActivitiesResponse listPortfolioActivities(ListPortfolioActivitiesRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * List Entity Activities.
     * <p>
     * List all activities associated with a given entity
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    ListEntityActivitiesResponse listEntityActivities(ListEntityActivitiesRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Get Activity by Activity ID.
     * <p>
     * Retrieve an activity by its activity ID - this endpoint can retrieve both portfolio and
     * entity activities when passed the appropriate API key
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    GetActivityResponse getActivity(GetActivityRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Get Activity by Activity ID.
     * <p>
     * Retrieve an activity by its activity ID
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    GetPortfolioActivityResponse getPortfolioActivity(GetPortfolioActivityRequest request) throws CoinbaseClientException, CoinbasePrimeException;

}
