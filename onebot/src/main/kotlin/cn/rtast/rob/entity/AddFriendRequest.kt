/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/19
 */


package cn.rtast.rob.entity

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.actionable.RequestEventActionable
import com.google.gson.annotations.SerializedName

data class AddFriendRequest(
    @SerializedName("user_id")
    val userId: Long,
    val comment: String,
    val flag: String,
    val time: Long
) : RequestEventActionable {
    override suspend fun approve() {
        ROneBotFactory.action.setFriendRequest(flag, true)
    }

    override suspend fun reject(remark: String?) {
        val newRemark = remark ?: ""
        ROneBotFactory.action.setFriendRequest(flag, false, newRemark)
    }
}