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

package com.coinbase.prime.onchainaddressbook;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.errors.CoinbasePrimeException;

public interface OnchainAddressBookService {
    /**
     * Update Onchain Address Book Entry.
     * <p>
     * Updates an entry to the portfolio's onchain address groups
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    UpdateOnchainAddressBookEntryResponse updateOnchainAddressBookEntry(UpdateOnchainAddressBookEntryRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * Create Onchain Address Book Entry.
     * <p>
     * Creates an entry to the portfolio's onchain address groups
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    CreateOnchainAddressBookEntryResponse createOnchainAddressBookEntry(CreateOnchainAddressBookEntryRequest request) throws CoinbaseClientException, CoinbasePrimeException;
    /**
     * Delete Onchain Address Group.
     * <p>
     * Deletes an entry in the portfolio's onchain address groups
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    DeleteOnchainAddressGroupResponse deleteOnchainAddressGroup(DeleteOnchainAddressGroupRequest request) throws CoinbaseClientException, CoinbasePrimeException;

    /**
     * List Onchain Address Groups.
     * <p>
     * Lists all onchain address groups for a given portfolio ID
     * </p>
     *
     * @param request the request parameters for this operation
     * @return the response payload for this operation
     * @throws CoinbaseClientException if the request fails client-side validation
     * @throws CoinbasePrimeException if the Prime API returns an error response
     */
    ListOnchainAddressGroupsResponse listOnchainAddressGroups(ListOnchainAddressGroupsRequest request) throws CoinbaseClientException, CoinbasePrimeException;

}
