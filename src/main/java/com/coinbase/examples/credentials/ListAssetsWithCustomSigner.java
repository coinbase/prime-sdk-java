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

package com.coinbase.examples.credentials;

import com.coinbase.core.credentials.Signer;
import com.coinbase.prime.assets.AssetsService;
import com.coinbase.prime.assets.ListAssetsRequest;
import com.coinbase.prime.assets.ListAssetsResponse;
import com.coinbase.prime.client.CoinbasePrimeClient;
import com.coinbase.prime.credentials.CoinbasePrimeCredentials;
import com.coinbase.prime.factory.PrimeServiceFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Same call as {@code ListAssets}, but signs requests with a custom {@link Signer} instead of the
 * SDK's built-in HMAC-SHA256 implementation. In production, {@link Signer#sign} would call out to
 * an HSM/KMS; this example signs locally to keep it runnable end to end.
 *
 * <p>Expects {@code COINBASE_PRIME_CREDENTIALS} to be JSON containing only {@code accessKey} and
 * {@code passphrase} (no {@code signingKey} needed), plus {@code COINBASE_PRIME_ENTITY_ID}.
 */
public class ListAssetsWithCustomSigner {
  public static void main(String[] args) {
    try {
      Signer hsmSigner = new LocalHmacStandInForHsm(System.getenv("SIGNING_KEY"));

      CoinbasePrimeCredentials credentials =
          new CoinbasePrimeCredentials(System.getenv("COINBASE_PRIME_CREDENTIALS"), hsmSigner);
      CoinbasePrimeClient client = new CoinbasePrimeClient(credentials);
      String entityId = System.getenv("COINBASE_PRIME_ENTITY_ID");

      AssetsService service = PrimeServiceFactory.createAssetsService(client);
      ListAssetsResponse response =
          service.listAssets(new ListAssetsRequest.Builder().entityId(entityId).build());

      System.out.println(
          new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Stands in for an HSM/KMS client. Receives the exact message bytes the SDK would otherwise
   * hash itself ({@code timestamp + method + path + body}), returns raw signature bytes — no
   * string encoding, no Base64. The SDK Base64-encodes the result before attaching it as the
   * {@code X-CB-ACCESS-SIGNATURE} header.
   */
  private static class LocalHmacStandInForHsm implements Signer {
    private final byte[] keyBytes;

    LocalHmacStandInForHsm(String signingKey) {
      this.keyBytes = signingKey.getBytes(java.nio.charset.StandardCharsets.UTF_8);
    }

    @Override
    public byte[] sign(byte[] message) {
      try {
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(keyBytes, "HmacSHA256"));
        return mac.doFinal(message);
      } catch (Exception e) {
        throw new RuntimeException("HSM sign call failed", e);
      }
    }
  }
}
