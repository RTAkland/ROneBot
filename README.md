<div align="center">

<h4>ROneBot -- 类似于NoneBot的OneBot异步消息处理框架</h4>

<h3>Made By <a href="https://github.com/RTAkland">RTAkland</a></h3>

<img src="https://static.rtast.cn/static/kotlin/made-with-kotlin.svg" alt="MadeWithKotlin">

<br>
<img alt="Kotlin Version" src="https://img.shields.io/badge/Kotlin-2.0.0-pink?logo=kotlin">

</div>

# 概述

这是一个类似于NoneBot的异步(协程)框架主要接入OneBot11协议, 现在可以处理绝大部分输入以下是可以处理的事件

```kotlin
interface OBMessage : OBAction {
    suspend fun onWebsocketServerStart() {}
    suspend fun onConnectEvent(websocket: WebSocket, event: ConnectEvent) {}
    suspend fun onHeartBeatMessage(websocket: WebSocket, event: HeartBeatEvent) {}
    suspend fun onMessage(websocket: WebSocket, rawMessage: String) {}
    suspend fun onGroupMessage(websocket: WebSocket, message: GroupMessage, json: String) {}
    suspend fun onPrivateMessage(websocket: WebSocket, message: PrivateMessage, json: String) {}
    suspend fun onInviteMessage(websocket: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    suspend fun onApproveMessage(webSocket: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    suspend fun onLeaveMessage(webSocket: WebSocket, groupId: Long, userId: Long, operator: Long, time: Long) {}
    suspend fun onMemberKick(webSocket: WebSocket, time: Long) {}
    suspend fun onBeKicked(webSocket: WebSocket, time: Long) {}
    suspend fun onWebsocketOpen(websocket: WebSocket) {}
    suspend fun onWebsocketClose(code: Int, reason: String, remote: Boolean) {}
    suspend fun onSetOperator(webSocket: WebSocket, time: Long) {}
    suspend fun onUnsetOperator(webSocket: WebSocket, time: Long) {}
    suspend fun onBan(webSocket: WebSocket, time: Long) {}
    suspend fun onPardon(webSocket: WebSocket, time: Long) {}
    suspend fun onJoinRequest(webSocket: WebSocket, groupId: Long, userId: Long, comment: String, time: Long) {}
    suspend fun onGroupMemberListResponse(webSocket: WebSocket, members: GroupMemberList) {}
    suspend fun onOneBotVersionInfoResponse(webSocket: WebSocket, info: OneBotVersionInfo) {}
    suspend fun onGroupMemberInfoResponse(webSocket: WebSocket, info: GroupMemberInfo) {}
    suspend fun onGroupListResponse(webSocket: WebSocket, groupList: GroupList) {}
    suspend fun onFriendListResponse(webSocket: WebSocket, friendList: FriendList) {}
    suspend fun onStrangerInfoResponse(webSocket: WebSocket, info: StrangerInfo) {}
    suspend fun onLoginInfoResponse(webSocket: WebSocket, info: LoginInfo) {}
    suspend fun onCanSendResponse(webSocket: WebSocket, result: Boolean) {}
}
```

> 框架支持使用`正向`和`反向`Websocket并且内置了`MessageCommand` 也就是`命令`你可以快速的注册一个命令而不需要重复造轮子
> 注意: ***内置的指令管理器并不包含权限控制你需要自行实现权限管理***

> 最低JDK版本为 `11`

# 最小实例

```kotlin
fun main() {
    ROneBotFactory.createClient("ws://127.0.0.1:6666", "1145141919810", object : OBMessage {
        override suspend fun onGroupMessage(websocket: WebSocket, message: GroupMessage, json: String) {
            println(message.rawMessage)
        }
    })
}
```

# 内置指令管理器

```kotlin
class EchoCommand : BaseCommand() {
    override val commandName = "/echo"
    override suspend fun executeGroup(listener: OBMessage, message: GroupMessage, args: List<String>) {
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

# 添加依赖

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

# 开源

- 本项目以[Apache-2.0](./LICENSE)许可开源, 即:
    - 你可以直接使用该项目提供的功能, 无需任何授权
    - 你可以在**注明来源版权信息**的情况下对源代码进行任意分发和修改以及衍生

# 鸣谢

<div>

<img src="https://static.rtast.cn/static/other/jetbrains.png" alt="JetBrainsIcon" width="128">

<a href="https://www.jetbrains.com/opensource/"><code>JetBrains Open Source</code></a> 提供的强大IDE支持

</div>