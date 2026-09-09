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
  void classifiesSubcodeEnumsSeparatelyFromDomainEnums() {
    assertTrue(GeneratedEnumKind.isSubcode("CreateOrderBadRequestSubcode"));
    assertFalse(GeneratedEnumKind.isSubcode("OrderSide"));
    assertEquals(GeneratedEnumKind.ERRORS_PACKAGE,
        GeneratedEnumKind.packageFor("CreateOrderBadRequestSubcode"));
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
    assertTrue(Files.readString(modelRoot.resolve("ErrorEnvelope.java"))
        .contains("import com.coinbase.prime.model.errors.CreateThingBadRequestSubcode;"));
  }
}
