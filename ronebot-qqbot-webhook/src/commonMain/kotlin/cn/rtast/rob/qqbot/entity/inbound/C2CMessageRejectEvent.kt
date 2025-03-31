/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/19
 */


package cn.rtast.rob.qqbot.entity.inbound

import cn.rtast.rob.qqbot.qbot.QQBotAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
public data class C2CMessageRejectEvent(
    val id: String,
    val d: MsgRejectEvent
) {
    @Serializable
    public data class MsgRejectEvent(
        val timestamp: String,
        @SerialName("openid")
        val openId: String,
    ) {
        @Transient
        lateinit var action: QQBotAction
    }
}