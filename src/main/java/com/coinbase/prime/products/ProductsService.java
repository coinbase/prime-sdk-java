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

package com.coinbase.prime.products;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.errors.CoinbasePrimeException;

public interface ProductsService {
  /**
   * List Portfolio Products.
   *
   * <p>List tradable products for a given portfolio
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListPortfolioProductsResponse listPortfolioProducts(ListPortfolioProductsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Public Product Candles (Beta).
   *
   * <p>Get rates for a single product by product ID, grouped in buckets. This feature is in beta
   * please reach out to your Coinbase Prime account manager for more information
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetCandlesResponse getCandles(GetCandlesRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;
}
