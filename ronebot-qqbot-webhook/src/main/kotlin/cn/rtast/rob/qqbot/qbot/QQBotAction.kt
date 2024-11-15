/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.qbot

import cn.rtast.rob.qqbot.BotInstance

class QQBotAction internal constructor(
    private val appid: String,
    private val clientSecret: String,
    private val botInstance: BotInstance
) {
}