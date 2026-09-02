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
import java.util.List;
import java.util.Map;

/** Immutable operation inventory used by all non-model generator phases. */
public final class SpecModels {
  private SpecModels() {}

  public static final class Document {
    private final Map<String, Object> root;
    private final List<Operation> operations;

    Document(Map<String, Object> root, List<Operation> operations) {
      this.root = root;
      this.operations = Collections.unmodifiableList(operations);
    }

    public Map<String, Object> root() { return root; }
    public List<Operation> operations() { return operations; }
  }

  public static final class Operation {
    private final String operationId;
    private final String httpMethod;
    private final String path;
    private final List<String> tags;
    private final List<Parameter> parameters;
    private final Map<String, Object> requestBodySchema;
    private final Map<String, Object> successResponseSchema;
    private final List<Integer> successStatusCodes;
    private final String summary;
    private final String sdkMethodName;

    Operation(String operationId, String httpMethod, String path, List<String> tags,
        List<Parameter> parameters, Map<String, Object> requestBodySchema,
        Map<String, Object> successResponseSchema, List<Integer> successStatusCodes,
        String summary, String sdkMethodName) {
      this.operationId = operationId;
      this.httpMethod = httpMethod;
      this.path = path;
      this.tags = Collections.unmodifiableList(tags);
      this.parameters = Collections.unmodifiableList(parameters);
      this.requestBodySchema = requestBodySchema;
      this.successResponseSchema = successResponseSchema;
      this.successStatusCodes = Collections.unmodifiableList(successStatusCodes);
      this.summary = summary;
      this.sdkMethodName = sdkMethodName;
    }

    public String operationId() { return operationId; }
    public String httpMethod() { return httpMethod; }
    public String path() { return path; }
    public List<String> tags() { return tags; }
    public List<Parameter> parameters() { return parameters; }
    public Map<String, Object> requestBodySchema() { return requestBodySchema; }
    public Map<String, Object> successResponseSchema() { return successResponseSchema; }
    public List<Integer> successStatusCodes() { return successStatusCodes; }
    public String summary() { return summary; }
    public String sdkMethodName() { return sdkMethodName; }
  }

  public static final class Parameter {
    private final String name;
    private final String location;
    private final boolean required;
    private final Map<String, Object> schema;

    Parameter(String name, String location, boolean required, Map<String, Object> schema) {
      this.name = name;
      this.location = location;
      this.required = required;
      this.schema = schema;
    }

    public String name() { return name; }
    public String location() { return location; }
    public boolean required() { return required; }
    public Map<String, Object> schema() { return schema; }
  }
}
