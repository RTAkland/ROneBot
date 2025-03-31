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
public data class C2CMessageReceiveEvent(
    val id: String,
    val d: MsgReceiveEvent
) {
    @Serializable
    public data class MsgReceiveEvent(
        val timestamp: String,
        @SerialName("openid")
        val openId: String,
    ) {
        @Transient
        lateinit var action: QQBotAction
    }
}