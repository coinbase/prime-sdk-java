# Coinbase Prime Java SDK — Deploy

Canonical repository: [coinbase/prime-sdk-java](https://github.com/coinbase/prime-sdk-java).

This project publishes through the [Sonatype Central Portal](https://central.sonatype.org/publish/publish-portal-maven/) (`central-publishing-maven-plugin` with server id `central`).

## Prerequisites

- JDK 11+
- Maven 3.8+
- GPG key configured (`gpg.keyname` in Maven settings or `pom.xml` properties)
- Sonatype Central user token ([generate in the portal](https://central.sonatype.com/account); use server id `central` in `~/.m2/settings.xml`)

## Publish with GitHub Actions

Creating a [GitHub Release](https://docs.github.com/en/repositories/releasing-projects-on-github/managing-releases-in-a-repository#creating-a-release) runs [`.github/workflows/publish.yml`](.github/workflows/publish.yml). You can also run the workflow manually from **Actions → publish → Run workflow**, providing the release tag (for example `v1.9.0`).

The workflow checks out the release tag, aligns `pom.xml` version with the tag (for example `v1.9.0` → `1.9.0`), runs Spotless and tests, then runs `mvn clean deploy`.

### Trigger manually from the CLI

Use the [GitHub CLI](https://cli.github.com/) (`gh auth login` if needed). From a clone of this repo:

```bash
gh workflow run publish -f tag=v1.9.0
```

From another directory, pass the repository explicitly:

```bash
gh workflow run publish --repo coinbase/prime-sdk-java -f tag=v1.9.0
```

Watch the latest run or list recent publish runs:

```bash
gh run watch
gh run list --workflow=publish
```

### Release environment secrets

Configure these under **Settings → Environments → release → Environment secrets** (the publish job uses the `release` environment):

| Secret | Description |
|--------|-------------|
| `MAVEN_CENTRAL_USERNAME` | Sonatype Central token username |
| `MAVEN_CENTRAL_TOKEN` | Sonatype Central token password |
| `MAVEN_GPG_PRIVATE_KEY` | ASCII-armored GPG secret key (`gpg --armor --export-secret-keys KEY_ID`) |
| `MAVEN_GPG_PASSPHRASE` | Passphrase for that key |
| `MAVEN_GPG_KEY_ID` | GPG key id used by `maven-gpg-plugin` (`gpg.keyname`) |

### Release checklist

1. Bump `<version>` on `main` (or rely on the workflow to set it from the tag).
2. Create and push an annotated tag: `git tag v1.9.0 && git push origin v1.9.0`
3. Create a GitHub Release for that tag (event type **created** triggers publish).
4. Confirm the workflow succeeded and the artifact appears on [Maven Central](https://central.sonatype.com/artifact/com.coinbase.prime/coinbase-prime-sdk-java).
