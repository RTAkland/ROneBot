/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/2
 */


package cn.rtast.rob.entity.custom

import cn.rtast.rob.common.annotations.ExcludeField
import cn.rtast.rob.util.ob.OneBotAction

data class BotOnlineEvent(
    @ExcludeField
    var action: OneBotAction,
    val reason: String
)

data class BotOfflineEvent(
    @ExcludeField
    var action: OneBotAction,
    val tag: String,
    val message: String
)