/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/19
 */


package cn.rtast.rob.event.raw

import cn.rtast.rob.actionable.RequestEventActionable
import cn.rtast.rob.onebot.OneBotAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking

@Serializable
public data class AddFriendRequestEvent(
    @Transient
    var action: OneBotAction? = null,
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
    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun approve() {
        action?.setFriendRequest(flag, true)
    }

    @JvmBlocking(suffix = "JvmBlocking")
    @JvmAsync(suffix = "JvmAsync")
    override suspend fun reject(remark: String?) {
        val newRemark = remark ?: ""
        action?.setFriendRequest(flag, false, newRemark)
    }
}