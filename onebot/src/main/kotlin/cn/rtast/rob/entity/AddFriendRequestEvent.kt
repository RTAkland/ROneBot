/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/19
 */


package cn.rtast.rob.entity

import cn.rtast.rob.actionable.RequestEventActionable
import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.util.ob.OneBotAction
import com.google.gson.annotations.SerializedName

data class AddFriendRequestEvent(
    @ExcludeField
    var action: OneBotAction?,
    @SerializedName("user_id")
    val userId: Long,
    val comment: String,
    val flag: String,
    val time: Long
) : RequestEventActionable {
    override suspend fun approve() {
        action?.setFriendRequest(flag, true)
    }

    override suspend fun reject(remark: String?) {
        val newRemark = remark ?: ""
        action?.setFriendRequest(flag, false, newRemark)
    }
}