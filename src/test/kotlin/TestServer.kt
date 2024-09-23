/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/29
 */

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.util.ob.OneBotListener


fun main() {
    val rob = ROneBotFactory.createServer(6760, "114514", object : OneBotListener {
        override suspend fun onGroupMessage(message: GroupMessage, json: String) {
            println(message.rawMessage)
            this.sendGroupMessage(message.groupId, "114514")
        }
    })

    rob.commandManager.register(EchoCommand())
}