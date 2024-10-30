/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.entity.custom.PardonEvent
import cn.rtast.rob.entity.lagrange.PokeEvent
import cn.rtast.rob.util.ob.OneBotAction
import cn.rtast.rob.util.ob.OneBotListener
import kotlin.time.Duration.Companion.seconds

class TestClient : OneBotListener {
    override suspend fun onGroupMessage(message: GroupMessage, json: String) {
    }

    override suspend fun onWebsocketErrorEvent(action: OneBotAction, ex: Exception) {
        ex.printStackTrace()
    }

    override suspend fun onPardon(event: PardonEvent) {
    }

    override suspend fun onWebsocketOpenEvent(action: OneBotAction) {
    }

    override suspend fun onGroupPoke(event: PokeEvent) {
    }
}

suspend fun main() {
    val client = TestClient()
    val wsAddress = System.getenv("WS_ADDRESS")
//    val wsAddress = "ws://127.0.0.1:3001"
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken, client)
//val instance1 = ROneBotFactory.createClient("ws://127.0.0.1:3001", "114514ghpA@", client)

//    instance1.commandManager.register(EchoCommand())
    ROneBotFactory.commandManager.register(EchoCommand())
    ROneBotFactory.commandManager.register(DelayCommand())
    ROneBotFactory.commandManager.register(MatchedCommand())
    ROneBotFactory.commandManager.register(PCommand())
//    instance1.scheduler.scheduleTask({
//        println(it.action.getLoginInfo())
//    }, 1000L, 1000L)
    instance1.addListeningGroups(985927054)
    ROneBotFactory.globalScheduler.scheduleTask({
        it.forEach {
            if (it.isActionInitialized) {
//                println(it.action.getLoginInfo())
            }
        }
    }, 1.seconds, 1.seconds)
}