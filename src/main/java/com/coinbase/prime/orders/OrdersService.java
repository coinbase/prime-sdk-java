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

package com.coinbase.prime.orders;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.errors.CoinbasePrimeException;

public interface OrdersService {

    /**
     * Accept Quote.
     * <p>
     * Accepts the quote received by the quote request and creates an order with the provided
     * quote ID. Always required: portfolio_id, product_id, side, quote_id, client_quote_id
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    AcceptQuoteResponse acceptQuote(AcceptQuoteRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * List Portfolio Fills.
     * <p>
     * Retrieve fills on a given portfolio. This endpoint requires a start_date, and returns a
     * payload with a default limit of 100 if not specified by the user. The maximum allowed
     * limit is 3000
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    ListPortfolioFillsResponse listPortfolioFills(ListPortfolioFillsRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * List Open Orders.
     * <p>
     * List all open orders. <br /><br />**Caution:** The maximum number of orders returned is
     * 5000. If a client has more than 5000 open orders, an error is returned prompting the
     * user to use Websocket API, or FIX API to stream open orders
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    ListOpenOrdersResponse listOpenOrders(ListOpenOrdersRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Create Order.
     * <p>
     * Create an order
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    CreateOrderResponse createOrder(CreateOrderRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * Get Order Preview.
     * <p>
     * Retrieve an order preview
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    GetOrderPreviewResponse getOrderPreview(GetOrderPreviewRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * List Portfolio Orders.
     * <p>
     * List historical orders for a given portfolio. This endpoint returns a payload with a
     * default limit of 100 if not specified by the user. The maximum allowed limit is 3000.
     * <br /><br />**Caution:** Currently, you cannot query open orders with this endpoint: use
     * List Open Orders if you have less than 1000 open orders, otherwise use Websocket API, or
     * FIX API to stream open orders
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    ListPortfolioOrdersResponse listPortfolioOrders(ListPortfolioOrdersRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * Get Order by Order ID.
     * <p>
     * Retrieve an order by order ID
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    GetOrderByOrderIdResponse getOrderByOrderId(GetOrderByOrderIdRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Cancel Order.
     * <p>
     * Cancel an order. (Filled orders cannot be canceled.)
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    CancelOrderResponse cancelOrder(CancelOrderRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * Edit Order (Beta).
     * <p>
     * Edit an open order. This feature is in beta please reach out to your Coinbase Prime
     * account manager for more information
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    EditOrderResponse editOrder(EditOrderRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * List Order Edit History.
     * <p>
     * List edit history for a specific order
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    ListOrderEditHistoryResponse listOrderEditHistory(ListOrderEditHistoryRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * List Order Fills.
     * <p>
     * Retrieve fills on a given order. This endpoint returns a payload with a default limit of
     * 100 if not specified by the user. The maximum allowed limit is 3000
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    ListOrderFillsResponse listOrderFills(ListOrderFillsRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * Create Quote Request.
     * <p>
     * A Quote Request is the start of the RFQ process. Coinbase Prime sends a Quote Request to
     * Liquidity Providers (LPs) on behalf of a customer looking to participate in an RFQ
     * trade. Always required: portfolio_id, product_id, side, client_quote_id, and
     * limit_price. One of either base_quantity or quote_value is always required
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    CreateQuoteResponse createQuote(CreateQuoteRequest request) throws CoinbaseClientException, CoinbasePrimeException;

}
