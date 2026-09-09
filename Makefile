.PHONY: fetch-spec compile format generate check-generated generate-live-diff

fetch-spec:
	mvn -B -f tools/model-generator/pom.xml compile exec:java@generate-models -Dexec.args="--fetch-spec"

compile:
	mvn -B compile

format:
	mvn -B spotless:apply

generate:
	mvn -B -f tools/model-generator/pom.xml compile exec:java@generate-models

check-generated:
	mvn -B -f tools/model-generator/pom.xml compile exec:java@generate-models -Dexec.args="--check"

generate-live-diff:
	mvn -B -f tools/model-generator/pom.xml compile exec:java@generate-models -Dexec.args="--live-diff"
