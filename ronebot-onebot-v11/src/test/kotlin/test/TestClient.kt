/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.util.BaseCommand
import cn.rtast.rob.util.ID

val commands = listOf(
    EchoCommand(), DelayCommand(), MatchedCommand(),
    ACommand()
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

suspend fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken)
    println(ROneBotFactory.botManager[1845464277.ID]?.action?.getLoginInfo())
    instance1.addListeningGroup(985927054)
    commands.forEach {
        ROneBotFactory.commandManager.register(it)
    }
}