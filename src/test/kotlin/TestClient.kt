/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.util.ob.MessageChain
import cn.rtast.rob.util.ob.OneBotListener

fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val rob = ROneBotFactory.createClient(wsAddress, wsAccessToken, object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
            val msg = MessageChain.Builder().addText("1")
            val msg2 = MessageChain.Builder().addText("2").addMessageChain(msg).build()
            message.reply(msg2)
        }
    })
    rob.commandManager.register(EchoCommand())  // not a suspend function
    rob.commandManager.register(DelayCommand())  // not a suspend function
    rob.scheduler.scheduleTask(suspend {}, 1000L, 1000L)
}