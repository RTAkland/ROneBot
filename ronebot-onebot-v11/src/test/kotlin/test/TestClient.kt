/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.annotations.command.GroupCommandHandler
import cn.rtast.rob.annotations.command.PrivateCommandHandler
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.custom.IWebsocketErrorEvent
import cn.rtast.rob.event.events.GroupMessageEvent
import cn.rtast.rob.event.onEvent
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.util.BaseCommand

class TestClient : OneBotListener {

    override suspend fun onGroupMessage(message: GroupMessage, json: String) {
    }

    override suspend fun onWebsocketErrorEvent(event: IWebsocketErrorEvent) {
        event.exception.printStackTrace()
    }
}

val commands = listOf(
    EchoCommand(), DelayCommand(), MatchedCommand(),
    ACommand()
)

class ACommand : BaseCommand() {
    override val commandNames = listOf("/1")

    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        println("call")
    }

    override suspend fun executePrivate(message: PrivateMessage, args: List<String>) {
    }
}

@GroupCommandHandler(["/test"])
suspend fun testCommand(message: GroupMessage) {
    println(message)
}

@PrivateCommandHandler(["/t1"])
suspend fun privateCommand(message: PrivateMessage) {
    println(message)
}

suspend fun main() {
    val client = TestClient()
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken, client).apply {
        println(this)
    }
    ROneBotFactory.commandManager.registerFunction(::testCommand)
    ROneBotFactory.commandManager.registerFunction(::privateCommand)
    instance1.addListeningGroup(985927054)
    instance1.onEvent<GroupMessageEvent> {
        println(it.action.getLoginInfo())
    }
    commands.forEach {
        ROneBotFactory.commandManager.register(it)
    }
}