.PHONY: fetch-spec compile format
fetch-spec:
	@mkdir -p apiSpec
	@curl -fsSL -o apiSpec/prime-public-spec.yaml https://api.prime.coinbase.com/v1/openapi.yaml

compile:
	mvn -B compile

format:
	mvn -B spotless:apply
