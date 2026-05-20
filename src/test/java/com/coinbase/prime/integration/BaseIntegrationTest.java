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

package com.coinbase.prime.integration;

import com.coinbase.prime.client.CoinbasePrimeClient;
import com.coinbase.prime.credentials.CoinbasePrimeCredentials;
import org.junit.jupiter.api.BeforeEach;

import static org.junit.jupiter.api.Assumptions.assumeTrue;

/**
 * Base class for live integration tests. Tests are skipped automatically when
 * COINBASE_PRIME_CREDENTIALS is not set in the environment.
 *
 * <p>Required env vars:
 * <ul>
 *   <li>COINBASE_PRIME_CREDENTIALS - JSON with accessKey, passphrase, signingKey</li>
 *   <li>COINBASE_PRIME_PORTFOLIO_ID - portfolio UUID used in portfolio-scoped calls</li>
 *   <li>COINBASE_PRIME_ENTITY_ID - entity UUID used in entity-scoped calls</li>
 * </ul>
 *
 * <p>Optional per-feature overrides (used when a subclass declares {@link #featureScope()}):
 * <ul>
 *   <li>COINBASE_PRIME_FUTURES_CREDENTIALS / _PORTFOLIO_ID / _ENTITY_ID</li>
 *   <li>COINBASE_PRIME_FINANCING_CREDENTIALS / _PORTFOLIO_ID / _ENTITY_ID</li>
 * </ul>
 * Any field not set falls back to the default COINBASE_PRIME_* variable.
 */
public abstract class BaseIntegrationTest {

    protected CoinbasePrimeClient client;
    protected String portfolioId;
    protected String entityId;

    /**
     * When non-null, integration config is resolved from COINBASE_PRIME_{scope}_* env vars
     * with fallback to the default COINBASE_PRIME_* vars.
     */
    protected String featureScope() {
        return null;
    }

    @BeforeEach
    public void setUpBase() throws Exception {
        String scope = featureScope();
        String credentialsJson = resolve(scope, "CREDENTIALS");
        portfolioId = resolve(scope, "PORTFOLIO_ID");
        entityId = resolve(scope, "ENTITY_ID");

        assumeTrue(credentialsJson != null && !credentialsJson.isEmpty(),
                scope != null
                        ? "Skipping integration test: COINBASE_PRIME_" + scope
                                + "_CREDENTIALS (or fallback COINBASE_PRIME_CREDENTIALS) not set"
                        : "Skipping integration test: COINBASE_PRIME_CREDENTIALS not set");

        CoinbasePrimeCredentials credentials = new CoinbasePrimeCredentials(credentialsJson);
        client = new CoinbasePrimeClient(credentials);
    }

    private static String resolve(String scope, String suffix) {
        if (scope != null) {
            String scoped = System.getenv("COINBASE_PRIME_" + scope + "_" + suffix);
            if (scoped != null && !scoped.isEmpty()) {
                return scoped;
            }
        }
        return System.getenv("COINBASE_PRIME_" + suffix);
    }
}
