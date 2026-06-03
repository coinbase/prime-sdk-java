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

package com.coinbase.prime.utils;

public class Constants {
  public static final String CB_ACCESS_KEY_HEADER = "X-CB-ACCESS-KEY";
  public static final String CB_ACCESS_PHRASE_HEADER = "X-CB-ACCESS-PASSPHRASE";
  public static final String CB_ACCESS_SIGNATURE_HEADER = "X-CB-ACCESS-SIGNATURE";
  public static final String CB_ACCESS_TIMESTAMP_HEADER = "X-CB-ACCESS-TIMESTAMP";
  public static final String CB_USER_AGENT_HEADER = "User-Agent";
  public static final String CB_PRIME_BASE_URL = "https://api.prime.coinbase.com/v1";
  public static final String SDK_VERSION = "1.9.0";

  /**
   * Replaces a trailing {@code /vN} segment with {@code /}{@code version}. Used when an endpoint is
   * served from a different API version than the default client (e.g. {@code v2}).
   */
  public static String versionedBaseUrl(String baseUrl, String version) {
    return baseUrl.replaceFirst("/v\\d+$", "") + "/" + version;
  }
}
