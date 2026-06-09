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

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PostProcessor {
    private static final Logger logger = LoggerFactory.getLogger(PostProcessor.class);

    // Special case enum renames: stripped name -> final enum name
    // This handles cases where enums need custom naming beyond simple prefix stripping
    private static final Map<String, String> ENUM_NAME_MAPPINGS = new HashMap<String, String>() {{
        put("ActivityType", "PrimeActivityType");  // CoinbasePublicRestApiActivityType -> PrimeActivityType
        // CoinbaseCustodyApiActivityType is handled by stripCommonPrefixes special case
    }};

    /**
     * Duplicate enums for TS/Go-aligned names on Prime cross-margin responses.
     * Graduated spec refs {@code XMControlStatus}; other SDKs expose {@code PrimeXMControlStatus} too.
     */
    private static final Map<String, String> ENUM_ALIASES = new LinkedHashMap<String, String>() {{
        put("XMControlStatus", "PrimeXMControlStatus");
        put("XMMarginLevel", "PrimeXMMarginLevel");
    }};

    private final Path tempDir;
    private final Path outputDir;
    private final Path enumsDir;
    private final EnumJavadocEnhancer enumJavadocEnhancer;
    private final ModelJavadocEnhancer modelJavadocEnhancer;

    private int newModelsCount = 0;
    private int updatedModelsCount = 0;
    private int removedStaleCount = 0;
    private final Set<Path> writtenOutputFiles = new HashSet<>();

    public PostProcessor(Path tempDir, Path outputDir, Path enumsDir, Path specPath) throws IOException {
        this.tempDir = tempDir;
        this.outputDir = outputDir;
        this.enumsDir = enumsDir;
        this.enumJavadocEnhancer = EnumJavadocEnhancer.load(specPath);
        this.modelJavadocEnhancer = ModelJavadocEnhancer.load(specPath);
    }

    /** Maps an OpenAPI schema name to the SDK Java enum type name (prefix strip + acronym rules). */
    static String stripSchemaNameToJavaType(String schemaName) {
        return stripCommonPrefixes(schemaName);
    }

    /** Maps an OpenAPI schema short name to the generated Java model class name. */
    static String deriveJavaModelName(String rawSchemaName) {
        String result = rawSchemaName;
        for (Map.Entry<String, String> entry : CONTENT_REPLACEMENTS.entrySet()) {
            if (result.contains(entry.getKey())) {
                result = result.replace(entry.getKey(), entry.getValue());
            }
        }
        result = stripCommonPrefixes(result);
        if (result.contains("Web3")) {
            result = result.replace("Web3", "Onchain");
        }
        return result;
    }

    public void processModels() throws IOException {
        logger.info("Finding generated model files...");
        List<Path> modelFiles = findGeneratedModelFiles();
        logger.info("Found {} model files to process", modelFiles.size());

        // Create output directories
        Files.createDirectories(outputDir);
        Files.createDirectories(enumsDir);

        // Separate enum files from model files
        List<Path> enumFiles = new ArrayList<>();
        List<Path> nonEnumFiles = new ArrayList<>();

        for (Path file : modelFiles) {
            if (isEnumFile(file)) {
                enumFiles.add(file);
            } else {
                nonEnumFiles.add(file);
            }
        }

        logger.info("Found {} enum files and {} model files", enumFiles.size(), nonEnumFiles.size());

        // Process enums FIRST so they're available for import fixing
        logger.info("Processing enums first...");
        for (Path file : enumFiles) {
            String fileName = file.getFileName().toString();
            logger.info("Processing enum: {}", fileName);

            try {
                processEnumFile(file);
            } catch (Exception e) {
                logger.error("Error processing enum file: " + fileName, e);
            }
        }

        createEnumAliases();

        // Then process models with fixed enum imports
        logger.info("Processing models...");
        for (Path file : nonEnumFiles) {
            String fileName = file.getFileName().toString();
            logger.info("Processing model: {}", fileName);

            try {
                processModelFile(file);
            } catch (Exception e) {
                logger.error("Error processing model file: " + fileName, e);
            }
        }

        removeStaleOutputFiles();

        // Clean up temporary directory
        logger.info("Cleaning up temporary files...");
        FileUtils.deleteDirectory(tempDir.toFile());

        // Log summary
        logger.info("==========================================");
        logger.info("Model Generation Summary:");
        logger.info("  New models: {}", newModelsCount);
        logger.info("  Updated models: {}", updatedModelsCount);
        logger.info("  Removed stale: {}", removedStaleCount);
        logger.info("  Total processed: {}", modelFiles.size());
        logger.info("==========================================");
    }

    /**
     * Deletes model and enum files under the output directories that were not produced in this run.
     */
    private void removeStaleOutputFiles() throws IOException {
        removedStaleCount = 0;
        if (Files.exists(outputDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(outputDir, "*.java")) {
                for (Path file : stream) {
                    removedStaleCount += deleteIfStale(file);
                }
            }
        }
        if (Files.exists(enumsDir)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(enumsDir, "*.java")) {
                for (Path file : stream) {
                    removedStaleCount += deleteIfStale(file);
                }
            }
        }
        if (removedStaleCount > 0) {
            logger.info("Removed {} stale model/enum file(s) not produced by this generation run", removedStaleCount);
        }
    }

    private int deleteIfStale(Path file) throws IOException {
        Path normalized = file.toAbsolutePath().normalize();
        if (writtenOutputFiles.contains(normalized)) {
            return 0;
        }
        Files.delete(file);
        logger.info("Deleted stale file: {}", file.getFileName());
        return 1;
    }

    private List<Path> findGeneratedModelFiles() throws IOException {
        List<Path> files = new ArrayList<>();
        Path modelPath = tempDir.resolve("raw");

        if (Files.exists(modelPath)) {
            Files.walkFileTree(modelPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    String fileName = file.getFileName().toString();
                    if (file.toString().endsWith(".java") &&
                        !fileName.contains("Test") &&
                        !fileName.matches(".*Api\\.java$")) { // Only skip files ending with "Api.java", not containing "Api"
                        files.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        }

        return files;
    }

    private boolean isEnumFile(Path file) throws IOException {
        String content = Files.readString(file);
        return content.contains("public enum ");
    }

    /**
     * Fixes enum imports to use the enums package and applies special case enum name mappings.
     * Handles both import statements and type references throughout the content.
     */
    private String fixEnumImports(String content) {
        // Get list of all actual enum names from enums directory
        Set<String> actualEnumNames = new HashSet<>();
        try {
            Files.walk(enumsDir, 1)
                .filter(p -> p.toString().endsWith(".java"))
                .forEach(p -> {
                    String fileName = p.getFileName().toString();
                    actualEnumNames.add(fileName.replace(".java", ""));
                });
        } catch (Exception e) {
            logger.warn("Could not read enums directory: {}", e.getMessage());
        }

        // First, apply special case enum name mappings (e.g., ActivityType -> PrimeActivityType)
        // This must happen BEFORE fixing import paths
        for (Map.Entry<String, String> mapping : ENUM_NAME_MAPPINGS.entrySet()) {
            String strippedName = mapping.getKey();
            String actualEnumName = mapping.getValue();

            // Only apply mapping if the actual enum exists
            if (actualEnumNames.contains(actualEnumName)) {
                // Replace type references (but not in @JsonProperty annotations)
                // Pattern: word boundary + strippedName + word boundary (not followed by quotes)
                content = content.replaceAll(
                    "\\b" + strippedName + "\\b(?![^@]*@JsonProperty)",
                    actualEnumName
                );
                logger.debug("Applied enum mapping: {} -> {}", strippedName, actualEnumName);
            }
        }

        // Then, fix import paths for all enums (move from model to model.enums package)
        for (String enumName : actualEnumNames) {
            content = content.replace(
                "import com.coinbase.prime.model." + enumName + ";",
                "import com.coinbase.prime.model.enums." + enumName + ";"
            );
        }

        return content;
    }

    private void processEnumFile(Path file) throws IOException {
        processFile(file, enumsDir, true);
    }

    private void createEnumAliases() throws IOException {
        for (Map.Entry<String, String> alias : ENUM_ALIASES.entrySet()) {
            String sourceName = alias.getKey();
            String aliasName = alias.getValue();
            Path sourcePath = enumsDir.resolve(sourceName + ".java");
            if (!Files.exists(sourcePath)) {
                logger.warn("Skipping enum alias {} -> {} (source not found)", sourceName, aliasName);
                continue;
            }

            String content = Files.readString(sourcePath);
            content = content.replaceAll("\\b" + Pattern.quote(sourceName) + "\\b", aliasName);
            Path aliasPath = enumsDir.resolve(aliasName + ".java");
            String copyrightYear = GeneratedFileHeader.resolveStartYear(aliasPath);
            content = decodeHtmlEntities(content);
            content = GeneratedFileHeader.applyStartYear(content, copyrightYear);
            Files.writeString(aliasPath, content);
            writtenOutputFiles.add(aliasPath.toAbsolutePath().normalize());
            logger.info("Created enum alias: {} -> {}", sourceName, aliasName);
        }
    }

    private void processModelFile(Path file) throws IOException {
        processFile(file, outputDir, false);
    }

    /**
     * Unified file processing logic for both enums and models.
     * @param file Source file to process
     * @param targetDir Target directory (enumsDir for enums, outputDir for models)
     * @param isEnum Whether this is an enum file (affects package name and preserves SCREAMING_SNAKE_CASE)
     */
    private void processFile(Path file, Path targetDir, boolean isEnum) throws IOException {
        String content = Files.readString(file);
        String originalFileName = file.getFileName().toString();
        String className = extractClassName(content);

        // Apply content replacements to ALL files (matching TS replaceString logic)
        content = applyContentReplacements(content);

        // Re-extract class name AFTER content replacements (they may have changed it)
        String afterReplacementsClassName = extractClassName(content);

        // Strip prefixes from class name (matching prime-sdk-ts logic)
        String originalClassName = afterReplacementsClassName;
        className = stripCommonPrefixes(afterReplacementsClassName);

        if (!className.equals(originalClassName)) {
            // Replace ALL occurrences of the class/enum name in the content
            // Use negative lookahead (?!_) to preserve SCREAMING_SNAKE_CASE constants (applies to both enums and models)
            content = content.replaceAll("\\b" + Pattern.quote(originalClassName) + "\\b(?!_)", className);
            logger.info("Transformed {} name: {} -> {}", isEnum ? "enum" : "class", originalClassName, className);
        }

        // Apply Web3 to Onchain transformation (models only, but safe for enums too)
        if (!isEnum) {
            content = applyWeb3ToOnchainTransformation(content, className);
        }

        // Extract final class name after all transformations (for correct filename)
        className = extractClassName(content);
        String fileName = className + ".java";

        // Apply Web3 to Onchain transformation to filename (models only)
        if (!isEnum && fileName.contains("Web3")) {
            fileName = fileName.replace("Web3", "Onchain");
            className = className.replace("Web3", "Onchain");
        }

        // Log filename transformation if changed
        if (!originalFileName.equals(fileName)) {
            logger.info("Transformed {} filename: {} -> {}", isEnum ? "enum" : "model", originalFileName, fileName);
        }

        Path outputPath = targetDir.resolve(fileName);
        // Read copyright year before deleting case-variant paths (TS getHeaderYear parity)
        String copyrightYear = GeneratedFileHeader.resolveStartYear(outputPath);
        boolean existsBefore = Files.exists(outputPath);

        // Handle case-only filename changes on case-insensitive filesystems
        // Delete ANY file with case-insensitive matching name BEFORE writing
        final String finalFileName = fileName;
        try {
            List<Path> toDelete = Files.list(targetDir)
                .filter(p -> p.getFileName().toString().equalsIgnoreCase(finalFileName))
                .collect(java.util.stream.Collectors.toList());

            for (Path p : toDelete) {
                Files.delete(p);
                if (!p.getFileName().toString().equals(finalFileName)) {
                    logger.info("Deleted old {} file with different casing: {} -> {}",
                        isEnum ? "enum" : "model", p.getFileName(), finalFileName);
                }
            }
        } catch (IOException e) {
            logger.warn("Could not delete old {} file variants: {}", isEnum ? "enum" : "model", e.getMessage());
        }

        // Apply final transformations based on file type
        if (isEnum) {
            // Fix package for enums
            content = content.replace("package com.coinbase.prime.model;", "package com.coinbase.prime.model.enums;");
            content = enumJavadocEnhancer.apply(content, className);
        } else {
            // Fix enum imports for models
            content = fixEnumImports(content);
            // Ensure boolean fields use primitive type, not Boolean wrapper
            content = applyBooleanPrimitiveConversion(content);
            if ("DateOfBirth".equals(className)) {
                content = applyDateOfBirthPrimitiveConversion(content);
            }
            // Drop @JsonProperty when wire name equals Java field name (Jackson default mapping)
            content = removeRedundantJsonProperty(content);
            content = modelJavadocEnhancer.apply(content, className);
            content = removeUnusedImports(content);
        }

        content = decodeHtmlEntities(content);
        content = removeOpenApiGeneratorAttribution(content);
        content = GeneratedFileHeader.applyStartYear(content, copyrightYear);

        Files.writeString(outputPath, content);
        writtenOutputFiles.add(outputPath.toAbsolutePath().normalize());

        if (!existsBefore) {
            logger.info("Generated new {}: {}", isEnum ? "enum" : "model", className);
            newModelsCount++;
        } else {
            logger.info("Updated {}: {}", isEnum ? "enum" : "model", className);
            updatedModelsCount++;
        }
        logger.debug("Wrote {} file: {}", isEnum ? "enum" : "model", outputPath);
    }

    private String applyWeb3ToOnchainTransformation(String content, String className) {
        if (content.contains("Web3") || content.contains("web3")) {
            logger.info("Applying Web3 to Onchain transformation for: {}", className);

            // Replace class names
            content = content.replaceAll("\\bWeb3", "Onchain");

            // Replace in property names and method names
            content = content.replaceAll("\\bweb3", "onchain");

            // Keep JSON property mappings unchanged
            content = content.replaceAll("@JsonProperty\\(\"onchain", "@JsonProperty(\"web3");
        }

        return content;
    }

    private String extractClassName(String content) {
        Pattern pattern = Pattern.compile("public\\s+(?:class|enum)\\s+(\\w+)");
        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return "";
    }

    // File path replacements (matching prime-sdk-ts filePathReplacements)
    // Used for transforming class names and file names
    private static final Map<String, String> FILE_PATH_REPLACEMENTS = new LinkedHashMap<String, String>() {{
        put("CoinbaseCustodyApiActivityType", "CustodyActivityType");
        put("CoinbasePublicRestApiActivityType", "PrimeActivityType");
        put("CoinbaseBrokerageProxyEventsMaterializedApi", "");
        put("CoinbasePublicRestApi", "");
        put("CoinbaseCustodyApi", "");
        put("PrimeRESTAPI", "");
        put("PublicRestApi", "");
        put("rFQ", "RFQ");
        put("FcmFuturesSweep", "FuturesSweep");
        put("GoogleTypeDate", "DateOfBirth");
    }};

    // Content replacements (matching prime-sdk-ts replacements)
    // Applied to all file content to strip prefixes from type references
    private static final Map<String, String> CONTENT_REPLACEMENTS = new LinkedHashMap<String, String>() {{
        put("coinbaseCustodyApiActivityType", "CustodyActivityType");
        put("coinbasePublicRestApiActivityType", "PrimeActivityType");
        put("CoinbaseCustodyApiActivityType", "CustodyActivityType");
        put("CoinbasePublicRestApiActivityType", "PrimeActivityType");
        put("CoinbasePublicRestApi", "");
        put("coinbasePublicRestApi", "");
        put("PrimeRESTAPI", "");
        put("primeRESTAPI", "");
        put("CoinbaseCustodyApi", "");
        put("coinbaseCustodyApi", "");
        put("CoinbaseBrokerageProxyEventsMaterializedApi", "");
        put("coinbaseBrokerageProxyEventsMaterializedApi", "");
        put("publicRestApi", "");
        put("PublicRestApi", "");
        // Simplify verbose model names
        put("CreateOnchainTransactionRequestEvmParams", "EvmParams");
        put("FcmFuturesSweepRequestAmount", "SweepAmount");
        put("FcmFuturesSweep", "FuturesSweep");
        // google.type.Date is excluded as Google*; map to existing DateOfBirth (same year/month/day shape)
        put("GoogleTypeDate", "DateOfBirth");
    }};


    /**
     * Normalizes acronyms in content (imports, class references, method calls, etc.).
     * Preserves SCREAMING_SNAKE_CASE enum constants and acronyms within comments.
     * Examples: GetFCMRiskLimits -> GetFcmRiskLimits
     *           But: FCM_POSITION_SIDE_UNSPECIFIED stays FCM_POSITION_SIDE_UNSPECIFIED
     *           And: "intermediary VASP" in comments stays "intermediary VASP"
     */
    private String normalizeAcronymsInContent(String content) {
        // List of known acronyms that should be converted to PascalCase
        Map<String, String> acronymMap = new LinkedHashMap<>();
        acronymMap.put("FCM", "Fcm");
        acronymMap.put("PM", "Pm");
        acronymMap.put("RFQ", "Rfq");
        acronymMap.put("NFT", "Nft");
        acronymMap.put("EVM", "Evm");
        acronymMap.put("VASP", "Vasp");
        acronymMap.put("TF", "Tf");

        // Step 1: Extract and replace comments with placeholders to preserve them
        List<String> preservedComments = new ArrayList<>();
        int commentIndex = 0;

        // Pattern to match all comment types: //, /* */, and /** */
        Pattern commentPattern = Pattern.compile(
            "//.*?$|/\\*.*?\\*/",
            Pattern.MULTILINE | Pattern.DOTALL
        );

        Matcher commentMatcher = commentPattern.matcher(content);
        StringBuffer contentWithPlaceholders = new StringBuffer();

        while (commentMatcher.find()) {
            String comment = commentMatcher.group();
            preservedComments.add(comment);
            commentMatcher.appendReplacement(contentWithPlaceholders,
                "___COMMENT_PLACEHOLDER_" + commentIndex + "___");
            commentIndex++;
        }
        commentMatcher.appendTail(contentWithPlaceholders);

        // Step 2: Perform acronym normalization on non-comment code
        String result = contentWithPlaceholders.toString();
        for (Map.Entry<String, String> entry : acronymMap.entrySet()) {
            String acronym = entry.getKey();
            String normalized = entry.getValue();

            // Replace acronym in various contexts:
            // 1. Class names: GetFCMRiskLimits -> GetFcmRiskLimits
            // 2. Import statements: import ...GetFCMRiskLimits -> import ...GetFcmRiskLimits
            // 3. Type references: GetFCMRiskLimitsResponse -> GetFcmRiskLimitsResponse
            // 4. Method names: getFCMMarginCall -> getFcmMarginCall
            // 5. Standalone type names: private VASP vasp -> private Vasp vasp
            // BUT: preserve SCREAMING_SNAKE_CASE enum constants like FCM_POSITION_SIDE_UNSPECIFIED
            // AND: preserve enum values like "CBE", "FCM" (standalone on their own line)

            // Pattern 1: Match acronym when followed by uppercase letter (word boundary before)
            // But NOT if it's part of a SCREAMING_SNAKE_CASE identifier (followed by underscore)
            result = result.replaceAll("\\b" + acronym + "(?=[A-Z](?!_))", normalized);

            // Pattern 2: Match acronym as standalone type (followed by lowercase identifier)
            // This catches: "private VASP vasp", "VASP getVasp()", etc.
            // But NOT enum values (standalone on their own line)
            result = result.replaceAll("\\b" + acronym + "(?=[\\s]+[a-z])", normalized);

            // Pattern 3: Match acronym in generics, method calls, and imports
            // This catches: "List<VASP>", "new VASP(", "import ...VASP;", etc.
            // But NOT enum values (which are typically just "FCM," or "FCM\n")
            result = result.replaceAll("\\b" + acronym + "(?=[\\(\\)<>;])", normalized);

            // Pattern 4: Match acronym at end of identifier name (before .)
            // This catches: "VASP.class", but avoids enum constants with underscores
            result = result.replaceAll("\\b" + acronym + "(?=\\.)", normalized);
        }

        // Step 3: Restore original comments with acronyms preserved
        for (int i = 0; i < preservedComments.size(); i++) {
            result = result.replace("___COMMENT_PLACEHOLDER_" + i + "___", preservedComments.get(i));
        }

        return result;
    }

    /**
     * Normalizes acronyms in class names to use PascalCase.
     * Examples: FCMMarginCall -> FcmMarginCall
     */
    private String normalizeAcronyms(String className) {
        // List of known acronyms that should be converted to PascalCase
        // Using LinkedHashMap to control replacement order (longer acronyms first)
        Map<String, String> acronymMap = new LinkedHashMap<>();
        acronymMap.put("FCM", "Fcm");
        acronymMap.put("PM", "Pm");
        acronymMap.put("RFQ", "Rfq");
        acronymMap.put("NFT", "Nft");
        acronymMap.put("EVM", "Evm");
        acronymMap.put("VASP", "Vasp");

        String result = className;
        for (Map.Entry<String, String> entry : acronymMap.entrySet()) {
            String acronym = entry.getKey();
            String normalized = entry.getValue();

            // Replace acronym when:
            // 1. At the start followed by uppercase letter (FCMMarginCall -> FcmMarginCall)
            // 2. At the end of the string (just FCM -> Fcm)
            // 3. In the middle followed by uppercase (EntityFCMBalance -> EntityFcmBalance)

            // Use word boundary and lookahead to ensure we only replace the acronym part
            result = result.replaceAll("\\b" + acronym + "(?=[A-Z])", normalized);

            // Handle end of string
            if (result.endsWith(acronym)) {
                result = result.substring(0, result.length() - acronym.length()) + normalized;
            }
        }

        return result;
    }

    /**
     * Apply file path replacements to strip common prefixes from class names.
     * Matches prime-sdk-ts filePathReplacements behavior.
     */
    private static String stripCommonPrefixes(String className) {
        String result = className;

        // Apply replacements in order (LinkedHashMap maintains insertion order)
        for (Map.Entry<String, String> entry : FILE_PATH_REPLACEMENTS.entrySet()) {
            if (result.contains(entry.getKey())) {
                result = result.replace(entry.getKey(), entry.getValue());
            }
        }

        // Normalize acronyms to PascalCase after prefix stripping
        result = normalizeAcronymsForClassName(result);

        return result;
    }

    private static String normalizeAcronymsForClassName(String className) {
        Map<String, String> acronymMap = new LinkedHashMap<>();
        acronymMap.put("FCM", "Fcm");
        acronymMap.put("PM", "Pm");
        acronymMap.put("RFQ", "Rfq");
        acronymMap.put("NFT", "Nft");
        acronymMap.put("EVM", "Evm");
        acronymMap.put("VASP", "Vasp");
        acronymMap.put("TF", "Tf");

        String result = className;
        for (Map.Entry<String, String> entry : acronymMap.entrySet()) {
            result = result.replaceAll("\\b" + entry.getKey() + "(?=[A-Z])", entry.getValue());
        }
        return result;
    }

    /**
     * Apply content replacements to all files to strip prefixes from type references.
     * Matches prime-sdk-ts replaceString() behavior - uses split/join like TS.
     */
    private String applyContentReplacements(String content) {
        // Apply content replacements (matching TS replacements object)
        // Uses String.replace() which replaces ALL occurrences (like TS split().join())
        for (Map.Entry<String, String> entry : CONTENT_REPLACEMENTS.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();

            // Simple string replacement - replaces all occurrences
            content = content.replace(key, value);
        }

        // Apply acronym normalization to content (class references, imports, etc.)
        content = normalizeAcronymsInContent(content);

        return content;
    }

    private String applyBooleanPrimitiveConversion(String content) {
        // Field declarations: private Boolean foo; -> private boolean foo;
        content = content.replaceAll("\\bprivate Boolean (\\w)", "private boolean $1");
        // Getter return types: public Boolean getFoo() -> public boolean getFoo()
        content = content.replaceAll("\\bpublic Boolean (get|is)(\\w)", "public boolean $1$2");
        // Setter and builder method params: (Boolean foo) -> (boolean foo)
        content = content.replaceAll("\\(Boolean (\\w)", "(boolean $1");
        return content;
    }

    private String removeOpenApiGeneratorAttribution(String content) {
        return content.replaceAll(
            "(?m)^ \\* This file is generated by Openapi Generator https://github\\.com/openapitools/openapi-generator\\s*\\n",
            "");
    }

    private String applyDateOfBirthPrimitiveConversion(String content) {
        content = content.replaceAll("\\bprivate Integer (year|month|day)\\b", "private int $1");
        content = content.replaceAll("\\bpublic Integer (getYear|getMonth|getDay)\\b", "public int $1");
        content = content.replaceAll("\\bvoid set(Year|Month|Day)\\(Integer ", "void set$1(int ");
        content = content.replaceAll("\\bprivate Integer (year|month|day)\\b", "private int $1");
        content = content.replaceAll("\\bBuilder (year|month|day)\\(Integer ", "Builder $1(int ");
        return content;
    }

    /**
     * Removes {@code @JsonProperty} when the wire name matches the Java field name.
     * Jackson maps by field name by default; annotations are only needed for snake_case wire names.
     */
    private String removeRedundantJsonProperty(String content) {
        Pattern pattern = Pattern.compile(
            "    @JsonProperty\\(\"([^\"]+)\"\\)\\s*\\n"
                + "(\\s*private\\s+[\\w<>,\\?\\[\\]\\.\\s]+\\s+)(\\w+)(\\s*;)",
            Pattern.MULTILINE
        );
        Matcher matcher = pattern.matcher(content);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            String wireName = matcher.group(1);
            String fieldPrefix = matcher.group(2);
            String fieldName = matcher.group(3);
            String suffix = matcher.group(4);
            if (wireName.equals(fieldName)) {
                matcher.appendReplacement(sb, Matcher.quoteReplacement(fieldPrefix + fieldName + suffix));
            }
        }
        matcher.appendTail(sb);
        content = sb.toString();

        if (!content.contains("@JsonProperty(")) {
            content = content.replaceAll(
                "import com\\.fasterxml\\.jackson\\.annotation\\.JsonProperty;\\s*\\n",
                ""
            );
        }
        return content;
    }

    /** Decodes HTML entities in generated Javadoc (OpenAPI / prior escaping). */
    static String decodeHtmlEntities(String content) {
        return content
            .replace("&amp;", "&")
            .replace("&lt;", "<")
            .replace("&gt;", ">")
            .replace("&#39;", "'")
            .replace("&apos;", "'")
            .replace("&#x60;", "`")
            .replace("&quot;", "\"");
    }

    /** Drops import lines whose types are not referenced in the generated class body. */
    private String removeUnusedImports(String content) {
        Matcher bodyMatcher = Pattern.compile("^public (?:class|enum) ", Pattern.MULTILINE).matcher(content);
        if (!bodyMatcher.find()) {
            return content;
        }
        int bodyStart = bodyMatcher.start();
        String body = content.substring(bodyStart);

        Pattern importPattern = Pattern.compile("^import ([\\w.]+);\\r?\\n", Pattern.MULTILINE);
        Matcher importMatcher = importPattern.matcher(content.substring(0, bodyStart));
        StringBuilder header = new StringBuilder();
        int lastEnd = 0;
        while (importMatcher.find()) {
            header.append(content, lastEnd, importMatcher.start());
            String fqcn = importMatcher.group(1);
            String simpleName = fqcn.substring(fqcn.lastIndexOf('.') + 1);
            Pattern usePattern = Pattern.compile("\\b" + Pattern.quote(simpleName) + "\\b");
            if (usePattern.matcher(body).find()) {
                header.append(importMatcher.group(0));
            }
            lastEnd = importMatcher.end();
        }
        header.append(content, lastEnd, bodyStart);
        return header.toString() + body;
    }

    private Path findProjectRoot() {
        Path current = outputDir;
        while (current != null) {
            if (Files.exists(current.resolve("pom.xml")) &&
                Files.exists(current.resolve("apiSpec/prime-public-spec.yaml"))) {
                return current;
            }
            current = current.getParent();
        }
        throw new RuntimeException("Could not find project root");
    }
}
