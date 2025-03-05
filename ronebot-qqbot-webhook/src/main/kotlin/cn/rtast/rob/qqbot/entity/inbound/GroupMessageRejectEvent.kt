/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/19
 */


package cn.rtast.rob.qqbot.entity.inbound

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.qqbot.qbot.QQBotAction
import com.google.gson.annotations.SerializedName

public data class GroupMessageRejectEvent(
    val id: String,
    val d: MsgRejectEvent
) {
    public data class MsgRejectEvent(
        @ExcludeField
        var action: QQBotAction,
        @SerializedName("group_openid")
        val groupOpenId: String,
        @SerializedName("op_member_openid")
        val opMemberOpenId: String,
        val timestamp: String
    )
}