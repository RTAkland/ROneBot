/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:40 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.raw

import cn.rtast.rob.milky.actionable.RequestEventActionable
import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.milky.MilkyAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking

/**
 * 邀请他人入群请求Json解析
 */
@Serializable
public data class RawGroupInvitedJoinRequestEvent(
    val data: GroupInvitedJoinRequest,
    @SerialName("event_type")
    val eventType: MilkyEvents,
) {
    @Serializable
    public data class GroupInvitedJoinRequest(
        /**
         * 请求 ID，用于同意 / 拒绝请求
         */
        @SerialName("request_id")
        val requestId: String,
        /**
         * 入群请求附加信息（可选）
         */
        @SerialName("invitee_id")
        val inviteeId: Long,
        /**
         * 被邀请者 QQ 号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 发起请求的用户 QQ 号
         */
        @SerialName("operator_id")
        val operatorId: Long
    ) : RequestEventActionable {
        @Transient
        lateinit var action: MilkyAction

        @JvmAsync
        @JvmBlocking
        override suspend fun accept() {
            action.acceptRequest(requestId)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject() {
            action.rejectRequest(requestId)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject(reason: String) {
            action.rejectRequest(requestId, reason)
        }
    }
}