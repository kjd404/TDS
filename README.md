# TDS

## Prerequisites
- JDK 17 or newer
- This project ships with a Gradle wrapper (version 8.5). Verify with:

```bash
./gradlew --version
```

## Build
```bash
./gradlew build
```

## Test
```bash
./gradlew test
```

## Run
```bash
./gradlew desktop:run
```

## macOS notes
- Ensure the Gradle wrapper is executable:

```bash
chmod +x gradlew
```

- The desktop client may require running on the first thread:

```bash
./gradlew desktop:run -Dorg.gradle.jvmargs=-XstartOnFirstThread
```
