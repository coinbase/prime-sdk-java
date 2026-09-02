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

package com.coinbase.prime.apikey;

import static org.junit.jupiter.api.Assertions.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ApiKeyServiceSerializationTest {

  private ObjectMapper objectMapper;

  @BeforeEach
  public void setUp() {
    objectMapper =
        new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
  }

  @Test
  public void testRotateApiKeyRequestEmptyBuilder() throws JsonProcessingException {
    RotateApiKeyRequest request = new RotateApiKeyRequest.Builder().build();
    assertNotNull(request);
    assertNull(request.getDurationSeconds());
  }

  @Test
  public void testRotateApiKeyRequestSerialization() throws JsonProcessingException {
    RotateApiKeyRequest request = new RotateApiKeyRequest.Builder().durationSeconds(3600L).build();
    String json = objectMapper.writeValueAsString(request);
    assertTrue(json.contains("\"duration_seconds\":3600"));
  }

  @Test
  public void testRotateApiKeyResponseDeserialization() throws JsonProcessingException {
    String json =
        "{"
            + "\"encrypted_credentials\":\"abc123\","
            + "\"activity_id\":\"e8bbed13-fa33-41de-86d5-4335d8f08166\""
            + "}";
    RotateApiKeyResponse response = objectMapper.readValue(json, RotateApiKeyResponse.class);
    assertNotNull(response);
    assertEquals("abc123", response.getEncryptedCredentials());
    assertEquals("e8bbed13-fa33-41de-86d5-4335d8f08166", response.getActivityId());
  }
}
