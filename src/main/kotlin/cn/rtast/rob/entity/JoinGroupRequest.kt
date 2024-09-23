/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/19
 */


package cn.rtast.rob.entity

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.actionable.RequestEventActionable
import com.google.gson.annotations.SerializedName

data class JoinGroupRequest(
    @SerializedName("user_id")
    val userId: Long,
    @SerializedName("group_id")
    val groupId: Long,
    @SerializedName("invitor_id")
    val invitorId: Long,
    val flag: String,
    val comment: String,
    val time: Long,
) : RequestEventActionable {
    override suspend fun approve() {
        ROneBotFactory.action.setGroupRequest(flag, "add")
    }

    override suspend fun reject(remark: String?) {
        val newRemark = remark ?: ""
        ROneBotFactory.action.setGroupRequest(flag, "add", false, newRemark)
    }
}