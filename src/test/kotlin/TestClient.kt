/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.util.ob.OneBotListener

fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val rob = ROneBotFactory.createClient(wsAddress, wsAccessToken, object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
            println(this.getGroupFileUrl(674869175, "d24d1c07-aef5-4614-8c8a-97663d87774d", 0))
            message.sender.groupPoke()
        }
    })
    rob.commandManager.register(EchoCommand())  // not a suspend function
    rob.commandManager.register(DelayCommand())  // not a suspend function
}