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
public data class GroupMessageRejectEvent(
    val id: String,
    val d: MsgRejectEvent
) {
    @Serializable
    public data class MsgRejectEvent(
        @SerialName("group_openid")
        val groupOpenId: String,
        @SerialName("op_member_openid")
        val opMemberOpenId: String,
        val timestamp: String
    ) {
        @Transient
        lateinit var action: QQBotAction
    }
}