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

package com.coinbase.prime.wallets;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.errors.CoinbasePrimeException;

public interface WalletsService {
  /**
   * List Portfolio Wallets.
   *
   * <p>List all wallets associated with a given portfolio
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListWalletsResponse listWallets(ListWalletsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Create Wallet.
   *
   * <p>Create a wallet. Note: The first ONCHAIN wallet for each network family must be created
   * through the Prime UI
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  CreateWalletResponse createWallet(CreateWalletRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Wallet by Wallet ID.
   *
   * <p>Retrieve a specific wallet by Wallet ID
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetWalletResponse getWallet(GetWalletRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * List Wallet Addresses.
   *
   * <p>Returns all deposit addresses associated with a wallet
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListWalletAddressesResponse listWalletAddresses(ListWalletAddressesRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Create Wallet Deposit Address.
   *
   * <p>Creates a new deposit address for a wallet. Only applicable to wallets that support multiple
   * deposit addresses on a given network
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  CreateWalletDepositAddressResponse createWalletDepositAddress(
      CreateWalletDepositAddressRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Wallet Deposit Instructions.
   *
   * <p>Retrieve a specific wallet's deposit instructions
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetWalletDepositInstructionsResponse getWalletDepositInstructions(
      GetWalletDepositInstructionsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;
}
