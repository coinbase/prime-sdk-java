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
import com.coinbase.prime.model.ConversionFeeTier;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

/**
 * Per-pair conversion fee row: month-to-date net conversion volume and
 * the applicable progressive fee tiers for an organization.
 */
public class ConversionFee {
    /**
     * Source currency ticker.
     */
    @JsonProperty("from_currency")
    private String fromCurrency;

    /**
     * Destination currency ticker.
     */
    @JsonProperty("to_currency")
    private String toCurrency;

    /**
     * Month-to-date net conversion volume denominated in from_currency, aggregated at the owning organization level. Positive &#x3D; net from->to, negative &#x3D; net to->from.
     */
    @JsonProperty("net_conversion_volume_mtd")
    private String netConversionVolumeMtd;

    /**
     * Progressive fee tiers applicable to this organization for this pair. Tiers are ordered from lowest to highest threshold.
     */
    @JsonProperty("fee_tiers")
    private List<ConversionFeeTier> feeTiers;

    public ConversionFee() {
    }

    public ConversionFee(Builder builder) {
        this.fromCurrency = builder.fromCurrency;
        this.toCurrency = builder.toCurrency;
        this.netConversionVolumeMtd = builder.netConversionVolumeMtd;
        this.feeTiers = builder.feeTiers;
    }
    public String getFromCurrency() {
        return fromCurrency;
    }

    public void setFromCurrency(String fromCurrency) {
        this.fromCurrency = fromCurrency;
    }
    public String getToCurrency() {
        return toCurrency;
    }

    public void setToCurrency(String toCurrency) {
        this.toCurrency = toCurrency;
    }
    public String getNetConversionVolumeMtd() {
        return netConversionVolumeMtd;
    }

    public void setNetConversionVolumeMtd(String netConversionVolumeMtd) {
        this.netConversionVolumeMtd = netConversionVolumeMtd;
    }
    public List<ConversionFeeTier> getFeeTiers() {
        return feeTiers;
    }

    public void setFeeTiers(List<ConversionFeeTier> feeTiers) {
        this.feeTiers = feeTiers;
    }
    public static class Builder {
        private String fromCurrency;

        private String toCurrency;

        private String netConversionVolumeMtd;

        private List<ConversionFeeTier> feeTiers;

        public Builder fromCurrency(String fromCurrency) {
            this.fromCurrency = fromCurrency;
            return this;
        }

        public Builder toCurrency(String toCurrency) {
            this.toCurrency = toCurrency;
            return this;
        }

        public Builder netConversionVolumeMtd(String netConversionVolumeMtd) {
            this.netConversionVolumeMtd = netConversionVolumeMtd;
            return this;
        }

        public Builder feeTiers(List<ConversionFeeTier> feeTiers) {
            this.feeTiers = feeTiers;
            return this;
        }

        public ConversionFee build() {
            return new ConversionFee(this);
        }
    }
}

