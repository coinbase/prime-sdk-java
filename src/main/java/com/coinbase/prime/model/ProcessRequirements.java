/*
 * Copyright 2025-present Coinbase Global, Inc.
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
import com.coinbase.prime.model.enums.TravelRuleStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

/** Represents the status of various process requirements for a transaction */
public class ProcessRequirements {
    /** Travel rule compliance status for a transaction */
    @JsonProperty("travel_rule_status")
    private TravelRuleStatus travelRuleStatus;

    public ProcessRequirements() {
    }

    public ProcessRequirements(Builder builder) {
        this.travelRuleStatus = builder.travelRuleStatus;
    }
    public TravelRuleStatus getTravelRuleStatus() {
        return travelRuleStatus;
    }

    public void setTravelRuleStatus(TravelRuleStatus travelRuleStatus) {
        this.travelRuleStatus = travelRuleStatus;
    }
    public static class Builder {
        private TravelRuleStatus travelRuleStatus;

        public Builder travelRuleStatus(TravelRuleStatus travelRuleStatus) {
            this.travelRuleStatus = travelRuleStatus;
            return this;
        }

        public ProcessRequirements build() {
            return new ProcessRequirements(this);
        }
    }
}

