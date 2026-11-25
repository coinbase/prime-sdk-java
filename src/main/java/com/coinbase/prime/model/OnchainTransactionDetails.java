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

import com.coinbase.prime.model.enums.SigningStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public class OnchainTransactionDetails {
  /**
   * The hex-encoded signed transaction. Only returned for transactions created with skip_broadcast
   * set to true; empty for all other transactions, including broadcast transactions that have been
   * signed.
   */
  @JsonProperty("signed_transaction")
  private String signedTransaction;

  /** New message for risk assessment details */
  @JsonProperty("risk_assessment")
  private RiskAssessment riskAssessment;

  /** The blockchain network chain ID. Will be empty for Solana transactions. */
  @JsonProperty("chain_id")
  private String chainId;

  /** The transaction nonce. Only present for EVM-based blockchain transactions. */
  private String nonce;

  /** The ID of the transaction that this transaction replaced */
  @JsonProperty("replaced_transaction_id")
  private String replacedTransactionId;

  /** The destination address for the transaction */
  @JsonProperty("destination_address")
  private String destinationAddress;

  /**
   * If set to true, the transaction will not be broadcast to the network. You can still retrieve
   * the signed transaction from the GetTransaction endpoint by transaction ID once the transaction
   * is created.
   */
  @JsonProperty("skip_broadcast")
  private boolean skipBroadcast;

  /** Reason for transaction failure if applicable */
  @JsonProperty("failure_reason")
  private String failureReason;

  /**
   * - SIGNING_STATUS_UNKNOWN: Unknown signing status - SIGNED: Transaction has been signed -
   * UNSIGNED: Transaction is unsigned
   */
  @JsonProperty("signing_status")
  private SigningStatus signingStatus;

  /**
   * The hex-encoded unsigned transaction exactly as submitted when the transaction was created.
   * This value is never updated. Before signing, Prime may set the nonce, network fees, or gas
   * limit on EVM transactions, or refresh the recent blockhash on Solana transactions, so the
   * transaction that is signed and broadcast can differ from this payload. The transfer amount is
   * only changed for EVM transactions initiated as a maximum transfer of the network's native
   * asset, where Prime lowers it so that the amount plus network fees does not exceed the wallet's
   * current balance.
   */
  @JsonProperty("original_unsigned_transaction")
  private String originalUnsignedTransaction;

  public OnchainTransactionDetails() {}

  public OnchainTransactionDetails(Builder builder) {
    this.signedTransaction = builder.signedTransaction;
    this.riskAssessment = builder.riskAssessment;
    this.chainId = builder.chainId;
    this.nonce = builder.nonce;
    this.replacedTransactionId = builder.replacedTransactionId;
    this.destinationAddress = builder.destinationAddress;
    this.skipBroadcast = builder.skipBroadcast;
    this.failureReason = builder.failureReason;
    this.signingStatus = builder.signingStatus;
    this.originalUnsignedTransaction = builder.originalUnsignedTransaction;
  }

  public String getSignedTransaction() {
    return signedTransaction;
  }

  public void setSignedTransaction(String signedTransaction) {
    this.signedTransaction = signedTransaction;
  }

  public RiskAssessment getRiskAssessment() {
    return riskAssessment;
  }

  public void setRiskAssessment(RiskAssessment riskAssessment) {
    this.riskAssessment = riskAssessment;
  }

  public String getChainId() {
    return chainId;
  }

  public void setChainId(String chainId) {
    this.chainId = chainId;
  }

  public String getNonce() {
    return nonce;
  }

  public void setNonce(String nonce) {
    this.nonce = nonce;
  }

  public String getReplacedTransactionId() {
    return replacedTransactionId;
  }

  public void setReplacedTransactionId(String replacedTransactionId) {
    this.replacedTransactionId = replacedTransactionId;
  }

  public String getDestinationAddress() {
    return destinationAddress;
  }

  public void setDestinationAddress(String destinationAddress) {
    this.destinationAddress = destinationAddress;
  }

  public boolean getSkipBroadcast() {
    return skipBroadcast;
  }

  public void setSkipBroadcast(boolean skipBroadcast) {
    this.skipBroadcast = skipBroadcast;
  }

  public String getFailureReason() {
    return failureReason;
  }

  public void setFailureReason(String failureReason) {
    this.failureReason = failureReason;
  }

  public SigningStatus getSigningStatus() {
    return signingStatus;
  }

  public void setSigningStatus(SigningStatus signingStatus) {
    this.signingStatus = signingStatus;
  }

  public String getOriginalUnsignedTransaction() {
    return originalUnsignedTransaction;
  }

  public void setOriginalUnsignedTransaction(String originalUnsignedTransaction) {
    this.originalUnsignedTransaction = originalUnsignedTransaction;
  }

  public static class Builder {
    private String signedTransaction;

    private RiskAssessment riskAssessment;

    private String chainId;

    private String nonce;

    private String replacedTransactionId;

    private String destinationAddress;

    private boolean skipBroadcast;

    private String failureReason;

    private SigningStatus signingStatus;

    private String originalUnsignedTransaction;

    public Builder signedTransaction(String signedTransaction) {
      this.signedTransaction = signedTransaction;
      return this;
    }

    public Builder riskAssessment(RiskAssessment riskAssessment) {
      this.riskAssessment = riskAssessment;
      return this;
    }

    public Builder chainId(String chainId) {
      this.chainId = chainId;
      return this;
    }

    public Builder nonce(String nonce) {
      this.nonce = nonce;
      return this;
    }

    public Builder replacedTransactionId(String replacedTransactionId) {
      this.replacedTransactionId = replacedTransactionId;
      return this;
    }

    public Builder destinationAddress(String destinationAddress) {
      this.destinationAddress = destinationAddress;
      return this;
    }

    public Builder skipBroadcast(boolean skipBroadcast) {
      this.skipBroadcast = skipBroadcast;
      return this;
    }

    public Builder failureReason(String failureReason) {
      this.failureReason = failureReason;
      return this;
    }

    public Builder signingStatus(SigningStatus signingStatus) {
      this.signingStatus = signingStatus;
      return this;
    }

    public Builder originalUnsignedTransaction(String originalUnsignedTransaction) {
      this.originalUnsignedTransaction = originalUnsignedTransaction;
      return this;
    }

    public OnchainTransactionDetails build() {
      return new OnchainTransactionDetails(this);
    }
  }
}
