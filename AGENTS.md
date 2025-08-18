# Codex Agent Instructions

This repository is a small LibGDX project split into `core` and `desktop` modules.

## Building
The Gradle wrapper JAR is not committed. If `gradle/wrapper/gradle-wrapper.jar` is missing, generate it with:

```
gradle wrapper --gradle-version 8.9
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

## Development
- Avoid direct calls to `Gdx.graphics`. Use the `GraphicsContext` interface and inject implementations where needed.
- Tests that require graphics timing or sizing should prefer `FakeGraphicsContext` from the `core/test` package.
