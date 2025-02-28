/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.annotations.command.functional.GroupCommandHandler
import cn.rtast.rob.annotations.command.functional.PrivateCommandHandler
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.custom.IWebsocketErrorEvent
import cn.rtast.rob.entity.text
import cn.rtast.rob.event.events.GroupMessageEvent
import cn.rtast.rob.event.onEvent
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.onebot.dsl.messageChain
import cn.rtast.rob.onebot.dsl.text
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
    ACommand(), TestSession()
)

class ACommand : BaseCommand() {
    override val commandNames = listOf("/1")

    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        println("call")
    }

    override suspend fun executePrivate(message: PrivateMessage, args: List<String>) {
    }
}

@GroupCommandHandler(["/test"], )
suspend fun testCommand(message: GroupMessage) {
    println(message)
}

@PrivateCommandHandler(["/t1"])
suspend fun privateCommand(message: PrivateMessage) {
    println(message)
}

class TestSession : BaseCommand() {
    override val commandNames = listOf("/session")

    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        if (message.text.contains("1")) {
            message.reply("继续输入")
            message.startSession()
        }
    }

    override suspend fun onGroupSession(msg: GroupMessage) {
        if (msg.text.contains("2")) {
            msg.skipSession()
            msg.reply("设置成功")
        } else {
            msg.reject(messageChain {
                text("请输入2!")
            })
        }
    }
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