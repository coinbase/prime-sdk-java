/*
 * Copyright 2025-present Coinbase Global, Inc.
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

package com.coinbase.prime.transactions;

import com.fasterxml.jackson.annotation.JsonProperty;

/** Create Transfer */
public class CreateWalletTransferResponse {
  /** The activity ID for the transfer */
  @JsonProperty("activity_id")
  private String activityId;

  /** A URL to the activity associated with this transfer for approval */
  @JsonProperty("approval_url")
  private String approvalUrl;

  /** The currency symbol of the transfer */
  @JsonProperty("symbol")
  private String symbol;

  /** The amount of the transfer */
  @JsonProperty("amount")
  private String amount;

  /** The network fee associated with the transfer */
  @JsonProperty("fee")
  private String fee;

  /** The destination address of the transfer */
  @JsonProperty("destination_address")
  private String destinationAddress;

  /** The destination type of the transfer */
  @JsonProperty("destination_type")
  private String destinationType;

  /** The source address used for the transfer */
  @JsonProperty("source_address")
  private String sourceAddress;

  /** The source type used for the transfer */
  @JsonProperty("source_type")
  private String sourceType;

  /** The id of the just created transaction */
  @JsonProperty("transaction_id")
  private String transactionId;

  public CreateWalletTransferResponse() {}

  public String getActivityId() {
    return activityId;
  }

  public void setActivityId(String activityId) {
    this.activityId = activityId;
  }

  public String getApprovalUrl() {
    return approvalUrl;
  }

  public void setApprovalUrl(String approvalUrl) {
    this.approvalUrl = approvalUrl;
  }

  public String getSymbol() {
    return symbol;
  }

  public void setSymbol(String symbol) {
    this.symbol = symbol;
  }

  public String getAmount() {
    return amount;
  }

  public void setAmount(String amount) {
    this.amount = amount;
  }

  public String getFee() {
    return fee;
  }

  public void setFee(String fee) {
    this.fee = fee;
  }

  public String getDestinationAddress() {
    return destinationAddress;
  }

  public void setDestinationAddress(String destinationAddress) {
    this.destinationAddress = destinationAddress;
  }

  public String getDestinationType() {
    return destinationType;
  }

  public void setDestinationType(String destinationType) {
    this.destinationType = destinationType;
  }

  public String getSourceAddress() {
    return sourceAddress;
  }

  public void setSourceAddress(String sourceAddress) {
    this.sourceAddress = sourceAddress;
  }

  public String getSourceType() {
    return sourceType;
  }

  public void setSourceType(String sourceType) {
    this.sourceType = sourceType;
  }

  public String getTransactionId() {
    return transactionId;
  }

  public void setTransactionId(String transactionId) {
    this.transactionId = transactionId;
  }
}
