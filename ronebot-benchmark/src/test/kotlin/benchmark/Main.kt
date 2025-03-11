/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/12
 */

package benchmark

import org.java_websocket.client.WebSocketClient
import org.java_websocket.handshake.ServerHandshake
import java.lang.Exception
import java.net.URI

private const val RAW_JSON = """{"message_type":"group","sub_type":"normal","message_id":908645166,"group_id":985927054,"user_id":3458671395,"anonymous":null,"message":[{"type":"text","data":{"text":"^*^*^"}}],"raw_message":"1","font":0,"sender":{"user_id":3458671395,"nickname":"RTAkland","card":"RTAkland22222","sex":"unknown","age":0,"area":"","level":"53","role":"admin","title":""},"message_style":{"bubble_id":0,"pendant_id":0,"font_id":0,"font_effect_id":0,"is_cs_font_effect_enabled":false,"bubble_diy_text_id":0},"time":1741725842,"self_id":1845464277,"post_type":"message"}"""

fun main() {
    val client = object : WebSocketClient(URI("ws://127.0.0.1:9090"), mapOf("Authorization" to "Bearer 114514")) {
        override fun onOpen(p0: ServerHandshake?) {

        }

        override fun onMessage(p0: String?) {

        }

        override fun onClose(p0: Int, p1: String?, p2: Boolean) {

        }

        override fun onError(p0: Exception?) {

        }
    }
    client.connectBlocking()
    (1..10000).map {
        client.send(RAW_JSON.replace("^*^*^", "/echo"))
    }
}