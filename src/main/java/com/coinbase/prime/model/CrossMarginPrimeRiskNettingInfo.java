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
import java.util.List;

/** Groups XM margin requirement components, offset credits, and per-asset rows. */
public class CrossMarginPrimeRiskNettingInfo {
  /**
   * Derivatives Clearing Organization Margin Requirement (DMR) is the margin requirement for all
   * futures positions, derived from the Derivatives Clearing Organization model
   */
  @JsonProperty("dco_margin_requirement")
  private String dcoMarginRequirement;

  /**
   * Portfolio Margin Requirement (PMR) is the margin requirement for all spot positions, derived
   * from the XM model
   */
  @JsonProperty("portfolio_margin_requirement")
  private String portfolioMarginRequirement;

  /**
   * Integrated Portfolio Margin Requirement (IPMR) is the margin requirement for all spot positions
   * + futures positions with underlying assets eligible in Portfolio Margin.
   */
  @JsonProperty("integrated_portfolio_margin_requirement")
  private String integratedPortfolioMarginRequirement;

  /**
   * Ineligible Futures Margin Requirement (IFMR) is the margin requirement for IPMR-ineligible
   * futures contracts
   */
  @JsonProperty("ineligible_futures_margin_requirement")
  private String ineligibleFuturesMarginRequirement;

  @JsonProperty("pmr_breakdown")
  private PrimeXMMarginRequirementBreakdown pmrBreakdown;

  @JsonProperty("ipmr_breakdown")
  private PrimeXMMarginRequirementBreakdown ipmrBreakdown;

  @JsonProperty("portfolio_margin_offset_credit_breakdown")
  private PrimeXMOffsetCreditBreakdown portfolioMarginOffsetCreditBreakdown;

  @JsonProperty("integrated_portfolio_margin_offset_credit_breakdown")
  private PrimeXMOffsetCreditBreakdown integratedPortfolioMarginOffsetCreditBreakdown;

  /** Netted positions used in the model calculation. */
  @JsonProperty("xm_positions")
  private List<CrossMarginPrimeXMPosition> xmPositions;

  public CrossMarginPrimeRiskNettingInfo() {}

  public CrossMarginPrimeRiskNettingInfo(Builder builder) {
    this.dcoMarginRequirement = builder.dcoMarginRequirement;
    this.portfolioMarginRequirement = builder.portfolioMarginRequirement;
    this.integratedPortfolioMarginRequirement = builder.integratedPortfolioMarginRequirement;
    this.ineligibleFuturesMarginRequirement = builder.ineligibleFuturesMarginRequirement;
    this.pmrBreakdown = builder.pmrBreakdown;
    this.ipmrBreakdown = builder.ipmrBreakdown;
    this.portfolioMarginOffsetCreditBreakdown = builder.portfolioMarginOffsetCreditBreakdown;
    this.integratedPortfolioMarginOffsetCreditBreakdown =
        builder.integratedPortfolioMarginOffsetCreditBreakdown;
    this.xmPositions = builder.xmPositions;
  }

  public String getDcoMarginRequirement() {
    return dcoMarginRequirement;
  }

  public void setDcoMarginRequirement(String dcoMarginRequirement) {
    this.dcoMarginRequirement = dcoMarginRequirement;
  }

  public String getPortfolioMarginRequirement() {
    return portfolioMarginRequirement;
  }

  public void setPortfolioMarginRequirement(String portfolioMarginRequirement) {
    this.portfolioMarginRequirement = portfolioMarginRequirement;
  }

  public String getIntegratedPortfolioMarginRequirement() {
    return integratedPortfolioMarginRequirement;
  }

  public void setIntegratedPortfolioMarginRequirement(String integratedPortfolioMarginRequirement) {
    this.integratedPortfolioMarginRequirement = integratedPortfolioMarginRequirement;
  }

  public String getIneligibleFuturesMarginRequirement() {
    return ineligibleFuturesMarginRequirement;
  }

  public void setIneligibleFuturesMarginRequirement(String ineligibleFuturesMarginRequirement) {
    this.ineligibleFuturesMarginRequirement = ineligibleFuturesMarginRequirement;
  }

  public PrimeXMMarginRequirementBreakdown getPmrBreakdown() {
    return pmrBreakdown;
  }

  public void setPmrBreakdown(PrimeXMMarginRequirementBreakdown pmrBreakdown) {
    this.pmrBreakdown = pmrBreakdown;
  }

