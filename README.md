<div align="center">

<img src="https://static.rtast.cn/static/icon/rob.png" alt="ROBICON" width="110">

<h4>ROneBot -- 类似于NoneBot的OneBot异步消息处理框架</h4>

<h3>Made By <a href="https://github.com/RTAkland">RTAkland</a></h3>

<img src="https://static.rtast.cn/static/kotlin/made-with-kotlin.svg" alt="MadeWithKotlin">

<br>
<img alt="GitHub Workflow Status" src="https://img.shields.io/github/actions/workflow/status/RTAkland/ROneBot/main.yml">
<img alt="Kotlin Version" src="https://img.shields.io/badge/Kotlin-2.0.0-pink?logo=kotlin">
<img alt="GitHub" src="https://img.shields.io/github/license/RTAkland/ROneBot?logo=apache">

</div>

# 概述

这是一个类似于NoneBot的异步(协程)框架主要接入OneBot11协议,
现在可以处理绝大部分的输入输出,
你可以点击[这里](ronebot-onebot-v11/src/main/kotlin/cn/rtast/rob/onebot/OneBotListener.kt)
来查看支持哪些输入. 点击[这里](ronebot-onebot-v11/src/main/kotlin/cn/rtast/rob/onebot/OneBotAction.kt)查看支持哪些输出

> 框架支持使用`正向`和`反向`Websocket并且内置了`MessageCommand` 也就是`命令`你可以快速的注册一个命令而不需要重复造轮子
> ~~注意: ***内置一个带权限的指令管理器, 权限 -> 群内权限(群主、管理员、成员, 这三种)***~~

> 最低JDK版本为 `11`

> [这里](https://repo.rtast.cn/RTAkland/FancyBot)是使用此框架的官方示例机器人, 包含了**50+**
> 种命令([Github](https://github.com/RTAkland/FancyBot))

# Java不友好

> 此框架深度依赖于Kotlin协程, 在Kotlin中被`suspend`修饰的函数会被隐式的添加一个参数
> `CoroutineContext`这个上下文参数用于控制协程的挂起和恢复, 体现在Java中你需要重写OneBotListener,
> 重写后的代码复杂且不易读,并且Java中没办法调用挂起函数所以使用此框架还是用Kotlin吧~

# 多实例

> ROneBot已经全面迁移到2.x版本并支持多实例!

# 开发

由于Kritor部分的`.proto`文件是通过git子模块引入的所以在clone完仓库本体之后你还需要执行以下命令
来拉取子模块的内容

```shell
$ git submodule update --init --recursive
```

# 注意事项

1. 你只能使用本框架创建一种服务方式, 要么使用`createServer` 要么使用 `createClient` 如果创建了两种会导致无法正常收发消息
2. 框架只能处理数组形式的消息, 如果强制使用CQ码格式的消息将会导致错误抛出

# 开源

- 本项目以[Apache-2.0](./LICENSE)许可开源, 即:
    - 你可以直接使用该项目提供的功能, 无需任何授权
    - 你可以在**注明来源版权信息**的情况下对源代码进行任意分发和修改以及衍生

# 鸣谢

<div>

<img src="https://resources.jetbrains.com/storage/products/company/brand/logos/jetbrains.png" alt="JetBrainsIcon" width="128">

<a href="https://www.jetbrains.com/opensource/"><code>JetBrains Open Source</code></a> 提供的强大IDE支持

</div>