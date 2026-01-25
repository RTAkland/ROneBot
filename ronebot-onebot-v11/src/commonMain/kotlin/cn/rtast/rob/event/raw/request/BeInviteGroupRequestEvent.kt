/*
 * Copyright © 2025 RTAkland & NekoCurit
 * Date: 1/25/26, 15:49PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
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
public data class BeInviteGroupRequestEvent(
    /**
     * 群号
     */
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 邀请者QQ号
     */
    @SerialName("user_id")
    val invitorId: Long,
    /**
     * 入群flag, 作为同意/拒绝邀请请求的ID,
     * 处理加群请求时需要使用
     */
    val flag: String,
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
        action.setGroupRequest(flag, "invite")
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reject(remark: String?) {
        action.setGroupRequest(flag, "invite", false)
    }
}