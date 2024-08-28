/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.util.BaseCommand
import cn.rtast.rob.util.ob.OBMessage
import org.java_websocket.WebSocket

class EchoCommand: BaseCommand() {
    override val commandName = "/echo"

    override suspend fun executeGroup(listener: OBMessage, message: GroupMessage, args: List<String>) {
        listener.sendGroupMessage(message.groupId, args.joinToString(" "))
    }
}


fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val rob = ROneBotFactory.createClient(wsAddress, wsAccessToken, object : OBMessage {
        override suspend fun onGroupMessage(websocket: WebSocket, message: GroupMessage, json: String) {
            println(message.rawMessage)
        }
    })
    rob.commandManager.register(EchoCommand())  // not a suspend function
}