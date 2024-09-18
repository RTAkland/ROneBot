/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.util.ob.CQMessageChain
import cn.rtast.rob.util.ob.OBMessage
import cn.rtast.rob.util.toJson


fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val rob = ROneBotFactory.createClient(wsAddress, wsAccessToken, object : OBMessage {

        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
            message.revoke(3)
            message.reply("114514")
        }

        override suspend fun onGroupFileUpload(groupId: Long, userId: Long, file: FileEvent) {
            println(file.file.name)
        }

        override suspend fun onPrivateMessage(message: PrivateMessage, json: String) {
        }

        override suspend fun onWebsocketErrorEvent(ex: Exception) {
            ex.printStackTrace()
        }

        override suspend fun onGroupMessageRevoke(message: GroupRevokeMessage) {
            println(message.messageId)
        }


        override suspend fun onGetGroupMessageResponse(message: GetMessage) {
            println("getMessage")
            val msg = CQMessageChain.Builder()
                .addAt(message.data.sender.userId)
                .addText("消息如下: ")
                .addNewLine()
                .addText(message.toJson())
                .build()
            this.sendGroupMessage(message.data.groupId!!, msg)
        }

        override suspend fun onGetPrivateMessageResponse(message: GetMessage) {
            println("getMessage")
            println(message.data.message)
        }
    })
    rob.commandManager.register(EchoCommand())  // not a suspend function
//    rob.action.sendGroupMessage(114514, "1919810")  // send a message in global scope
    rob.addListeningGroups(985927054, 114514)  // set listening groups, set empty to listen all groups' event
}