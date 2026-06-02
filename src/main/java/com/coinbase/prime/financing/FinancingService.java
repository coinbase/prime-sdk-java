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

package com.coinbase.prime.financing;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.errors.CoinbasePrimeException;

public interface FinancingService {
    ListInterestAccrualsResponse listInterestAccruals(ListInterestAccrualsRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * Get Exchange Cross Margin Overview
     */
GetCrossMarginOverviewResponse getCrossMarginOverview(GetCrossMarginOverviewRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Get Entity Locate Availabilities
     */
GetEntityLocateAvailabilitiesResponse getEntityLocateAvailabilities(GetEntityLocateAvailabilitiesRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Get Margin Information
     */
GetMarginInformationResponse getMarginInformation(GetMarginInformationRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    ListMarginCallSummariesResponse listMarginCallSummaries(ListMarginCallSummariesRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    ListTradeFinanceObligationsResponse listTradeFinanceObligations(ListTradeFinanceObligationsRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    GetTradeFinanceTieredPricingFeesResponse getTradeFinanceTieredPricingFees(GetTradeFinanceTieredPricingFeesRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    ListFinancingEligibleAssetsResponse listFinancingEligibleAssets() throws CoinbaseClientException, CoinbasePrimeException;
    ListInterestAccrualsForPortfolioResponse listInterestAccrualsForPortfolio(ListInterestAccrualsForPortfolioRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * Get Portfolio Buying Power
     */
GetPortfolioBuyingPowerResponse getPortfolioBuyingPower(GetPortfolioBuyingPowerRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    GetPortfolioCreditInformationResponse getPortfolioCreditInformation(GetPortfolioCreditInformationRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    ListExistingLocatesResponse listExistingLocates(ListExistingLocatesRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * Create New Locates
     */
CreateNewLocatesResponse createNewLocates(CreateNewLocatesRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    ListMarginConversionsResponse listMarginConversions(ListMarginConversionsRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * Get Portfolio Withdrawal Power
     */
GetPortfolioWithdrawalPowerResponse getPortfolioWithdrawalPower(GetPortfolioWithdrawalPowerRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Sets FCM funding configuration for the entity and submits the desired configuration to Prime API for approval.
     */
    UpdateFundingSettingsResponse updateFundingSettings(UpdateFundingSettingsRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Gets the current Cross Margin (XM) risk parameters for an entity.
     */
    GetCrossMarginRiskParametersResponse getCrossMarginRiskParameters(GetCrossMarginRiskParametersRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Returns real time risk data from the cross margin model.
     */
    GetCrossMarginPrimeOverviewResponse getCrossMarginPrimeOverview(GetCrossMarginPrimeOverviewRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Retrieves market data including volatility and average daily volume for an entity.
     */
    GetMarketDataResponse getMarketData(GetMarketDataRequest request) throws CoinbaseClientException, CoinbasePrimeException;
}
