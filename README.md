<div align="center">
 <img src = "docs/handbook/images/logo.png" width = "100px"/>
</div>

<h1 align="center">Multiplatform UI</h1>

> [!IMPORTANT]
> This project is under active development.
> 
> Issues, feedback, and pull requests are **very welcome**.
> 
> Feel free to open a PR if you'd like to help improve this library.

**Multiplatform UI** is an open-source UI component library built on **Compose Multiplatform**.

Our core goal is to provide commonly used **advanced components**. With Multiplatform UI, developers
can easily and quickly use them on **Android**, **iOS**, **Desktop (JVM)**, and **Web (Wasm/JS)**
platforms without complex setup.

[![Maven Central](https://img.shields.io/github/v/release/Chen-Xi-g/MultiplatformUI)](https://search.maven.org/search?q=g:ltd.cloudgrid.components)
[![Kotlin](https://img.shields.io/badge/kotlin-2.3.10-7F52FF)](https://kotlinlang.org/)
[![Compose Multiplatform](https://img.shields.io/badge/compose-1.10.0-4285F4)](https://www.jetbrains.com/compose-multiplatform)
[![License](https://img.shields.io/github/license/Chen-Xi-g/MultiplatformUI)](LICENSE)

### Supported Platforms

![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-Native-000000?logo=apple&logoColor=white)
![macOS](https://img.shields.io/badge/macOS-Native-000000?logo=apple&logoColor=white)
![Desktop](https://img.shields.io/badge/Desktop-JVM-007396?logo=openjdk&logoColor=white)
![JsCanvas](https://img.shields.io/badge/Web-JsCanvas-F7DF1E?logo=javascript)
![WasmJs](https://img.shields.io/badge/Web-WasmJs-654FF0?logo=webassembly&logoColor=white)

## 📚 Documentation

Dive into our comprehensive guides and interactive component library to accelerate your development.

- [👉 View Full Documentation](https://chen-xi-g.github.io/MultiplatformUI/) - Guides and interactive component library
- [🔍 Explore API Reference](https://chen-xi-g.github.io/MultiplatformUI/dokka/) - Auto-generated API docs (Dokka)

## 📂 Project Structure

| Module                       | Description                                                                  |
|:-----------------------------|:-----------------------------------------------------------------------------|
| **`shared`**                 | Core logic and shared UI of the demo app. Contains component usage examples. |
| **`app`**                    | Entry points and configuration for the demo app on each platform.            |
| &nbsp;&nbsp; ├─ `androidApp` | Android application entry point.                                             |
| &nbsp;&nbsp; ├─ `iosApp`     | iOS application (Xcode project).                                             |
| &nbsp;&nbsp; ├─ `desktopApp` | Desktop (JVM) application entry point.                                       |
| &nbsp;&nbsp; └─ `webApp`     | Web (Wasm/JS) application entry point.                                       |
| **`components`**             | **Core component library**.                                                  |
| **`docs`**                   | Documentation website source code (React + Vite).                            |
| **`build-logic`**            | Custom Gradle plugins and build configurations.                              |

## 📦 Component Library Integration

This project adopts a modular design, where `:components` is the location of the core component
library.

### Currently Supported Components:

- **Buttons**:
    - `SwipeButton`: A button that supports swipe-to-confirm operations, commonly used for payments or important operation confirmations.

## 📄 License

This project is licensed under the [MIT License](LICENSE).

## Star History

[![Star History Chart](https://api.star-history.com/svg?repos=Chen-Xi-g/MultiplatformUI&type=date&legend=top-left)](https://www.star-history.com/#Chen-Xi-g/MultiplatformUI&type=date&legend=top-left)
