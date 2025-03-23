/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/19
 */


package cn.rtast.rob.qqbot.entity.inbound

import cn.rtast.rob.qqbot.qbot.QQBotAction
import com.google.gson.annotations.SerializedName

public data class C2CMessageReceiveEvent(
    val id: String,
    val d: MsgReceiveEvent
) {
    public data class MsgReceiveEvent(
        @Transient
        var action: QQBotAction,
        val timestamp: String,
        @SerializedName("openid")
        val openId: String,
    )
}