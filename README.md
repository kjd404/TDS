# TDS

## Prerequisites
- JDK 17 or newer
- Gradle wrapper scripts target Gradle 8.9. If `gradle/wrapper/gradle-wrapper.jar` is missing, generate it with:

```bash
gradle wrapper --gradle-version 8.9
```

Verify with:

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

## Development
Game code that needs frame timing or screen dimensions should depend on the `GraphicsContext` interface rather than accessing `Gdx.graphics` directly. The `core` module provides a `GdxGraphicsContext` implementation and tests can use `FakeGraphicsContext` to control values.

Textures should be loaded through LibGDX's `AssetManager` instead of instantiating `Texture` directly. Projectile and bullet logic is implemented via the `ParticleSystem` interface, with instances created through `ParticleSystemFactory`.

Screens are constructed through `ScreenFactory` (used by `MenuScreen`) to decouple creation from usage.

## macOS notes
- Ensure the Gradle wrapper is executable:

```bash
chmod +x gradlew
```

- The desktop client may require running on the first thread:

```bash
./gradlew desktop:run -Dorg.gradle.jvmargs=-XstartOnFirstThread
```
