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
        println(message.anonymous)
    }

    override suspend fun onWebsocketErrorEvent(action: OneBotAction, ex: Exception) {
        ex.printStackTrace()
    }

    override suspend fun onPardon(event: PardonEvent) {
        println(event)
    }

    override suspend fun onWebsocketOpenEvent(action: OneBotAction) {
        println(action.getLoginInfo())
    }

    override suspend fun onGroupPoke(event: PokeEvent) {
        println(event.action.getLoginInfo())
        println(event.pokeAction)
    }
}

fun main() {
    val client = TestClient()
    val wsAddress = System.getenv("WS_ADDRESS")
//    val wsAddress = "ws://127.0.0.1:3001"
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken, client)
//val instance1 = ROneBotFactory.createClient("ws://127.0.0.1:3001", "114514ghpA@", client)

//    instance1.commandManager.register(EchoCommand())
    ROneBotFactory.commandManager.register(EchoCommand())  // not a suspend function
    ROneBotFactory.commandManager.register(DelayCommand())  // not a suspend function
    ROneBotFactory.commandManager.register(MatchedCommand())  // not a suspend function
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