# Codex Agent Instructions

This repository is a small LibGDX project split into `core` and `desktop` modules.
The build scripts predate Gradle 4 and rely on the deprecated `compile` configuration,
so they fail on recent Gradle releases. There are no unit tests.

## Building
Use a system-installed Gradle and run:

```
gradle build
```

With Gradle 8+ this will fail with `Could not find method compile()`.
If you have access to an older Gradle (around 3.x), the build may succeed.
The bundled `gradlew` script is not executable and also references an outdated
wrapper version, so prefer the system `gradle`.

## Running
The desktop application can be started with:

```
gradle desktop:run
```

This will also fail under modern Gradle for the same reason as `build`.

In short, expect build commands to fail unless using an older Gradle.
