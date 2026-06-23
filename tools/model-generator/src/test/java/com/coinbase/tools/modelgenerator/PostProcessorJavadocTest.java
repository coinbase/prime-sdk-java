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

package com.coinbase.tools.modelgenerator;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class PostProcessorJavadocTest {

  @Test
  void decodeHtmlEntities_decodesCommonEntities() {
    assertEquals(
        "aged > urgent > standard > debit",
        PostProcessor.decodeHtmlEntities("aged &gt; urgent &gt; standard &gt; debit"));
    assertEquals(
        "EQ / MR >= threshold_value",
        PostProcessor.decodeHtmlEntities("EQ / MR &gt;= threshold_value"));
    assertEquals(
        "(tier_a, tier_b) -> rate",
        PostProcessor.decodeHtmlEntities("(tier_a, tier_b) -&gt; rate"));
    assertEquals(
        "margin excess is > 0",
        PostProcessor.decodeHtmlEntities("margin excess is &gt; 0"));
  }

  @Test
  void decodeHtmlEntities_leavesPlainTextUnchanged() {
    assertEquals(
        "aged > urgent > standard > debit",
        PostProcessor.decodeHtmlEntities("aged > urgent > standard > debit"));
    assertEquals(
        "EQ / MR >= threshold_value",
        PostProcessor.decodeHtmlEntities("EQ / MR >= threshold_value"));
  }

  @Test
  void decodeHtmlEntities_decodesAcrossFullFileContent() {
    String input =
        "public class Foo {\n"
            + "  /**\n"
            + "   * aged &gt; urgent &gt; standard &gt; debit.\n"
            + "   */\n"
            + "  private String bar;\n"
            + "}\n";

    String expected =
        "public class Foo {\n"
            + "  /**\n"
            + "   * aged > urgent > standard > debit.\n"
            + "   */\n"
            + "  private String bar;\n"
            + "}\n";

    assertEquals(expected, PostProcessor.decodeHtmlEntities(input));
  }
}
