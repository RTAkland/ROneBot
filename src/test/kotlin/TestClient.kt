import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.util.ob.OBMessage
import org.java_websocket.WebSocket

/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */


fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    ROneBotFactory.createClient(wsAddress, wsAccessToken, object : OBMessage {

        override fun onGroupMessage(websocket: WebSocket, message: GroupMessage, json: String) {
        }

        override fun onCanSendResponse(webSocket: WebSocket, result: Boolean) {
            println(result)
        }
    })
}