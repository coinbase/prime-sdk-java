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
import com.coinbase.prime.advancedtransfer.CreateAdvancedTransferRequest;
import com.coinbase.prime.advancedtransfer.CreateAdvancedTransferResponse;
import com.coinbase.prime.client.CoinbasePrimeClient;
import com.coinbase.prime.credentials.CoinbasePrimeCredentials;
import com.coinbase.prime.factory.PrimeServiceFactory;
import com.coinbase.prime.model.AdvancedTransfer;
import com.coinbase.prime.model.BlindMatchMetadata;
import com.coinbase.prime.model.FundMovement;
import com.coinbase.prime.model.TransferLocation;
import com.coinbase.prime.model.enums.AdvancedTransferType;
import com.coinbase.prime.model.enums.TransferLocationType;
import com.coinbase.prime.utils.Utils;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

public class CreateAdvancedTransfer {
  public static void main(String[] args) {
    try {
      if (args.length < 2) {
        System.err.println(
            "Usage: CreateAdvancedTransfer <source_counterparty_id> <target_counterparty_id> [reference_id]");
        System.err.println("Example: CreateAdvancedTransfer CBNHP73AV3 CBPSKN6P2F SE2611240000001");
        System.exit(1);
      }

      CoinbasePrimeCredentials credentials =
          new CoinbasePrimeCredentials(System.getenv("COINBASE_PRIME_CREDENTIALS"));
      CoinbasePrimeClient client = new CoinbasePrimeClient(credentials);
      String portfolioId = System.getenv("COINBASE_PRIME_PORTFOLIO_ID");

      String sourceCounterpartyId = args[0];
      String targetCounterpartyId = args[1];
      String today = LocalDate.now(ZoneOffset.UTC).format(DateTimeFormatter.BASIC_ISO_DATE);
      String referenceId = args.length > 2 ? args[2] : "SE" + today.substring(2) + "0000001";

      System.out.println(
          "Using IDs: Portfolio ID: "
              + portfolioId
              + ", Source: "
              + sourceCounterpartyId
              + ", Target: "
              + targetCounterpartyId
              + ", Reference ID: "
              + referenceId);

      FundMovement btcLeg =
          new FundMovement.Builder()
              .source(
                  new TransferLocation.Builder()
                      .type(TransferLocationType.COUNTERPARTY_ID)
                      .value(sourceCounterpartyId)
                      .build())
              .target(
                  new TransferLocation.Builder()
                      .type(TransferLocationType.COUNTERPARTY_ID)
                      .value(targetCounterpartyId)
                      .build())
              .currency("BTC")
              .amount("0.0002")
              .build();

      FundMovement usdcLeg =
          new FundMovement.Builder()
              .source(
                  new TransferLocation.Builder()
                      .type(TransferLocationType.COUNTERPARTY_ID)
                      .value(targetCounterpartyId)
                      .build())
              .target(
                  new TransferLocation.Builder()
                      .type(TransferLocationType.COUNTERPARTY_ID)
                      .value(sourceCounterpartyId)
                      .build())
              .currency("USDC")
              .amount("18.56")
              .build();

      BlindMatchMetadata metadata =
          new BlindMatchMetadata.Builder()
              .referenceId(referenceId)
              .settlementDate(today)
              .tradeDate(today)
              .settlementTime("1000")
              .build();

      AdvancedTransfer transfer =
          new AdvancedTransfer.Builder()
              .type(AdvancedTransferType.ADVANCED_TRANSFER_TYPE_BLIND_MATCH)
              .fundMovements(List.of(btcLeg, usdcLeg))
              .blindMatchMetadata(metadata)
              .build();

      AdvancedTransferService service = PrimeServiceFactory.createAdvancedTransferService(client);
      CreateAdvancedTransferResponse response =
          service.createAdvancedTransfer(
              new CreateAdvancedTransferRequest.Builder()
                  .portfolioId(portfolioId)
                  .advancedTransfer(transfer)
                  .idempotencyKey(UUID.randomUUID().toString())
                  .build());

      System.out.println(
          Utils.getObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
