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

package com.coinbase.prime.transactions;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.errors.CoinbasePrimeException;

public interface TransactionsService {
    /**
     * List Portfolio Transactions.
     * <p>
     * List transactions for a given portfolio
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    ListPortfolioTransactionsResponse listPortfolioTransactions(ListPortfolioTransactionsRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Get Transaction by Transaction ID.
     * <p>
     * Retrieve a specific transaction by its transaction ID
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    GetTransactionResponse getTransaction(GetTransactionRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Create Conversion.
     * <p>
     * Perform a conversion between 2 assets
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    CreateConversionResponse createConversion(CreateConversionRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Create Onchain Transaction.
     * <p>
     * Create an onchain transaction
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    CreateOnchainTransactionResponse createOnchainTransaction(CreateOnchainTransactionRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * List Wallet Transactions.
     * <p>
     * Retrieve transactions for a given wallet
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    ListWalletTransactionsResponse listWalletTransactions(ListWalletTransactionsRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Create Transfer.
     * <p>
     * Create a wallet transfer
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    CreateWalletTransferResponse createWalletTransfer(CreateWalletTransferRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Create Withdrawal.
     * <p>
     * Create a withdrawal
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    CreateWalletWithdrawalResponse createWalletWithdrawal(CreateWalletWithdrawalRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Get Transaction Travel Rule Data.
     * <p>
     * (Beta) Get fulfilled travel rule data for a transaction
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    GetTransactionTravelRuleDataResponse getTransactionTravelRuleData(GetTransactionTravelRuleDataRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Submit Deposit Travel Rule Data.
     * <p>
     * Submit travel rule data for an existing deposit transaction
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    SubmitDepositTravelRuleDataResponse submitDepositTravelRuleData(SubmitDepositTravelRuleDataRequest request) throws CoinbaseClientException, CoinbasePrimeException;

}
