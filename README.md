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
现在可以处理绝大部分的输入输出, 你可以点击[这里](onebot/src/main/kotlin/cn/rtast/rob/util/ob/OneBotListener.kt)
来查看支持哪些输入. 点击[这里](onebot/src/main/kotlin/cn/rtast/rob/util/ob/OneBotAction.kt)查看支持哪些输出

# 多实例 - WIP
> 多实例在 [feature/multi-instance](https://github.com/RTAkland/ROneBot/tree/feature/multi-instance)分支
> 但是由于本人技术有限暂时还没开发完成, 如果你认为你有能力开发请尽情的提交PR或者MR(Merge request)吧~

> 框架支持使用`正向`和`反向`Websocket并且内置了`MessageCommand` 也就是`命令`你可以快速的注册一个命令而不需要重复造轮子
> 注意: ***内置的指令管理器并不包含权限控制你需要自行实现权限管理***

> 最低JDK版本为 `11`

> [这里](https://repo.rtast.cn/RTAkland/FancyBot)是使用此框架的官方示例机器人, 包含了**50+**
> 种命令([Github](https://github.com/RTAkland/FancyBot))

# 如何使用

框架需要使用`java-websocket`, `gson`, `kotlinx.coroutines` 三个库

## 添加Maven仓库

```kotlin
maven {
    name = "repo.rtast.cn"
    url = uri("https://repo.rtast.cn/api/v4/projects/33/packages/maven")
}
```

## 添加依赖

```kotlin
dependencies {
    implementation("cn.rtast:ROneBot:{version}")
}
```

# 最小实例

```kotlin
fun main() {
    ROneBotFactory.createClient("ws://127.0.0.1:6666", "1145141919810", object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
            println(message.rawMessage)
        }
    })
}
```

# 内置指令管理器

> 内置的指令管理器可以处理 `指令别名` 即多个指令名指向一个指令,
> 你可以在[这里](onebot/src/main/kotlin/cn/rtast/rob/util/CommandManager.kt)
> 查看指令别名是如何实现的

> ***注意***: 内置指令管理器使用空格来匹配第一个命令是否匹配,
> 执行所有命令都需要再命令名后加上一个空格才能被正确的执行
> 如果你有其他想法可以提交PR让框架直接实现你的想法让所有人都可以用
> 或者自行实现一个命令管理器

```kotlin
class EchoCommand : BaseCommand() {
    override val commandNames = listOf("/echo", "/eee")  // 指令别名写法
    override suspend fun executeGroup(listener: OneBotListener, message: GroupMessage, args: List<String>) {
        // args 参数默认去除掉了指令部分, 如果一条消息是 "/echo 114514 1919810" 那么args就是
        // listOf("114514", "1919810")
        listener.sendGroupMessage(message.groupId, args.joinToString(" "))
    }
}

fun main() {
    val fancyBot = FancyBot()
    val rob = ROneBotFactory.createServer(6760, fancyBot)
    val commandManager = rob.commandManager
    val commands = listOf<BaseCommand>(
        EchoCommand()
    )
    commands.forEach { commandManager.register(it) }
}
```

# 消息构造器

> 框架支持使用链式调用的方式构造一个消息, 以下是一个简单的示例, 也可以直接使用字符串的形式发送

```kotlin
val msgChain = MessageChain.Builder()
    .addAt(message.sender.userId)
    .addText(message.rawMessage)
    .addNewLine(3)  // repeat 3 times: append 3 \n to end
    .build()
this.sendGroupMessage(message.groupId, msgChain)
```

# 消息即对象

> 你可以对一个消息对象执行某些操作例如 撤回(revoke/recall) 回复(reply)

```kotlin
fun main() {
    ROneBotFactory.createClient("ws://127.0.0.1:6666", "1145141919810", object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
            message.revoke(10)  // 延迟10秒后撤回这条消息
        }
    })
}
```

# 内置任务调度器

> 你可以创建自己的任务并设置延迟时间和循环周期

```kotlin
fun main() {
    val rob = ROneBotFactory.createClient("ws://127.0.0.1:6666", "1145141919810", object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
            message.revoke(10)  // 延迟10秒后撤回这条消息
        }
    })
    // 创建一个挂起的lambda来表示一个任务
    val task = suspend {
        TODO("在这里做任何你想做的事")
        // 你也可以使用ROneBotFactory.action来对Bot进行操作例如如下操作
        println(rob.action.getStatus()) 
        // action是延迟初始化, 在ROB作为客户端连接OneBot实现时会在createClient之后
        // 初始化action, 作为Server时会在启动Websocket服务器之后初始化action
        // 在没有初始化之前任何任务都不会被执行, 初始化完成之后才会执行调度器内的任务
    }
    val taskHandle = rob.scheduler.scheduleTask(task, 1000L, 5000L)  // 延迟1秒启动, 循环周期为5秒
    // 或者这样写
    rob.scheduler.scheduleTask(suspend { TODO() }, 0, 1000)
    taskHandle.cancel()  // 取消这个任务
    rob.scheduler.cancelTask(taskHandle)  // 或者这样写
}
```

# Deferred对象操作

> 虽然这是Websocket所有操作都是异步执行, 但是得益于Kotlin协程的`CompletableDeferred<T>>()`
> 你可以像调用普通函数一样调用一些有返回值的异步操作参考以下例子

```kotlin
fun main() {
    ROneBotFactory.createClient("ws://127.0.0.1:3001", "114514", object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
            val versionInfo = this.getLoginInfo()  // 这里使用了CompletableDeferred来操作
            println(versionInfo)
        }
    })
}
```

# 合理使用反射和注解

> 反射可以在程序运行时反观或修改一个类的结构. 注解可以在不修改类结构的同时为类增加新的行为和属性

> 默认指令管理器只提供了`指令别名`如果你想给一个命令添加一个描述你可以使用注解+反射来实现

假设你将所有的命令存储在了一个list中

```kotlin
// 仅作演示 HelpCommand被定义在下面
val commands = listOf<BaseCommand>(HelpCommand())
```

现在定义一个注解

```kotlin
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class CommandDescription(val description: String)
```

> 这个注解包含了一个`description`属性来提供这个指令的描述

再来定义一个Help指令

```kotlin
@CommandDescription("帮助")
class HelpCommand : BaseCommand() {
    override val commandNames = listOf("/help")

    override suspend fun executeGroup(listener: OneBotListener, message: GroupMessage, args: List<String>) {
        val msg = MessageChain.Builder()
        commands.sortedBy { it::class.simpleName }.forEach {
            // 这里使用反射来获取所有指令的注解, 如果一个类没有被`CommandDescription`注解就设置成`暂无描述`
            val description = if (!it::class.hasAnnotation<CommandDescription>()) "暂无描述"
            else it::class.findAnnotation<CommandDescription>()?.description!!
            // 获取每个命令的类名
            val commandName = it::class.simpleName!!
            // 将每个指令别名合并成一个字符串
            val commandNames = it.commandNames.joinToString(",")
            // 添加到最终的输入消息中
            msg.addText("[$commandName] [$description] 命令: $commandNames").addNewLine()
        }
        msg.addText("共计${commands.size}条命令")
        message.reply(msg.build())
    }
}
```

> 现在发送`/help`就可以看到效果了

***注意*** 反射是一种耗时的操作需要合理的运用反射才能让程序获得更好的性能

如果你还想自定义指令的名称还可以自己新建一个注解来为你的指令添加自定义指令名
只需要修改构造消息的部分就可以完成

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