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

package com.coinbase.tools.modelgenerator;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Year;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Copyright header handling for generated models — parity with prime-sdk-ts
 * {@code getHeaderYear()} / {@code addGeneratedHeader()} in {@code apiSpec/generateTypes.js}.
 *
 * <p>The Mustache template uses a placeholder start year; this class preserves the year from an
 * existing SDK file when regenerating, and uses the current calendar year for brand-new files.
 */
public final class GeneratedFileHeader {
    private static final Pattern COPYRIGHT_YEAR =
        Pattern.compile("Copyright (\\d{4})-present Coinbase Global, Inc\\.");

    private GeneratedFileHeader() {}

    /**
     * @param outputFile final path under {@code src/main/java/.../model/} (or {@code enums/})
     */
    public static String resolveStartYear(Path outputFile) throws IOException {
        if (Files.exists(outputFile)) {
            Optional<String> year = extractStartYear(Files.readString(outputFile));
            if (year.isPresent()) {
                return year.get();
            }
        }

        Path parent = outputFile.getParent();
        if (parent != null && Files.isDirectory(parent)) {
            String fileName = outputFile.getFileName().toString();
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(parent, "*.java")) {
                for (Path candidate : stream) {
                    if (!candidate.getFileName().toString().equalsIgnoreCase(fileName)) {
                        continue;
                    }
                    Optional<String> year = extractStartYear(Files.readString(candidate));
                    if (year.isPresent()) {
                        return year.get();
                    }
                }
            }
        }

        return defaultStartYear();
    }

    /** Replaces the template placeholder year with {@code startYear}. */
    public static String applyStartYear(String generatedContent, String startYear) {
        Matcher matcher = COPYRIGHT_YEAR.matcher(generatedContent);
        if (!matcher.find()) {
            return generatedContent;
        }
        return matcher.replaceFirst("Copyright " + startYear + "-present Coinbase Global, Inc.");
    }

    static Optional<String> extractStartYear(String fileContent) {
        Matcher matcher = COPYRIGHT_YEAR.matcher(fileContent);
        if (matcher.find()) {
            return Optional.of(matcher.group(1));
        }
        return Optional.empty();
    }

    static String defaultStartYear() {
        return String.valueOf(Year.now().getValue());
    }
}
