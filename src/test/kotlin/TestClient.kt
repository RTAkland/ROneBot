/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.util.ob.OBMessage
import org.java_websocket.WebSocket


fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val rob = ROneBotFactory.createClient(wsAddress, wsAccessToken, object : OBMessage {
        override suspend fun onBeRepliedInGroup(webSocket: WebSocket, message: GroupMessage) {
            println(message)
        }

        override suspend fun onBeRepliedInPrivate(webSocket: WebSocket, message: PrivateMessage) {
            println(message)
        }
    })
    rob.commandManager.register(EchoCommand())  // not a suspend function
//    rob.action.sendGroupMessage(114514, "1919810")  // send a message in global scope
}