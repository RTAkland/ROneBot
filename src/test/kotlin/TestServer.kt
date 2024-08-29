import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.util.ob.OBMessage
import org.java_websocket.WebSocket

/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/29
 */


fun main() {
    val rob = ROneBotFactory.createServer(6760, "114514", object : OBMessage {
        override suspend fun onGroupMessage(websocket: WebSocket, message: GroupMessage, json: String) {
            println(message.rawMessage)
            this.sendGroupMessage(message.groupId, "114514")
        }
    })

    rob.commandManager.register(EchoCommand())
}