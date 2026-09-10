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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ErrorSubcodeGenerationTest {
  @Test
  void classifiesErrorCodeAndSubcodeEnumsSeparatelyFromDomainEnums() {
    assertTrue(GeneratedEnumKind.isSubcode("CreateOrderBadRequestSubcode"));
    assertTrue(GeneratedEnumKind.isErrorCode("BadRequestErrorCode"));
    assertTrue(GeneratedEnumKind.isErrorEnum("CreateOrderBadRequestSubcode"));
    assertTrue(GeneratedEnumKind.isErrorEnum("BadRequestErrorCode"));
    assertFalse(GeneratedEnumKind.isErrorEnum("OrderSide"));
    assertEquals(
        GeneratedEnumKind.ERRORS_PACKAGE,
        GeneratedEnumKind.packageFor("CreateOrderBadRequestSubcode"));
    assertEquals(GeneratedEnumKind.ERRORS_PACKAGE, GeneratedEnumKind.packageFor("BadRequestErrorCode"));
    assertEquals(GeneratedEnumKind.ENUMS_PACKAGE, GeneratedEnumKind.packageFor("OrderSide"));
  }

  @Test
  void postProcessorRoutesSubcodesAndFixesModelImports() throws Exception {
    Path root = Files.createTempDirectory("error-subcode-generation");
    Path generatedModels = root.resolve("generated/raw/src/main/java/com/coinbase/prime/model");
    Path modelRoot = root.resolve("src/main/java/com/coinbase/prime/model");
    Path enumsRoot = modelRoot.resolve("enums");
    Path errorsRoot = modelRoot.resolve("errors");
    Path spec = root.resolve("openapi.yaml");
    Files.createDirectories(generatedModels);
    Files.writeString(spec, "openapi: 3.0.0\ncomponents:\n  schemas: {}\n");
    Files.writeString(
        generatedModels.resolve("PrimeRESTAPICreateThingBadRequestSubcode.java"),
        "package com.coinbase.prime.model;\n"
            + "public enum PrimeRESTAPICreateThingBadRequestSubcode { INVALID }\n");
    Files.writeString(
        generatedModels.resolve("BadRequestErrorCode.java"),
        "package com.coinbase.prime.model;\n"
            + "public enum BadRequestErrorCode { INVALID }\n");
    Files.writeString(
        generatedModels.resolve("ErrorEnvelope.java"),
        "package com.coinbase.prime.model;\n"
            + "import com.coinbase.prime.model.PrimeRESTAPICreateThingBadRequestSubcode;\n"
            + "public class ErrorEnvelope {\n"
            + "  private PrimeRESTAPICreateThingBadRequestSubcode subcode;\n"
            + "}\n");

    new PostProcessor(root.resolve("generated"), modelRoot, enumsRoot, errorsRoot, spec).processModels();

    Path errorEnum = errorsRoot.resolve("CreateThingBadRequestSubcode.java");
    assertTrue(Files.exists(errorEnum));
    assertFalse(Files.exists(enumsRoot.resolve("CreateThingBadRequestSubcode.java")));
    assertTrue(Files.readString(errorEnum).contains("package com.coinbase.prime.model.errors;"));
    assertTrue(Files.exists(errorsRoot.resolve("BadRequestErrorCode.java")));
    assertTrue(Files.readString(modelRoot.resolve("ErrorEnvelope.java"))
        .contains("import com.coinbase.prime.model.errors.CreateThingBadRequestSubcode;"));
  }

  @Test
  void actualOpenApiGenerationDoesNotIgnoreErrorEnumsBeforeErrorsRouting() throws Exception {
    Path root = Files.createTempDirectory("raw-error-enum-generation");
    Path spec = root.resolve("openapi.yaml");
    Files.writeString(
        spec,
        "openapi: 3.0.0\n"
            + "info: { title: test, version: 1.0.0 }\n"
            + "paths: {}\n"
            + "components:\n"
            + "  schemas:\n"
            + "    PrimeRESTAPI_CreateThingBadRequestSubcode:\n"
            + "      type: string\n"
            + "      enum: [INVALID]\n");
    Path projectRoot = Path.of(System.getProperty("user.dir")).toAbsolutePath().getParent().getParent();
    Path generated = root.resolve("generated");
    Path sourceRoot = root.resolve("src/main/java");
    Path modelRoot = sourceRoot.resolve("com/coinbase/prime/model");

    new OpenApiGenerator(spec.toString(), generated, projectRoot).generateModels();
    new PostProcessor(
            generated,
            sourceRoot,
            modelRoot,
            modelRoot.resolve("enums"),
            modelRoot.resolve("errors"),
            spec,
            root.resolve("generated-model-files.json"))
        .processModels();

    assertTrue(Files.exists(modelRoot.resolve("errors/CreateThingBadRequestSubcode.java")));
  }

  @Test
  void cleansOnlyManifestOwnedModelsAndSkipsIgnoredResponseSchemas() throws Exception {
    Path root = Files.createTempDirectory("model-manifest-generation");
    Path rawModels = root.resolve("generated/raw/src/main/java/com/coinbase/prime/model");
    Path sourceRoot = root.resolve("src/main/java");
    Path modelRoot = sourceRoot.resolve("com/coinbase/prime/model");
    Path manifest = root.resolve("generated-model-files.json");
    Path spec = root.resolve("openapi.yaml");
    Files.createDirectories(rawModels);
    Files.createDirectories(modelRoot);
    Files.writeString(spec, "openapi: 3.0.0\ncomponents:\n  schemas: {}\n");
    Files.writeString(rawModels.resolve("Fresh.java"),
        "package com.coinbase.prime.model;\npublic class Fresh {}\n");
    Files.writeString(rawModels.resolve("IgnoredResponse.java"),
        "package com.coinbase.prime.model;\npublic class IgnoredResponse {}\n");
    Files.writeString(modelRoot.resolve("Stale.java"), "stale");
    Files.writeString(modelRoot.resolve("HandWritten.java"), "keep");
    Files.writeString(manifest, "[\n  \"com/coinbase/prime/model/Stale.java\"\n]\n");

    new PostProcessor(
        root.resolve("generated"), sourceRoot, modelRoot, modelRoot.resolve("enums"),
        modelRoot.resolve("errors"), spec, manifest).processModels();

    assertTrue(Files.exists(modelRoot.resolve("Fresh.java")));
    assertFalse(Files.exists(modelRoot.resolve("IgnoredResponse.java")));
    assertFalse(Files.exists(modelRoot.resolve("Stale.java")));
    assertTrue(Files.exists(modelRoot.resolve("HandWritten.java")));
    assertTrue(Files.readString(manifest).contains("Fresh.java"));
  }
}
