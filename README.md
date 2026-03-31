<div align="center">
 <img src="docs/handbook/images/logo.png" width="100px"/>
</div>

<h1 align="center">Multiplatform UI</h1>

> [!Important]
> 本项目正在积极开发中。
>
> 非常欢迎提交 **问题反馈、建议与拉取请求**。
>
> 如果你愿意帮助完善本库，欢迎随时提交 PR。

**Multiplatform UI** 是一个基于 **Compose Multiplatform** 构建的开源 UI 组件库。

我们的核心目标是提供常用的**高级组件**。借助 Multiplatform UI，开发者
无需复杂配置，即可在 **Android**、**iOS**、**桌面端（JVM）** 和 **Web（Wasm/JS）**
平台上轻松、快速地使用这些组件。

[![Maven Central](https://img.shields.io/github/v/release/Chen-Xi-g/MultiplatformUI)](https://search.maven.org/search?q=g:ltd.cloudgrid.components)
[![Kotlin](https://img.shields.io/badge/kotlin-2.3.10-7F52FF)](https://kotlinlang.org/)
[![Compose Multiplatform](https://img.shields.io/badge/compose-1.10.0-4285F4)](https://www.jetbrains.com/compose-multiplatform)
[![License](https://img.shields.io/github/license/Chen-Xi-g/MultiplatformUI)](LICENSE)

### 支持平台

![Android](https://img.shields.io/badge/Android-3DDC84?logo=android&logoColor=white)
![iOS](https://img.shields.io/badge/iOS-Native-000000?logo=apple&logoColor=white)
![macOS](https://img.shields.io/badge/macOS-Native-000000?logo=apple&logoColor=white)
![Desktop](https://img.shields.io/badge/Desktop-JVM-007396?logo=openjdk&logoColor=white)
![JsCanvas](https://img.shields.io/badge/Web-JsCanvas-F7DF1E?logo=javascript)
![WasmJs](https://img.shields.io/badge/Web-WasmJs-654FF0?logo=webassembly&logoColor=white)

## 📚 文档

深入阅读我们完善的指南与可交互组件库，加速你的开发流程。

- [👉 查看完整文档](https://chen-xi-g.github.io/MultiplatformUI/) - 开发指南与可交互组件库
- [🔍 查阅 API 参考](https://chen-xi-g.github.io/MultiplatformUI/dokka/) - 自动生成的 API 文档（Dokka）

## 📂 项目结构

| 模块                          | 说明                        |
|:------------------------------|:--------------------------|
| **`shared`**                  | 示例应用的核心逻辑与共享 UI，包含组件使用示例。 |
| **`app`**                     | 各平台示例应用的入口与配置。            |
| &nbsp;&nbsp; ├─ `androidApp`  | Android 应用入口。             |
| &nbsp;&nbsp; ├─ `iosApp`      | iOS 应用（Xcode 项目）。         |
| &nbsp;&nbsp; ├─ `desktopApp`  | 桌面端（JVM）应用入口。             |
| &nbsp;&nbsp; └─ `webApp`      | Web（Wasm/JS）应用入口。         |
| **`components`**              | **核心组件库**。                |
| **`docs`**                    | 文档网站源码（Zensical）。         |
| **`build-logic`**             | 自定义 Gradle 插件与构建配置。       |

## 📦 组件库集成

本项目采用模块化设计，`components` 模块为核心组件库所在位置。

### 目前已支持组件：

- **按钮类**：
    - `SwipeButton`：支持滑动确认操作的按钮，常用于支付或重要操作确认场景。

## 📄 许可证

本项目基于 [MIT 许可证](LICENSE) 开源。

## Star 趋势

[![Star History Chart](https://api.star-history.com/svg?repos=Chen-Xi-g/MultiplatformUI&type=date&legend=top-left)](https://www.star-history.com/#Chen-Xi-g/MultiplatformUI&type=date&legend=top-left)