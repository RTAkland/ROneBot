/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.event.events.GroupMessageEvent
import cn.rtast.rob.event.onEvent

suspend fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken)
    instance1.addListeningGroup(985927054)
    instance1.onEvent<GroupMessageEvent> {
        println(ROneBotFactory.botManager.getBotInstanceByAction(it.action))
    }
}