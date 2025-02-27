/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/2
 */


package cn.rtast.rob.entity.custom

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.onebot.OneBotAction

data class IBotOnlineEvent(
    @ExcludeField
    var action: OneBotAction,
    val reason: String
)

data class IBotOfflineEvent(
    @ExcludeField
    var action: OneBotAction,
    val tag: String,
    val message: String
)