欢迎使用ROB！ 这是一个Kotlin的OneBot11 SDK开发框架并且支持多实例!

# 注意事项

1. ROB不是一个OneBot实现, 他的作用是连接到OneBot实现, 然后处理OneBot实现下发的消息。
2. ROB深度依赖于`Kotlinx.Coroutines`(Kotlin协程)所以只能在Kotlin中使用, 不能在Java中使用。
3. ROB是一个JVM平台的SDK不是KMP平台的SDK
4. ROB所需的最低JDK版本为`11`

# 快速开始

## 添加Maven仓库

```kotlin
repositories {
    maven("https://repo.maven.rtast.cn/releases/")
}
```

## 添加依赖

```kotlin
dependencies {
    // 这里的版本替换成最新版本
    implementation("cn.rtast:ronebot-onebot-v11:${version}")
}
```

# 最小实例

```kotlin
fun main() {
    OneBotFactory.createClient("ws://127.0.0.1:6666", "1145141919810", object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
            println(message.rawMessage)
        }
    })
}
```

# API文档

[APIWiki](https://rob-api.rtast.cn/)

# 加入交流群

[点击这里加群](https://qm.qq.com/q/KrmU7AjzuC)

<img src="https://github.com/user-attachments/assets/eabd622e-f38d-4541-9e27-7d9623a97805" width="200">
