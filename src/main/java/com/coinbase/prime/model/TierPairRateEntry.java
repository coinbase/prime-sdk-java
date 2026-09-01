/*
 * Copyright 2026-present Coinbase Global, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *  Do not edit the class manually.
 */

package com.coinbase.prime.model;
import com.fasterxml.jackson.annotation.JsonProperty;

/** TierPairRateEntry represents a single (tier_a, tier_b) -> rate entry in an offset credit matrix. */
public class TierPairRateEntry {
    /**
     * First tier in the pair.
     */
    @JsonProperty("tier_a")
    private String tierA;

    /**
     * Second tier in the pair.
     */
    @JsonProperty("tier_b")
    private String tierB;

    /**
     * Credit rate for this tier pair.
     */
    private String rate;

    public TierPairRateEntry() {
    }

    public TierPairRateEntry(Builder builder) {
        this.tierA = builder.tierA;
        this.tierB = builder.tierB;
        this.rate = builder.rate;
    }
    public String getTierA() {
        return tierA;
    }

    public void setTierA(String tierA) {
        this.tierA = tierA;
    }
    public String getTierB() {
        return tierB;
    }

    public void setTierB(String tierB) {
        this.tierB = tierB;
    }
    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }
    public static class Builder {
        private String tierA;

        private String tierB;

        private String rate;

        public Builder tierA(String tierA) {
            this.tierA = tierA;
            return this;
        }

        public Builder tierB(String tierB) {
            this.tierB = tierB;
            return this;
        }

        public Builder rate(String rate) {
            this.rate = rate;
            return this;
        }

        public TierPairRateEntry build() {
            return new TierPairRateEntry(this);
        }
    }
}

