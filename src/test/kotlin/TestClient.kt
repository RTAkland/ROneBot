/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GetMessage
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.GroupRevokeMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.util.ob.CQMessageChain
import cn.rtast.rob.util.ob.MessageChain
import cn.rtast.rob.util.ob.OBMessage
import cn.rtast.rob.util.toJson
import org.java_websocket.WebSocket


fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val rob = ROneBotFactory.createClient(wsAddress, wsAccessToken, object : OBMessage {
        override suspend fun onGroupMessage(websocket: WebSocket, message: GroupMessage, json: String) {
        }

        override suspend fun onPrivateMessage(websocket: WebSocket, message: PrivateMessage, json: String) {
            this.getMessage(message.messageId, "114514", message.sender.userId)
        }

        override suspend fun onWebsocketErrorEvent(webSocket: WebSocket, ex: Exception) {
            ex.printStackTrace()
        }

        override suspend fun onGroupMessageRevoke(ws: WebSocket, message: GroupRevokeMessage) {
            println(message.messageId)
            this.getMessage(message.messageId, "114514", message.groupId)
        }


        override suspend fun onGetGroupMessageResponse(ws: WebSocket, message: GetMessage) {
            println("getMessage")
            val msg = CQMessageChain.Builder()
                .addAt(message.data.sender.userId)
                .addText("消息如下: ")
                .addNewLine()
                .addText(message.toJson())
                .build()
            this.sendGroupMessage(message.data.groupId!!, msg)
        }

        override suspend fun onGetPrivateMessageResponse(ws: WebSocket, message: GetMessage) {
            println("getMessage")
            println(message.data.message)
        }
    })
    rob.commandManager.register(EchoCommand())  // not a suspend function
//    rob.action.sendGroupMessage(114514, "1919810")  // send a message in global scope
    rob.addListeningGroups(985927054, 114514)  // set listening groups, set empty to listen all groups' event
}