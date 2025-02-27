/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/31
 */


package cn.rtast.rob.entity.custom

import cn.rtast.rob.onebot.OneBotAction

data class IWebsocketCloseEvent(
    val action: OneBotAction,
    val code: Int,
    val reason: String,
    val remote: Boolean
)

data class IWebsocketErrorEvent(
    val action: OneBotAction,
    val exception: Exception,
)