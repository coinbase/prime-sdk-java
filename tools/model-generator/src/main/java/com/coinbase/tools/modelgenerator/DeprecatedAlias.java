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

package com.coinbase.tools.modelgenerator;

/**
 * A declarative Java SDK compatibility alias for a canonical operation binding.
 *
 * <p>Aliases preserve a previously public Java name (request type, response type, and service
 * method) as a deprecated forwarding surface once an operation's canonical cross-SDK name
 * diverges from the existing Java name.
 */
public final class DeprecatedAlias {
  private final String sdkMethod;
  private final String message;

  public DeprecatedAlias(String sdkMethod, String message) {
    this.sdkMethod = sdkMethod;
    this.message = message;
  }

  /** The deprecated PascalCase Java SDK method/type basename, for example {@code CreateWalletTransfer}. */
  public String sdkMethod() {
    return sdkMethod;
  }

  /** The {@code @deprecated} Javadoc message pointing consumers to the canonical replacement. */
  public String message() {
    return message;
  }
}
