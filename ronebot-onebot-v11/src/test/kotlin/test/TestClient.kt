/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.event.onEvent
import cn.rtast.rob.event.packed.MessageTimeoutEvent
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.onebot.OneBotListener
import kotlinx.coroutines.delay
import kotlin.time.Duration.Companion.seconds

suspend fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = OneBotFactory.createClient(wsAddress, wsAccessToken, object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage) {
            delay(1.5.seconds)
        }

        override suspend fun onMessageTimeout(event: MessageTimeoutEvent) {
            println(event)
        }
    })
    instance1.addListeningGroup(985927054)
}