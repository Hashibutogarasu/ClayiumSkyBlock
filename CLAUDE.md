# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

ClayiumSkyBlock is a Minecraft 1.7.10 Forge mod built with the GTNHConvention build system (RetroFuturaGradle).

- **modId**: `clayiumskyblock`
- **modGroup**: `com.karasu256.clayiumskyblock`
- **Main class**: `com.karasu256.clayiumskyblock.ClayiumSkyBlock`

## Build Commands

```bash
./gradlew build                  # Build the mod jar
./gradlew spotlessApply          # Auto-fix code formatting (run before committing)
./gradlew spotlessCheck          # Check for formatting violations
./gradlew setupDecompWorkspace   # First-time setup (decompiles Minecraft sources)
```

## Runtime Requirements

Minecraft 1.7.10 requires **Java 8** — Java 9+ breaks `URLClassLoader` used by launchwrapper.

Set `JAVA_HOME` to a Java 8 JDK before running from the IDE or Gradle. `addon.gradle` reads `JAVA_HOME` and applies it as the executable for all `JavaExec` tasks (including the run tasks).

## File Roles

| File | Purpose |
|---|---|
| `gradle.properties` | Central configuration: mod name, ID, package, etc. |
| `dependencies.gradle` | Add mod dependencies (CurseMaven, etc.) |
| `repositories.gradle` | Add extra Maven repositories |
| `addon.gradle` | Custom build logic — one of the few files safe to edit |
| `build.gradle.kts` | Applies the GTNHConvention plugin only. **Do not edit.** |

## Adding Dependencies

CurseMaven is auto-registered via `includeWellKnownRepositories = true`:
```groovy
// dependencies.gradle
implementation(rfg.deobf("curse.maven:<slug>-<projectId>:<fileId>"))
```

Obfuscated jars must be wrapped with `rfg.deobf()` to deobfuscate them for the dev environment.

## Code Formatting

Spotless (Google Java Format) runs automatically during builds via `spotlessCheck`. A formatting violation will fail the build. Always run `./gradlew spotlessApply` after editing Java files.

## Coding Rules

JavaDoc is required on all public classes and methods, written in English. Inline comments and section-divider comments are prohibited.

**Good:**
```java
/**
 * Registers all recipes added by this mod.
 *
 * @param event the initialization event
 */
public void registerRecipes(FMLInitializationEvent event) {
    GameRegistry.addSmelting(Items.clay_ball, new ItemStack(Items.brick), 0.3f);
}
```

**Bad:**
```java
// Register recipes
public void registerRecipes(FMLInitializationEvent event) {
    // Add clay smelting
    GameRegistry.addSmelting(Items.clay_ball, new ItemStack(Items.brick), 0.3f);
}
```

```java
// ========== Recipe Registration ==========
public void registerRecipes(FMLInitializationEvent event) { ... }
```

## Commits and Pull Requests

- Use [Conventional Commits](https://www.conventionalcommits.org/) format: `type: message` (e.g. `feat: add clay furnace recipe`)
- **Never use scopes** — `feat(auth): ...` style is not allowed
- Commit messages must always be written in **English**
- PR titles follow the same format and must also be in English

## Package Naming

Never change the `modGroup` / package without explicit instruction from the user. If a package name is unspecified, ask before proceeding.
