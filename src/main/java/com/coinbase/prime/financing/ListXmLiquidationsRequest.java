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

package com.coinbase.prime.financing;

import static com.coinbase.core.utils.Utils.isNullOrEmpty;

import com.coinbase.core.errors.CoinbaseClientException;
import com.coinbase.prime.common.Pagination;
import com.coinbase.prime.common.PrimeListRequest;
import com.coinbase.prime.model.enums.SortDirection;
import com.coinbase.prime.model.enums.XMLiquidationStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

/** List Cross Margin Liquidations */
public class ListXmLiquidationsRequest extends PrimeListRequest {
  /** XM customer Prime Entity ID */
  @JsonProperty(required = true, value = "entity_id")
  @JsonIgnore
  private String entityId;

  @JsonProperty("status")
  private XMLiquidationStatus status;

  @JsonProperty("start_time")
  private String startTime;

  @JsonProperty("end_time")
  private String endTime;

  public ListXmLiquidationsRequest() {}

  public ListXmLiquidationsRequest(Builder builder) {
    super(builder.cursor, builder.sortDirection, builder.limit);
    this.entityId = builder.entityId;
    this.status = builder.status;
    this.startTime = builder.startTime;
    this.endTime = builder.endTime;
  }

  public String getEntityId() {
    return entityId;
  }

  public void setEntityId(String entityId) {
    this.entityId = entityId;
  }

  public XMLiquidationStatus getStatus() {
    return status;
  }

  public void setStatus(XMLiquidationStatus status) {
    this.status = status;
  }

  public String getStartTime() {
    return startTime;
  }

  public void setStartTime(String startTime) {
    this.startTime = startTime;
  }

  public String getEndTime() {
    return endTime;
  }

  public void setEndTime(String endTime) {
    this.endTime = endTime;
  }

  public static class Builder {
    private String entityId;
    private XMLiquidationStatus status;
    private String startTime;
    private String endTime;
    private String cursor;
    private SortDirection sortDirection;
    private Integer limit;

    public Builder() {}

    public Builder entityId(String entityId) {
      this.entityId = entityId;
      return this;
    }

    public Builder status(XMLiquidationStatus status) {
      this.status = status;
      return this;
    }

    public Builder startTime(String startTime) {
      this.startTime = startTime;
      return this;
    }

    public Builder endTime(String endTime) {
      this.endTime = endTime;
      return this;
    }

    public Builder limit(Integer limit) {
      this.limit = limit;
      return this;
    }

    public Builder pagination(Pagination pagination) {
      this.cursor = pagination.getNextCursor();
      this.sortDirection = pagination.getSortDirection();
      return this;
    }

    public ListXmLiquidationsRequest build() throws CoinbaseClientException {
      validate();
      return new ListXmLiquidationsRequest(this);
    }

    private void validate() throws CoinbaseClientException {
      if (isNullOrEmpty(this.entityId)) {
        throw new CoinbaseClientException("EntityId is required");
      }
    }
  }
}
