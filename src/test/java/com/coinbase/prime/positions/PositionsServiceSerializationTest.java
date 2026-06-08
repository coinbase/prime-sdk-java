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

package com.coinbase.prime.positions;

import static org.junit.jupiter.api.Assertions.*;

import com.coinbase.core.errors.CoinbaseClientException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PositionsServiceSerializationTest {

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  // ==================== ListAggregateEntityPositions Tests ====================

  @Test
  public void testListAggregateEntityPositionsRequestConstruction() throws CoinbaseClientException {
    ListAggregateEntityPositionsRequest request =
        new ListAggregateEntityPositionsRequest.Builder().entityId("entity-123").build();
    assertNotNull(request);
    assertEquals("entity-123", request.getEntityId());
  }

  @Test
  public void testListAggregateEntityPositionsRequestBuilderValidation() {
    assertThrows(
        CoinbaseClientException.class,
        () -> new ListAggregateEntityPositionsRequest.Builder().build());
  }

  @Test
  public void testListAggregateEntityPositionsResponseDeserialization()
      throws JsonProcessingException {
    String json =
        "{"
            + "\"positions\":["
            + "{\"symbol\":\"BTC\",\"amount\":\"0.5\",\"cost_basis\":\"25000.00\"},"
            + "{\"symbol\":\"ETH\",\"amount\":\"5.0\",\"cost_basis\":\"15000.00\"}"
            + "],"
            + "\"pagination\":{\"has_next\":false}"
            + "}";

    ListAggregateEntityPositionsResponse response =
        objectMapper.readValue(json, ListAggregateEntityPositionsResponse.class);
    assertNotNull(response);
    assertNotNull(response.getPositions());
    assertEquals(2, response.getPositions().length);
  }

  // ==================== ListEntityPositions Tests ====================

  @Test
  public void testListEntityPositionsRequestConstruction() throws CoinbaseClientException {
    ListEntityPositionsRequest request =
        new ListEntityPositionsRequest.Builder().entityId("entity-123").build();
    assertNotNull(request);
    assertEquals("entity-123", request.getEntityId());
  }

  @Test
  public void testListEntityPositionsResponseDeserialization() throws JsonProcessingException {
    String json =
        "{"
            + "\"positions\":["
            + "{\"symbol\":\"BTC\",\"amount\":\"1.0\",\"cost_basis\":\"50000.00\"},"
            + "{\"symbol\":\"ETH\",\"amount\":\"10.0\",\"cost_basis\":\"30000.00\"}"
            + "],"
            + "\"pagination\":{\"next_cursor\":\"pos-cursor\",\"has_next\":true}"
            + "}";

    ListEntityPositionsResponse response =
        objectMapper.readValue(json, ListEntityPositionsResponse.class);
    assertNotNull(response);
    assertNotNull(response.getPositions());
    assertEquals(2, response.getPositions().length);
    assertNotNull(response.getPagination());
  }
}
