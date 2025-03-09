/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.revoke
import cn.rtast.rob.onebot.OneBotListener
import kotlin.time.Duration.Companion.seconds

suspend fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = OneBotFactory.createClient(wsAddress, wsAccessToken, object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage) {
            message.reply("111")?.revoke(0.1.seconds, message.action)
            message.revoke(0.1.seconds)
        }
    })
    instance1.addListeningGroup(985927054)
}