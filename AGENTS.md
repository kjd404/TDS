# Codex Agent Instructions

This repository is a small LibGDX project split into `core` and `desktop` modules.

## Building
The Gradle wrapper JAR is not committed. If `gradle/wrapper/gradle-wrapper.jar` is missing, generate it with:

```
gradle wrapper --gradle-version 8.5
```

Then use the wrapper:

```
./gradlew build
```

Whenever build steps or developer-facing commands change, verify and update `README.md` accordingly.

## Running
The desktop application can be started with:

```
./gradlew desktop:run
```

## Testing
This project contains unit tests. Run them with:

```
./gradlew test
```
