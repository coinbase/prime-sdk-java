/*
 * Copyright 2026-present Coinbase Global, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.coinbase.tools.modelgenerator;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/** Resolved Java SDK ownership and naming for one OpenAPI operation. */
public final class OperationBinding {
  private final String operationId;
  private final String serviceFolder;
  private final String serviceName;
  private final String sdkMethod;
  private final boolean omitRequest;
  private final boolean paginated;
  private final Map<String, String> parameterTypeOverrides;

  OperationBinding(
      String operationId,
      String serviceFolder,
      String serviceName,
      String sdkMethod,
      boolean omitRequest,
      boolean paginated,
      Map<String, String> parameterTypeOverrides) {
    this.operationId = operationId;
    this.serviceFolder = serviceFolder;
    this.serviceName = serviceName;
    this.sdkMethod = sdkMethod;
    this.omitRequest = omitRequest;
    this.paginated = paginated;
    this.parameterTypeOverrides = Collections.unmodifiableMap(new LinkedHashMap<>(parameterTypeOverrides));
  }

  public String operationId() { return operationId; }
  public String serviceFolder() { return serviceFolder; }
  public String serviceName() { return serviceName; }
  public String sdkMethod() { return sdkMethod; }
  public boolean omitRequest() { return omitRequest; }
  public boolean paginated() { return paginated; }
  public Map<String, String> parameterTypeOverrides() { return parameterTypeOverrides; }
}
