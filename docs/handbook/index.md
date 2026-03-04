---
icon: lucide/book-open-text
---

# Getting Started

**Multiplatform UI** is a collection of high-quality UI components for Kotlin Multiplatform. This guide will help you integrate the library into your project.

## Supported Platforms

- **Android**
- **iOS**
- **Desktop (JVM)**
- **WasmJs**
- **Js**

## Setup

!!! tip

    [![Maven Central](https://img.shields.io/maven-central/v/com.github.Chen-Xi-g/MultiplatformUI)](https://search.maven.org/search?q=g:ltd.cloudgrid.components)

    **Note**: Replace `<version>` with the latest release version found on Maven Central.

To use Multiplatform UI in your project, follow these steps to configure your build system.

### 1. Add Repository

Ensure `mavenCentral()` is included in your project's repository list. This is typically found in `settings.gradle.kts` or the root `build.gradle.kts`.

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
```

### 2. Add Dependencies

You can include specific modules based on your needs. Add the dependencies to your module's `build.gradle.kts`.

**For Compose Multiplatform Projects (`commonMain`):**

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("ltd.cloudgrid.components:buttons:<version>")
        }
    }
}
```

**For Android-only Projects:**

```kotlin
dependencies {
    implementation("ltd.cloudgrid.components:buttons-android:<version>")
}
```

## Available Modules

The library is modularized so you only import what you use.

| Module | Description | Artifact ID |
| :--- | :--- | :--- |
| **Buttons** | Advanced button interactions (e.g., SwipeButton) | `ltd.cloudgrid.components:buttons` |

## API Documentation

View the [API Documentation](https://chen-xi-g.github.io/MultiplatformUI/dokka/), built with Dokka, that provides complete details for all available APIs.