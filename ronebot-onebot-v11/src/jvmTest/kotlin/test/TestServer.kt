/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package test

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.onebot.OneBotListener

suspend fun main() {
    val bot = OneBotFactory.createServer(8082, "", object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage) {
            println(message)
        }
    })
}