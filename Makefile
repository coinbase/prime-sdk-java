.PHONY: fetch-spec compile format generate check-generated check-surface generate-live-diff

fetch-spec:
	mvn -B -f tools/model-generator/pom.xml compile exec:java@generate-models -Dexec.args="--fetch-spec"

compile:
	mvn -B compile

format:
	mvn -B spotless:apply

generate:
	mvn -B -f tools/model-generator/pom.xml compile exec:java@generate-models
	mvn -B spotless:apply

check-generated:
	mvn -B -f tools/model-generator/pom.xml compile exec:java@generate-models -Dexec.args="--check"

# Proves generation from the committed spec changes only the files listed in the allowlist.
check-surface:
	mvn -B -f tools/model-generator/pom.xml compile exec:java@generate-models -Dexec.args="--check --expect-changes=tools/model-generator/config/expected-generation-changes.txt"

generate-live-diff:
	mvn -B -f tools/model-generator/pom.xml compile exec:java@generate-models -Dexec.args="--live-diff"
