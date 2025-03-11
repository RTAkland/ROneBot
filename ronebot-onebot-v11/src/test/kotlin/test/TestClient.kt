/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.OneBotFactory

suspend fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = OneBotFactory.createClient(wsAddress, wsAccessToken)
    instance1.addListeningGroup(985927054)
    OneBotFactory.commandManager {
        groupCommand(listOf("/te")) {
            println(it)
        }
        privateCommand(listOf("/te")) {
            println(it)
        }
    }
}