  public PrimeXMMarginRequirementBreakdown getIpmrBreakdown() {
    return ipmrBreakdown;
  }

  public void setIpmrBreakdown(PrimeXMMarginRequirementBreakdown ipmrBreakdown) {
    this.ipmrBreakdown = ipmrBreakdown;
  }

  public PrimeXMOffsetCreditBreakdown getPortfolioMarginOffsetCreditBreakdown() {
    return portfolioMarginOffsetCreditBreakdown;
  }

  public void setPortfolioMarginOffsetCreditBreakdown(
      PrimeXMOffsetCreditBreakdown portfolioMarginOffsetCreditBreakdown) {
    this.portfolioMarginOffsetCreditBreakdown = portfolioMarginOffsetCreditBreakdown;
  }

  public PrimeXMOffsetCreditBreakdown getIntegratedPortfolioMarginOffsetCreditBreakdown() {
    return integratedPortfolioMarginOffsetCreditBreakdown;
  }

  public void setIntegratedPortfolioMarginOffsetCreditBreakdown(
      PrimeXMOffsetCreditBreakdown integratedPortfolioMarginOffsetCreditBreakdown) {
    this.integratedPortfolioMarginOffsetCreditBreakdown =
        integratedPortfolioMarginOffsetCreditBreakdown;
  }

  public List<CrossMarginPrimeXMPosition> getXMPositions() {
    return xmPositions;
  }

  public void setXMPositions(List<CrossMarginPrimeXMPosition> xmPositions) {
    this.xmPositions = xmPositions;
  }

  public static class Builder {
    private String dcoMarginRequirement;

    private String portfolioMarginRequirement;

    private String integratedPortfolioMarginRequirement;

    private String ineligibleFuturesMarginRequirement;

    private PrimeXMMarginRequirementBreakdown pmrBreakdown;

    private PrimeXMMarginRequirementBreakdown ipmrBreakdown;

    private PrimeXMOffsetCreditBreakdown portfolioMarginOffsetCreditBreakdown;

    private PrimeXMOffsetCreditBreakdown integratedPortfolioMarginOffsetCreditBreakdown;

    private List<CrossMarginPrimeXMPosition> xmPositions;

    public Builder dcoMarginRequirement(String dcoMarginRequirement) {
      this.dcoMarginRequirement = dcoMarginRequirement;
      return this;
    }

    public Builder portfolioMarginRequirement(String portfolioMarginRequirement) {
      this.portfolioMarginRequirement = portfolioMarginRequirement;
      return this;
    }

    public Builder integratedPortfolioMarginRequirement(
        String integratedPortfolioMarginRequirement) {
      this.integratedPortfolioMarginRequirement = integratedPortfolioMarginRequirement;
      return this;
    }

    public Builder ineligibleFuturesMarginRequirement(String ineligibleFuturesMarginRequirement) {
      this.ineligibleFuturesMarginRequirement = ineligibleFuturesMarginRequirement;
      return this;
    }

    public Builder pmrBreakdown(PrimeXMMarginRequirementBreakdown pmrBreakdown) {
      this.pmrBreakdown = pmrBreakdown;
      return this;
    }

    public Builder ipmrBreakdown(PrimeXMMarginRequirementBreakdown ipmrBreakdown) {
      this.ipmrBreakdown = ipmrBreakdown;
      return this;
    }

    public Builder portfolioMarginOffsetCreditBreakdown(
        PrimeXMOffsetCreditBreakdown portfolioMarginOffsetCreditBreakdown) {
      this.portfolioMarginOffsetCreditBreakdown = portfolioMarginOffsetCreditBreakdown;
      return this;
    }

    public Builder integratedPortfolioMarginOffsetCreditBreakdown(
        PrimeXMOffsetCreditBreakdown integratedPortfolioMarginOffsetCreditBreakdown) {
      this.integratedPortfolioMarginOffsetCreditBreakdown =
          integratedPortfolioMarginOffsetCreditBreakdown;
      return this;
    }

    public Builder xmPositions(List<CrossMarginPrimeXMPosition> xmPositions) {
      this.xmPositions = xmPositions;
      return this;
    }

    public CrossMarginPrimeRiskNettingInfo build() {
      return new CrossMarginPrimeRiskNettingInfo(this);
    }
  }
}
