/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.util.message.MessageChain
import cn.rtast.rob.util.ob.OBMessage
import org.java_websocket.WebSocket


fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val rob = ROneBotFactory.createClient(wsAddress, wsAccessToken, object : OBMessage {
        override suspend fun onGroupMessage(websocket: WebSocket, message: GroupMessage, json: String) {
            val msgChain = MessageChain.Builder()
                .addAt(message.sender.userId)
                .addText(message.rawMessage)
                .addNewLine(3)  // repeat 3 times: append 3 \n to end
                .build()
            this.sendGroupMessage(message.groupId, msgChain)
        }

        override suspend fun onWebsocketError(webSocket: WebSocket, ex: Exception) {
            ex.printStackTrace()
        }
    })
    rob.commandManager.register(EchoCommand())  // not a suspend function
//    rob.action.sendGroupMessage(114514, "1919810")  // send a message in global scope
    rob.addListeningGroups(985927054, 114514)  // set listening groups, set empty to listen all groups' event
}