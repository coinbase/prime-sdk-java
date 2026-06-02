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

package com.coinbase.prime.staking;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.errors.CoinbasePrimeException;

public interface StakingService {
    /**
     * Claim Wallet Staking Rewards (Alpha).
     * <p>
     * Request to claim staking rewards. This feature is in alpha. Please reach out to your
     * Coinbase Prime account manager for more information
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    ClaimRewardsResponse claimRewards(ClaimRewardsRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * Request to stake or delegate a wallet.
     * <p>
     * Creates an execution request to stake or delegate funds to a validator
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    CreateStakeResponse createStake(CreateStakeRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * Request to unstake a wallet.
     * <p>
     * Creates an execution request to unstake delegated or staked funds in a wallet
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    CreateUnstakeResponse createUnstake(CreateUnstakeRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * List Transaction Validators.
     * <p>
     * List ETH 0x02 validators associated with wallet-level stake transactions for a given
     * portfolio. It will not return data for unstake transactions, portfolio stake
     * transactions, transactions which staked different currencies, or which staked to
     * Ethereum 0x01 validators
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    ListTransactionValidatorsResponse listTransactionValidators(ListTransactionValidatorsRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Request to stake currency in a portfolio.
     * <p>
     * Creates an execution request to stake funds across a portfolio. This will stake funds in
     * one or more wallets in the portfolio, with a total bondable balance up to the requested
     * stake amount
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    PortfolioStakingInitiateResponse portfolioStakingInitiate(PortfolioStakingInitiateRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Request to unstake currency across a portfolio.
     * <p>
     * Creates an execution request to unstake funds across a portfolio. This will unstake
     * funds in one or more wallets in the portfolio, with a total bonded balance up to the
     * requested unstake amount
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    PortfolioStakingUnstakeResponse portfolioStakingUnstake(PortfolioStakingUnstakeRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Get Staking Status.
     * <p>
     * Get staking status for a wallet. Returns estimated completion times for active staking
     * requests
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    GetStakingStatusResponse getStakingStatus(GetStakingStatusRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Get Unstaking Status.
     * <p>
     * Get unstaking estimates for a wallet. Returns estimated completion times for active
     * unstaking requests
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    GetUnstakingStatusResponse getUnstakingStatus(GetUnstakingStatusRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Preview Unstake.
     * <p>
     * Previews an unstaking request with the given amount and returns the estimated amount
     * that would be unstaked. This feature currently only supports ETH
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    PreviewUnstakeResponse previewUnstake(PreviewUnstakeRequest request) throws CoinbaseClientException, CoinbasePrimeException;

}
