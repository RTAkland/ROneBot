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
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking

@Serializable
public data class JoinGroupRequestEvent(
    /**
     * QQ号
     */
    @SerialName("user_id")
    val userId: Long,
    /**
     * 群号
     */
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 邀请者QQ号
     */
    @SerialName("invitor_id")
    val invitorId: Long?,
    /**
     * 入群flag, 作为加群请求的ID,
     * 处理加群请求时需要使用
     */
    val flag: String,
    /**
     * 验证信息
     */
    val comment: String,
    /**
     * 时间戳
     */
    val time: Long,
) : RequestEventActionable {
    @Transient
    lateinit var action: OneBotAction

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun approve() {
        action.setGroupRequest(flag, "add")
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reject(remark: String?) {
        val newRemark = remark ?: ""
        action.setGroupRequest(flag, "add", false, newRemark)
    }
}