/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/31
 */


package cn.rtast.rob.event.raw.internal

import cn.rtast.rob.onebot.OneBotAction

public data class RawWebsocketCloseEvent(
    val action: OneBotAction,
)

public data class RawWebsocketErrorEvent(
    val action: OneBotAction,
    val exception: Exception,
)