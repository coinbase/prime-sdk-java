/*
 * Copyright 2026-present Coinbase Global, Inc.
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

package com.coinbase.examples.advancedtransfer;

import com.coinbase.prime.advancedtransfer.AdvancedTransferService;
import com.coinbase.prime.advancedtransfer.ListAdvancedTransfersRequest;
import com.coinbase.prime.advancedtransfer.ListAdvancedTransfersResponse;
import com.coinbase.prime.client.CoinbasePrimeClient;
import com.coinbase.prime.credentials.CoinbasePrimeCredentials;
import com.coinbase.prime.factory.PrimeServiceFactory;
import com.coinbase.prime.model.enums.AdvancedTransferState;
import com.coinbase.prime.model.enums.AdvancedTransferType;
import com.coinbase.prime.utils.Utils;

public class ListAdvancedTransfers {
  public static void main(String[] args) {
    try {
      CoinbasePrimeCredentials credentials =
          new CoinbasePrimeCredentials(System.getenv("COINBASE_PRIME_CREDENTIALS"));
      CoinbasePrimeClient client = new CoinbasePrimeClient(credentials);
      String portfolioId = System.getenv("COINBASE_PRIME_PORTFOLIO_ID");

      System.out.println("Using IDs: Portfolio ID: " + portfolioId);

      ListAdvancedTransfersRequest.Builder builder =
          new ListAdvancedTransfersRequest.Builder().portfolioId(portfolioId);

      if (args.length > 0 && !args[0].isEmpty()) {
        builder.state(AdvancedTransferState.valueOf(args[0].toUpperCase()));
      }
      if (args.length > 1 && !args[1].isEmpty()) {
        builder.type(AdvancedTransferType.valueOf(args[1].toUpperCase()));
      }
      if (args.length > 2 && !args[2].isEmpty()) {
        builder.referenceId(args[2]);
      }

      AdvancedTransferService service = PrimeServiceFactory.createAdvancedTransferService(client);
      ListAdvancedTransfersResponse response = service.listAdvancedTransfers(builder.build());

      System.out.println(
          Utils.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
