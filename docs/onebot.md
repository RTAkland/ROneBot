# ROneBot-onebot-v11

这里是OneBotV11 SDK的帮助文档

# 开始使用

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
    // 这里的版本替换成最新版本
    implementation("cn.rtast:ronebot-onebot-v11:${version}")
}
```

> 替换成最新版本, 最新版本可以在Gitlab的Maven仓库查看, [这里](https://repo.rtast.cn/RTAkland/ronebot/-/packages)是
> 所有版本的Maven仓库地址尽量使用最新版进行开发~

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

> ~~***注意***: 内置指令管理器使用空格来匹配第一个命令是否匹配,
> 执行所有命令都需要再命令名后加上一个空格才能被正确的执行
> 如果你有其他想法可以提交PR让框架直接实现你的想法让所有人都可以用
> 或者自行实现一个命令管理器~~

> ***注意: 原本的自带权限的命令管理器已被废弃, 且不可用***

```kotlin
/**
 * 带上匹配模式注解可以指定使用什么模式来匹配
 * 如果不写注解或者将注解设置成[MatchingStrategy.SPACES]
 * 的话默认使用空格来分割命令
 */
@CommandMatchingStrategy(MatchingStrategy.REGEX)
class EchoCommand : BaseCommand() {
    override val commandNames = listOf("/echo", "/eee")  // 指令别名写法
    override suspend fun executeGroup(listener: OneBotListener, message: GroupMessage, args: List<String>) {
        // args 参数默认去除掉了指令部分, 如果一条消息是 "/echo 114514 1919810" 那么args就是
        // listOf("114514", "1919810")
        message.action.sendGroupMessage(message.groupId, args.joinToString(" "))
    }
}

fun main() {
    val fancyBot = FancyBot()
    val instance1 = ROneBotFactory.createServer(6760, fancyBot)
    val commands = listOf<BaseCommand>(
        EchoCommand()
    )
    commands.forEach { ROneBotFactory.commandManager.register(it) }
}
```

# 消息构造器

## 链式调用构造消息

> 框架支持使用链式调用的方式构造一个消息, 以下是一个简单的示例, 也可以直接使用字符串的形式发送

```kotlin
val msgChain = MessageChain.Builder()
    .addAt(message.sender.userId)
    .addText(message.rawMessage)
    .addNewLine(3)  // 添加3个换行符到末尾
    .build()
this.sendGroupMessage(message.groupId, msgChain)
```

## 操作符重载构造消息

> 由于一些奇怪的特性, 直接使用 `+` 操作符对两个对象进行操作时
> IDE并不会有代码补全的提示, 也不会自动进行导入, 所以在某些情况下
> 你需要使用`XXX().plus`的形式进行导入这个拓展操作符

下面是示例代码

```kotlin
fun main() {
    // 可以直接对一个链式调用构造的消息使用操作符重载
    val chain = MessageChain.Builder().addText("1").build() + Text("")
    println(chain)
    val operator = AT(3458671395) +
            Text("114514") +
            Image("https://example.com/example.png") +
            Reply(114514L) +
            Face(666) +
            NewLine()
    println(operator)
}
```

# 消息即对象

> 你可以对一个消息对象执行某些操作例如 撤回(revoke/recall) 回复(reply)

```kotlin
fun main() {
    ROneBotFactory.createClient("ws://127.0.0.1:6666", "1145141919810", object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
            message.revoke(10)  // 延迟10秒后撤回这条消息
            // 你可以在任何事件回调接口中访问`action`这个对象, 例如下面的示例
            println(message.action.getLoginInfo())
        }
    })
}
```

# 内置任务调度器

> 你可以创建自己的任务并设置延迟时间和循环周期

```kotlin
fun main() {
    val instance1 = ROneBotFactory.createClient("ws://127.0.0.1:6666", "1145141919810", object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
            message.revoke(10)  // 延迟10秒后撤回这条消息
        }
    })
    instance1.scheduler.scheduleTask({
        println(it.action.getLoginInfo())
    }, 0, 1000)
    taskHandle.cancel()  // 取消这个任务
    instance1.scheduler.cancelTask(taskHandle)  // 或者这样写
}
```

# Deferred对象操作

> 虽然Websocket所有操作都是异步执行, 但是得益于Kotlin协程的`CompletableDeferred<T>>()`
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