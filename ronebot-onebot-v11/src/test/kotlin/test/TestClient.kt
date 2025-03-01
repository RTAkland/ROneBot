/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.annotations.command.functional.GroupCommandHandler
import cn.rtast.rob.annotations.command.functional.PrivateCommandHandler
import cn.rtast.rob.annotations.command.functional.session.GroupSessionHandler
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.entity.text
import cn.rtast.rob.event.events.GroupMessageEvent
import cn.rtast.rob.event.onEvent
import cn.rtast.rob.onebot.dsl.messageChain
import cn.rtast.rob.onebot.dsl.text
import cn.rtast.rob.session.rejectGroupSession
import cn.rtast.rob.session.skipGroupSession
import cn.rtast.rob.session.startGroupSession
import cn.rtast.rob.util.BaseCommand

val commands = listOf(
    EchoCommand(), DelayCommand(), MatchedCommand(),
    ACommand(), TestSession()
)

class ACommand : BaseCommand() {
    override val commandNames = listOf("/1")

    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        message.startSession()
        println("call")
    }

    override suspend fun executePrivate(message: PrivateMessage, args: List<String>) {
        message.startSession()
    }
}

class TestReceiver {

    @GroupSessionHandler
    suspend fun testGroup(message: GroupMessage) {
        println(message.action.getLoginInfo())
        if (!message.text.contains("2")) {
            rejectGroupSession(message, messageChain {
                text("请输入2")
            })
        } else {
            skipGroupSession(message)
            message.reply("设置成功")
        }
    }
}


@GroupCommandHandler(["/test"], TestReceiver::class)
suspend fun testCommand(message: GroupMessage) {
    startGroupSession(message, ::testCommand)
    message.reply("请继续输入2")
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
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken)
    ROneBotFactory.commandManager.registerFunction(::testCommand)
    ROneBotFactory.commandManager.registerFunction(::privateCommand)
    instance1.addListeningGroup(985927054)
    instance1.onEvent<GroupMessageEvent> {
        println(it)
    }
    commands.forEach {
        ROneBotFactory.commandManager.register(it)
    }
}