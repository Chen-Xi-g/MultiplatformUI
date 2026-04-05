# SwipeButton

<div style="position: relative;max-width: 100%;height: 350px;border-radius: 10px;overflow: hidden;border: 1px solid #ccc;">
  <iframe src="compose/index.html?component=SwipeButton" style="width: 100%;height: 100%;border: none;display: block;"></iframe>
</div>

## 📦 安装

[![Maven Central](https://img.shields.io/github/v/release/Chen-Xi-g/MultiplatformUI)](https://search.maven.org/search?q=g:ltd.cloudgrid.components)

### Gradle（Kotlin DSL）

```kotlin
dependencies {
    implementation("ltd.cloudgrid.components:buttons:<version>")
}
```

> 请替换 `<version>` 为最新版本

## 🚀 快速开始

### 1️⃣ 创建状态

```kotlin
val swipeState = rememberSwipeButtonState(
    onComplete = {
        // 用户滑动完成
    }
)
```

### 2️⃣ 使用组件

```kotlin
SwipeButton(
    modifier = Modifier
        .fillMaxWidth()
        .height(56.dp),
    state = swipeState,

    background = {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color(0xFF4CAF50))
        )
    },

    content = {
        Text(
            text = "Swipe to confirm",
            color = Color.White
        )
    },

    indicator = {
        Box(
            Modifier
                .fillMaxSize()
                .background(Color.White, CircleShape)
        )
    },

    completedContent = {
        CircularProgressIndicator(color = Color.White)
    }
)
```

## 🧠 使用方式说明

### 滑动完成回调

```kotlin
val swipeState = rememberSwipeButtonState(
    onComplete = {
        // 触发业务逻辑（例如发起请求）
    }
)
```

### 重置按钮（常见）

```kotlin
LaunchedEffect(Unit) {
    swipeState.reset()
}
```

👉 常用于：

* 请求完成后恢复初始状态
* 用户操作取消

### 强制完成 / 重置

```kotlin
swipeState.forceComplete()
swipeState.forceReset()
```

### 自定义触发阈值

```kotlin
val swipeState = rememberSwipeButtonState(
    confirmThreshold = 0.8f
)
```

### 监听滑动进度

```kotlin
val swipeState = rememberSwipeButtonState(
    onProgressChanged = { progress ->
        // 0f ~ 1f
    }
)
```

## 🎨 UI 自定义指南

该组件是 **Headless 设计**，你需要提供 4 个 UI：

| 参数                 | 说明        |
| ------------------ | --------- |
| `background`       | 背景层       |
| `content`          | 文本 / 提示内容 |
| `indicator`        | 可拖动滑块     |
| `completedContent` | 完成态 UI    |

### 示例：进度驱动文本

```kotlin
content = { progress ->
    Text(
        text = if (progress > 0.6f) "Release to confirm" else "Swipe to confirm"
    )
}
```

### 示例：加载态

```kotlin
completedContent = {
    CircularProgressIndicator()
}
```

## ⚙️ 常用配置

### 禁用交互

```kotlin
SwipeButton(
    isEnabled = false
)
```

### 内容是否跟随滑动

```kotlin
SwipeButton(
    isContentFollow = true
)
```

### 动画配置

```kotlin
SwipeButton(
    widthAnimationSpec = tween(500),
    cornerAnimationSpec = tween(500),
    restAnimationSpec = spring(),
    completeAnimationSpec = tween(300),
    resetAnimationSpec = tween(300)
)
```
