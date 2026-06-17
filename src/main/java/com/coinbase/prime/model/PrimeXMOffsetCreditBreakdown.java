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

public class PrimeXMOffsetCreditBreakdown {
    /**
     * Basis offset credit component.
     */
    @JsonProperty("basis_credit")
    private String basisCredit;

    /**
     * Long/short tier-pair offset credit.
     */
    @JsonProperty("long_short_credit")
    private String longShortCredit;

    /**
     * Long/long tier-pair offset credit.
     */
    @JsonProperty("long_long_credit")
    private String longLongCredit;

    /**
     * Short/short tier-pair offset credit.
     */
    @JsonProperty("short_short_credit")
    private String shortShortCredit;

    /**
     * Same-tier offset credit.
     */
    @JsonProperty("same_tier_credit")
    private String sameTierCredit;

    /**
     * Total offset credit.
     */
    @JsonProperty("total_credit")
    private String totalCredit;

    public PrimeXMOffsetCreditBreakdown() {
    }

    public PrimeXMOffsetCreditBreakdown(Builder builder) {
        this.basisCredit = builder.basisCredit;
        this.longShortCredit = builder.longShortCredit;
        this.longLongCredit = builder.longLongCredit;
        this.shortShortCredit = builder.shortShortCredit;
        this.sameTierCredit = builder.sameTierCredit;
        this.totalCredit = builder.totalCredit;
    }
    public String getBasisCredit() {
        return basisCredit;
    }

    public void setBasisCredit(String basisCredit) {
        this.basisCredit = basisCredit;
    }
    public String getLongShortCredit() {
        return longShortCredit;
    }

    public void setLongShortCredit(String longShortCredit) {
        this.longShortCredit = longShortCredit;
    }
    public String getLongLongCredit() {
        return longLongCredit;
    }

    public void setLongLongCredit(String longLongCredit) {
        this.longLongCredit = longLongCredit;
    }
    public String getShortShortCredit() {
        return shortShortCredit;
    }

    public void setShortShortCredit(String shortShortCredit) {
        this.shortShortCredit = shortShortCredit;
    }
    public String getSameTierCredit() {
        return sameTierCredit;
    }

    public void setSameTierCredit(String sameTierCredit) {
        this.sameTierCredit = sameTierCredit;
    }
    public String getTotalCredit() {
        return totalCredit;
    }

    public void setTotalCredit(String totalCredit) {
        this.totalCredit = totalCredit;
    }
    public static class Builder {
        private String basisCredit;

        private String longShortCredit;

        private String longLongCredit;

        private String shortShortCredit;

        private String sameTierCredit;

        private String totalCredit;

        public Builder basisCredit(String basisCredit) {
            this.basisCredit = basisCredit;
            return this;
        }

        public Builder longShortCredit(String longShortCredit) {
            this.longShortCredit = longShortCredit;
            return this;
        }

        public Builder longLongCredit(String longLongCredit) {
            this.longLongCredit = longLongCredit;
            return this;
        }

        public Builder shortShortCredit(String shortShortCredit) {
            this.shortShortCredit = shortShortCredit;
            return this;
        }

        public Builder sameTierCredit(String sameTierCredit) {
            this.sameTierCredit = sameTierCredit;
            return this;
        }

        public Builder totalCredit(String totalCredit) {
            this.totalCredit = totalCredit;
            return this;
        }

        public PrimeXMOffsetCreditBreakdown build() {
            return new PrimeXMOffsetCreditBreakdown(this);
        }
    }
}

