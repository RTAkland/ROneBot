/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.annotations.command.functional.GroupCommandHandler
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.custom.IWebsocketErrorEvent
import cn.rtast.rob.entity.text
import cn.rtast.rob.event.events.GroupMessageEvent
import cn.rtast.rob.event.onEvent
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.onebot.dsl.messageChain
import cn.rtast.rob.onebot.dsl.text
import cn.rtast.rob.session.functional.createGroupSession

class TestClient : OneBotListener {

    override suspend fun onGroupMessage(message: GroupMessage, json: String) {
    }

    override suspend fun onWebsocketErrorEvent(event: IWebsocketErrorEvent) {
        event.exception.printStackTrace()
    }
}

val commands = listOf(
    EchoCommand(), DelayCommand(), MatchedCommand(),
)

@GroupCommandHandler(["/test"])
suspend fun testCommand(message: GroupMessage) {
    message.reply("继续输入2")
    createGroupSession(message, ::testCommand) {
        if (message.text.contains("2")) {
            skipGroupSession()
        } else {
            rejectGroupSession(messageChain {
                text("输入2")
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
    instance1.addListeningGroup(985927054)
    instance1.onEvent<GroupMessageEvent> {
    }
    commands.forEach {
        ROneBotFactory.commandManager.register(it)
    }
}