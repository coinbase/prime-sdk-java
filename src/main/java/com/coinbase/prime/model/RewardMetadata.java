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

import com.coinbase.prime.model.enums.RewardSubtype;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RewardMetadata {
  /**
   * - REWARD_SUBTYPE_UNKNOWN: An unknown reward subtype, reward subtype may not be supported in the
   * API response yet - MEV_REWARD: A maximal extractable value reward i.e. sol mev rewards -
   * INFLATION_REWARD: An inflationary reward i.e. solana inflationary rewards - BLOCK_REWARD: A
   * block reward i.e. solana block rewards - VALIDATOR_REWARD: A validator reward i.e. ethereum
   * validator (consensus layer) rewards - TRANSACTION_REWARD: A transaction reward i.e. ethereum
   * transaction (execution layer) rewards - STAKING_FEE_REBATE_REWARD: A staking fee rebate reward
   * i.e. coinbase pays rebates for staking fees to eligible delegators - BUIDL_DIVIDEND: A BUIDL
   * dividend reward i.e. dividends from BUIDL fund holdings - CUSTOM_STABLECOIN_REWARD: A custom
   * stablecoin reward i.e. USDC reward payouts
   */
  private RewardSubtype subtype;

  /** Details for a custom stablecoin reward payout transaction */
  @JsonProperty("custom_stablecoin_reward_details")
  private CustomStablecoinRewardDetails customStablecoinRewardDetails;

  public RewardMetadata() {}

  public RewardMetadata(Builder builder) {
    this.subtype = builder.subtype;
    this.customStablecoinRewardDetails = builder.customStablecoinRewardDetails;
  }

  public RewardSubtype getSubtype() {
    return subtype;
  }

  public void setSubtype(RewardSubtype subtype) {
    this.subtype = subtype;
  }

  public CustomStablecoinRewardDetails getCustomStablecoinRewardDetails() {
    return customStablecoinRewardDetails;
  }

  public void setCustomStablecoinRewardDetails(
      CustomStablecoinRewardDetails customStablecoinRewardDetails) {
    this.customStablecoinRewardDetails = customStablecoinRewardDetails;
  }

  public static class Builder {
    private RewardSubtype subtype;

    private CustomStablecoinRewardDetails customStablecoinRewardDetails;

    public Builder subtype(RewardSubtype subtype) {
      this.subtype = subtype;
      return this;
    }

    public Builder customStablecoinRewardDetails(
        CustomStablecoinRewardDetails customStablecoinRewardDetails) {
      this.customStablecoinRewardDetails = customStablecoinRewardDetails;
      return this;
    }

    public RewardMetadata build() {
      return new RewardMetadata(this);
    }
  }
}
