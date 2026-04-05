---
icon: lucide/book-open-text
---

# 快速开始

**Multiplatform UI** 是一套适用于 Kotlin Multiplatform 的高质量 UI 组件库。本指南将帮助你将库集成到项目中。

## 支持平台

![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-Native-000000?logo=apple&logoColor=white)
![macOS](https://img.shields.io/badge/macOS-Native-000000?logo=apple&logoColor=white)
![Desktop](https://img.shields.io/badge/Desktop-JVM-007396?logo=openjdk&logoColor=white)
![JsCanvas](https://img.shields.io/badge/Web-JsCanvas-F7DF1E?logo=javascript)
![WasmJs](https://img.shields.io/badge/Web-WasmJs-654FF0?logo=webassembly&logoColor=white)

## 集成配置

!!! warning

    [![Maven Central](https://img.shields.io/github/v/release/Chen-Xi-g/MultiplatformUI)](https://search.maven.org/search?q=g:ltd.cloudgrid.components)

    **注意**：请将 `<version>` 替换为 Maven Central 上的最新版本。

在项目中使用 Multiplatform UI，请按照以下步骤配置构建系统。

### 1. 添加仓库

确保项目仓库列表中包含 `mavenCentral()`，通常配置在 `settings.gradle.kts` 或根目录 `build.gradle.kts` 中。

```kotlin
dependencyResolutionManagement {
    repositories {
        mavenCentral()
    }
}
```

### 2. 添加依赖

你可以根据需求按需引入对应模块。在模块的 `build.gradle.kts` 中添加依赖。

**Compose Multiplatform 项目（`commonMain`）：**

```kotlin
kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation("ltd.cloudgrid.components:buttons:<version>") // Button 组件
        }
    }
}
```

**仅 Android 项目：**

```kotlin
dependencies {
    implementation("ltd.cloudgrid.components:buttons-android:<version>") // Button 组件
}
```

## 可用模块

本库采用模块化设计，按需引入即可。

| 模块 | 说明 | 依赖坐标 |
| :--- | :--- | :--- |
| **Buttons** | 按钮交互组件 | `ltd.cloudgrid.components:buttons` |

## API 文档

查看完整的 [API 文档](https://chen-xi-g.github.io/MultiplatformUI/dokka/)，由 Dokka 生成，包含所有可用 API 的详细说明。