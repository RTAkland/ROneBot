/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.event.flowEvent
import cn.rtast.rob.event.packed.GroupMessageEvent
import cn.rtast.rob.event.packed.ReactionAddEvent
import cn.rtast.rob.event.subscribe

suspend fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = OneBotFactory.createClient(wsAddress, wsAccessToken)
    instance1.flowEvent<GroupMessageEvent> {
        collect {
            println(it.message.reply("111"))
        }
    }
    instance1.subscribe<ReactionAddEvent> {
        println(it)
    }
    instance1.addListeningGroup(985927054)
}