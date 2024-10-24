/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package rob

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.util.ob.OneBotListener

class TestClient : OneBotListener {
    override suspend fun onGroupMessage(message: GroupMessage, json: String) {
        println(message.text)
        println(message.texts)
        println(message.mfaces)
        println(message.mface)
    }

    override suspend fun onWebsocketErrorEvent(ex: Exception) {
        ex.printStackTrace()
    }
}

val client = TestClient()

val wsAddress = System.getenv("WS_ADDRESS")
val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken, client)
//val instance2 = ROneBotFactory.createClient("ws://127.0.0.1:3001", "114514ghpA@", client)

fun main() {
//    instance1.commandManager.register(EchoCommand())
    ROneBotFactory.commandManager.register(EchoCommand())  // not a suspend function
    ROneBotFactory.commandManager.register(DelayCommand())  // not a suspend function
    ROneBotFactory.commandManager.register(MatchedCommand())  // not a suspend function
    instance1.scheduler.scheduleTask(suspend {
//        println(rob.action.getStatus())
    }, 1000L, 1000L)
    instance1.addListeningGroups(985927054)
}