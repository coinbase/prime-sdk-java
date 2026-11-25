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
import com.coinbase.prime.advancedtransfer.CancelAdvancedTransferRequest;
import com.coinbase.prime.advancedtransfer.CancelAdvancedTransferResponse;
import com.coinbase.prime.client.CoinbasePrimeClient;
import com.coinbase.prime.credentials.CoinbasePrimeCredentials;
import com.coinbase.prime.factory.PrimeServiceFactory;
import com.coinbase.prime.utils.Utils;

public class CancelAdvancedTransfer {
  public static void main(String[] args) {
    try {
      if (args.length < 1) {
        System.err.println("Usage: CancelAdvancedTransfer <advanced_transfer_id>");
        System.exit(1);
      }

      CoinbasePrimeCredentials credentials =
          new CoinbasePrimeCredentials(System.getenv("COINBASE_PRIME_CREDENTIALS"));
      CoinbasePrimeClient client = new CoinbasePrimeClient(credentials);
      String portfolioId = System.getenv("COINBASE_PRIME_PORTFOLIO_ID");
      String advancedTransferId = args[0];

      System.out.println(
          "Using IDs: Portfolio ID: "
              + portfolioId
              + ", Advanced Transfer ID: "
              + advancedTransferId);

      AdvancedTransferService service = PrimeServiceFactory.createAdvancedTransferService(client);
      CancelAdvancedTransferResponse response =
          service.cancelAdvancedTransfer(
              new CancelAdvancedTransferRequest.Builder()
                  .portfolioId(portfolioId)
                  .advancedTransferId(advancedTransferId)
                  .build());

      System.out.println(
          Utils.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
