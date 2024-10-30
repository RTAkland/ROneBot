/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.entity.custom.PardonEvent
import cn.rtast.rob.entity.lagrange.PokeEvent
import cn.rtast.rob.util.ob.OneBotAction
import cn.rtast.rob.util.ob.OneBotListener

class TestClient : OneBotListener {
    override suspend fun onGroupMessage(message: GroupMessage, json: String) {
        println(message.action.getMessage(message.messageId).images)
    }

    override suspend fun onWebsocketErrorEvent(action: OneBotAction, ex: Exception) {
        ex.printStackTrace()
    }

    override suspend fun onPardon(event: PardonEvent) {
    }

    override suspend fun onWebsocketOpenEvent(action: OneBotAction) {
    }

    override suspend fun onGroupPoke(event: PokeEvent) {
    }
}

val commands = listOf(
    EchoCommand(), DelayCommand(), MatchedCommand(),
)
val permissionCommands = listOf(
    PCommand()
)

suspend fun main() {
    val client = TestClient()
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken, client)
    instance1.addListeningGroups(985927054)
    commands.forEach { ROneBotFactory.commandManager.register(it) }
    permissionCommands.forEach { ROneBotFactory.commandManager.register(it) }
}