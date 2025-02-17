<div align="center">

<img src="https://static.rtast.cn/static/icon/rob.png" alt="ROBICON" width="110">

<h4>ROneBot -- OneBot11协程SDK</h4>

<h3>Made By <a href="https://github.com/RTAkland">RTAkland</a></h3>

<img src="https://static.rtast.cn/static/kotlin/made-with-kotlin.svg" alt="MadeWithKotlin">

<br>
<img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/RTAkland/ROneBot/main.yml">
<img alt="Kotlin Version" src="https://img.shields.io/badge/Kotlin-2.1.0-pink?logo=kotlin">
<img alt="GitHub" src="https://img.shields.io/github/license/RTAkland/ROneBot?logo=apache">

</div>

# 概述

这是一个OneBot11协程SDK, 主要接入OneBot11协议, 计划支持其他主流协议(WIP)
现在可以处理绝大部分的输入输出,
你可以点击[这里](ronebot-onebot-v11/src/main/kotlin/cn/rtast/rob/onebot/OneBotListener.kt)
来查看支持哪些输入. 点击[这里](ronebot-onebot-v11/src/main/kotlin/cn/rtast/rob/onebot/OneBotAction.kt)查看支持哪些输出

> 框架支持使用`正向`和`反向`Websocket并且内置了`MessageCommand` 也就是`命令`你可以快速的注册一个命令而不需要重复造轮子

> 最低JDK版本为 `11`

> [这里](https://repo.rtast.cn/RTAkland/FancyBot)是使用此框架的官方示例机器人, 包含了**50+**
> 种命令([Github](https://github.com/RTAkland/FancyBot))

# 文档

[https://docs.rtast.cn/#/docs/ronebot/README](https://docs.rtast.cn/#/docs/ronebot/README)

# Java不友好(?)

> 只能用Kotlin来使用ROneBot哦~

# 多实例

> ROneBot已经全面迁移到2.x版本并支持多实例!

# 开源

- 本项目以[Apache-2.0](./LICENSE)许可开源, 即:
    - 你可以直接使用该项目提供的功能, 无需任何授权
    - 你可以在**注明来源版权信息**的情况下对源代码进行任意分发和修改以及衍生

# 鸣谢

<div>

<img src="https://resources.jetbrains.com/storage/products/company/brand/logos/jetbrains.png" alt="JetBrainsIcon" width="128">

<a href="https://www.jetbrains.com/opensource/"><code>JetBrains Open Source</code></a> 提供的强大IDE支持

</div>