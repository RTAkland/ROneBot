/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/2
 */


package cn.rtast.rob.event.raw.onebot

import cn.rtast.rob.onebot.OneBotAction

public data class RawBotOnlineEvent(
    val reason: String,
    var action: OneBotAction
)

public data class RawBotOfflineEvent(
    val tag: String,
    val message: String,
    var action: OneBotAction
)