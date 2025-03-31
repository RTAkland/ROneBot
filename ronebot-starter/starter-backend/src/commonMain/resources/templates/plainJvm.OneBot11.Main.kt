package {{APP_PACKAGE}}

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.onebot.OneBotListener

suspend fun main() {
    val wsAddress = "ws://127.0.0.1:8081"
    val wsAccessToken = "123456"
    val instance1 = OneBotFactory.createClient(wsAddress, wsAccessToken, object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage) {
            println(message)
        }
    })
}