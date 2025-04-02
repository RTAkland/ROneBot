/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.event.raw.onebot

import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
public data class RawConnectEvent(
    @SerialName("self_id")
    var selfId: Long,
    val time: Long,
) {
    @Transient
    lateinit var action: OneBotAction
}