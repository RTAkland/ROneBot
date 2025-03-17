package {{APP_PACKAGE}}

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.onebot.OneBotListener

suspend fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = OneBotFactory.createClient(wsAddress, wsAccessToken, object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage) {
            println(message)
        }
    })
}