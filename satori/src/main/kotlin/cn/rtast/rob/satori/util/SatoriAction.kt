/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/9
 */


package cn.rtast.rob.satori.util

import cn.rtast.rob.common.CommonAction
import cn.rtast.rob.common.util.toJson
import cn.rtast.rob.satori.BotInstance

class SatoriAction internal constructor(
    private val botInstance: BotInstance,
): CommonAction {
    override suspend fun send(message: Any) {
        this.send(message.toJson())
    }

    override suspend fun send(message: String) {
        botInstance.websocket.send(message)
    }
}