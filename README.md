<div align="center">

<img src="./assets/logo-icon-light.svg" alt="ROBICON" width="90">

<h4>ROneBot: Milky & OneBot11 协程SDK</h4>

#### 已支持的平台

<img src="https://img.shields.io/badge/Platform-JVM11+-yellow.svg?logo=openjdk&logoColor=yellow" alt="Badge JVM" />
<img src="https://img.shields.io/badge/Platform-LinuxX64/LinuxArmX64-8A2BE2.svg?logo=linux&logoColor=8A2BE2" alt="linux" />
<img src="https://img.shields.io/badge/Platform-MacOsArmX64-white.svg?logo=apple&logoColor=white" alt="linux" />
<img src="https://custom-icon-badges.demolab.com/badge/Platform-MinGWX64-0078D6?logo=windows11&logoColor=blue" alt="mingw" />
<img src="https://custom-icon-badges.demolab.com/badge/Platform-Cloudflare Workers(alpha)-F38020?logo=cloudflare" alt="cloudflare worker" />

#### 对接协议

<img src="https://img.shields.io/badge/🥛Milky-Stable-green" alt="Badge Milky" />
<img src="https://img.shields.io/badge/OneBot11-Stable-green?logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAHAAAABwCAMAAADxPgR5AAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAAxQTFRF////29vbr6+vAAAAk1hCcwAAAAR0Uk5T////AEAqqfQAAAKcSURBVHja7NrbctswDATQXfD//zlpO7FlmwAWIOnOtNaTM5JwDMa8E+PNFz7g3waJ24fviyDPgfhz8fHP39cBcBL9KoJbQUxjA2iYqHL3FAnvzhL4GtVNUcoSZe6eSHizBcK5LL7dBr2AUZlev1ARRHCljzRALIEog6H3U6bCIyqIZdAT0eBuJYaGiJaHSjmkYIZd+qSGWAQnIaz2OArVnX6vrItQvbhZJtVGB5qX9wKqCMkb9W7aexfCO/rwQRBzsDIsYx4AOz0nhAtWu7bqkEQBO0Pr+Ftjt5fFCUEbm0Sbgdu8WSgJ5NgH2iu46R/o1UcBXJsFusWF/QUaz3RwJMEgngfaGGdSxJkE/Yg4lOBryBiMwvAhZrVMUUvwqU7F05b5WLaUIN4M4hRocQQRnEedgsn7TZB3UCpRrIJwQfqvGwsg18EnI2uSVNC8t+0QmMXogvbPg/xk+Mnw/6kW/rraUlvqgmFreAA09xW5t0AFlHrQZ3CsgvZm0FbHNKyBmheBKIF2cCA8A600aHPmFtRB1XvMsJAiza7LpPog0UJwccKdzw8rdf8MyN2ePYF896LC5hTzdZqxb6VNXInaupARLDNBWgI8spq4T0Qb5H4vWfPmHo8OyB1ito+AysNNz0oglj1U955sjUN9d41LnrX2D/u7eRwxyOaOpfyevCWbTgDEoilsOnu7zsKhjRCsnD/QzhdkYLBLXjiK4f3UWmcx2M7PO21CKVTH84638NTplt6JIQH0ZwCNuiWAfvuLhdrcOYPVO9eW3A67l7hZtgaY9GZo9AFc6cryjoeFBIWeU+npnk/nLE0OxCHL1eQsc1IciehjpJv5mqCsjeopaH6r15/MrxNnVhu7tmcslay2gO2Z1QfcfX0JMACG41/u0RrI9QAAAABJRU5ErkJggg==" alt="Badge OneBot11" />

</div>

# 概述

> OneBot11模块支持在Cloudflare worker部署Bot, 但仍在早期开发中, 请勿在正式环境中使用

这是一个 `Milky` & `OneBot11` 协程SDK, 主要接入 `Milky` & `OneBot11` 协议并支持多实例, 深度依赖Kotlin协程,
以及Kotlin DSL的特性, 下面是对接Milky协议的示例代码(Kotlin)

```kotlin
val bot = MilkyBotFactory.createBot("http://127.0.0.1:3000", "114514")
// 监听事件
bot.subscribe<GroupMessageEvent> {
    println(it.event.reply("Hello"))
}

// 监听事件的第二种方式
with(bot.listener) {
    onGroupMessage {
        println(it.event.segments.text)
    }
}

// dsl 创建命令
createCommand("/hello", BaseCommand.ExecuteType.Group) {
    println("Hello world")
}.register()

bot.addListeningGroup(123456789)
bot.join()
```

> ROB也为Java用户设计了一系列API

> ROB在任何平台/模块中都`不使用`反射, 以便获取更好的性能

# 使用文档

> 在看文档前请先浏览 [issue17](https://github.com/RTAkland/ROneBot/issues/17)

迫不及待想要使用了吗? 请前往 [使用文档](https://rob.rtast.cn/)

# 贡献指南

想要贡献代码？请查看 [贡献指南](CONTRIBUTING.md)！

# 开源

- 本项目以[Apache-2.0](./LICENSE)许可开源, 即:
    - 你可以直接使用该项目提供的功能, 无需任何授权
    - 你可以在**注明来源版权信息**的情况下对源代码进行任意分发和修改以及衍生
