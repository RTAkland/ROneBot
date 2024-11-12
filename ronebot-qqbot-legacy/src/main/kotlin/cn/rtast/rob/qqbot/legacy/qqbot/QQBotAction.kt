/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */


package cn.rtast.rob.qqbot.legacy.qqbot

import cn.rtast.rob.common.ext.SendActionExt
import cn.rtast.rob.qqbot.legacy.BotInstance

class QQBotAction internal constructor(
    private val botInstance: BotInstance
) : SendActionExt {
    override suspend fun send(api: String, payload: Any?): String {
        TODO()
    }
}