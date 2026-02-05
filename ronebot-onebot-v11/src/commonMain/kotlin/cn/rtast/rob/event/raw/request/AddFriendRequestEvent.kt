/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/19
 */


package cn.rtast.rob.event.raw.request

import cn.rtast.rob.actionable.RequestEventActionable
import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
public data class AddFriendRequestEvent(
    /**
     * QQ号
     */
    @SerialName("user_id")
    val userId: Long,
    /**
     * 验证信息
     */
    val comment: String,
    /**
     * flag作为本次请求的ID
     */
    val flag: String,
    /**
     * 时间戳
     */
    val time: Long
) : RequestEventActionable {

    @Transient
    lateinit var action: OneBotAction

    override suspend fun approve() {
        action.setFriendRequest(flag, true)
    }

    override suspend fun reject(remark: String?) {
        val newRemark = remark ?: ""
        action.setFriendRequest(flag, false, newRemark)
    }
}