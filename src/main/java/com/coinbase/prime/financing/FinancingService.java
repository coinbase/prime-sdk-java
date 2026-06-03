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
  /**
   * List Interest Accruals.
   *
   * <p>Lists interest accruals for an entity between the specified date range given
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListInterestAccrualsResponse listInterestAccruals(ListInterestAccrualsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Exchange Cross Margin Overview.
   *
   * <p>Gets live data for Cross Margin (XM) for a specific XM customer
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetCrossMarginOverviewResponse getCrossMarginOverview(GetCrossMarginOverviewRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Entity Locate Availabilities.
   *
   * <p>Get currencies available to be located with their corresponding amount and rate
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetEntityLocateAvailabilitiesResponse getEntityLocateAvailabilities(
      GetEntityLocateAvailabilitiesRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Margin Information.
   *
   * <p>Gets real-time evaluation of the margin model based on current positions and spot rates
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetMarginInformationResponse getMarginInformation(GetMarginInformationRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * List Margin Call Summaries.
   *
   * <p>Lists the margin call history for a given entity ID
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListMarginCallSummariesResponse listMarginCallSummaries(ListMarginCallSummariesRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * List Trade Finance Obligations.
   *
   * <p>List trade finance obligations for a given entity
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListTradeFinanceObligationsResponse listTradeFinanceObligations(
      ListTradeFinanceObligationsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Trade Finance Tiered Pricing Fees.
   *
   * <p>Get trade finance tiered pricing fees for a given entity at a specific time, default to
   * current time
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetTradeFinanceTieredPricingFeesResponse getTradeFinanceTieredPricingFees(
      GetTradeFinanceTieredPricingFeesRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * List Financing Eligible Assets.
   *
   * <p>Get all assets eligible for Trade Finance with their adjustment factors
   *
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListFinancingEligibleAssetsResponse listFinancingEligibleAssets()
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * List Interest Accruals For Portfolio.
   *
   * <p>Lists interest accruals between the specified date range for a specific portfolio ID
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListInterestAccrualsForPortfolioResponse listInterestAccrualsForPortfolio(
      ListInterestAccrualsForPortfolioRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Portfolio Buying Power.
   *
   * <p>Returns the size of a buy trade that can be performed based on existing holdings and
   * available credit. The result will differ for different assets due to asset specific credit
   * configurations and caps. Note that this result is changing based on asset price fluctuations,
   * so may be rejected when submitted
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetPortfolioBuyingPowerResponse getPortfolioBuyingPower(GetPortfolioBuyingPowerRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Portfolio Credit Information.
   *
   * <p>Retrieve a portfolio's post-trade credit information
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetPortfolioCreditInformationResponse getPortfolioCreditInformation(
      GetPortfolioCreditInformationRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * List Existing Locates.
   *
   * <p>List locates for the portfolio
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListExistingLocatesResponse listExistingLocates(ListExistingLocatesRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Create New Locates.
   *
   * <p>Create a new locate
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  CreateNewLocatesResponse createNewLocates(CreateNewLocatesRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * List Margin Conversions.
   *
   * <p>Lists conversions and short collateral requirement between specified date range. This
   * endpoint is deprecated and will be removed in the future. Use
   * /v1/entities/{entity_id}/margin_summaries instead
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  ListMarginConversionsResponse listMarginConversions(ListMarginConversionsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Portfolio Withdrawal Power.
   *
   * <p>Returns the nominal quantity of a given asset that can be withdrawn based on holdings and
   * current portfolio equity
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetPortfolioWithdrawalPowerResponse getPortfolioWithdrawalPower(
      GetPortfolioWithdrawalPowerRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Update Funding Settings.
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  UpdateFundingSettingsResponse updateFundingSettings(UpdateFundingSettingsRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Cross Margin Risk Parameters (Beta).
   *
   * <p>Gets the current Cross Margin (XM) risk parameters for an entity
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetCrossMarginRiskParametersResponse getCrossMarginRiskParameters(
      GetCrossMarginRiskParametersRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Exchange Cross Margin Overview.
   *
   * <p>Gets live data for Cross Margin (XM) for a specific XM customer
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetCrossMarginPrimeOverviewResponse getCrossMarginPrimeOverview(
      GetCrossMarginPrimeOverviewRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;

  /**
   * Get Market Data (Beta).
   *
   * <p>Retrieves market data including volatility and average daily volume for an entity
   *
   * @param request the request parameters for this operation
   * @return the response payload for this operation
   * @throws CoinbaseClientException if the request fails client-side validation
   * @throws CoinbasePrimeException if the Prime API returns an error response
   */
  GetMarketDataResponse getMarketData(GetMarketDataRequest request)
      throws CoinbaseClientException, CoinbasePrimeException;
}
