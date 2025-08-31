/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:44 PM
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
 * 邀请自己入群请求Json解析
 */
@Serializable
public data class RawGroupInvitationRequestEvent(
    val data: GroupInvitationRequest,
    @SerialName("event_type")
    val eventType: MilkyEvents,
) {
    @Serializable
    public data class GroupInvitationRequest(
        /**
         * 请求 ID，用于同意 / 拒绝请求
         */
        @SerialName("invitation_seq")
        val invitationSeq: Long,
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 发起请求的用户 QQ 号
         */
        @SerialName("initiator_id")
        val initiatorId: Long,
    ) : RequestEventActionable {
        @Transient
        lateinit var action: MilkyAction

        @JvmAsync
        @JvmBlocking
        override suspend fun accept() {
            action.acceptGroupInvitation(groupId, invitationSeq)
        }

        @JvmAsync
        @JvmBlocking
        @Deprecated(level = DeprecationLevel.WARNING, message = "This function is useless.")
        override suspend fun accept(isFiltered: Boolean) {
            action.acceptGroupInvitation(groupId, invitationSeq)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject() {
            action.rejectGroupInvitation(groupId, invitationSeq)
        }

        @JvmAsync
        @JvmBlocking
        @Deprecated(level = DeprecationLevel.WARNING, message = "This function is useless.")
        override suspend fun reject(isFiltered: Boolean) {
            action.rejectGroupInvitation(groupId, invitationSeq, null)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject(reason: String) {
            action.rejectGroupInvitation(groupId, invitationSeq, reason)
        }

        @JvmAsync
        @JvmBlocking
        @Deprecated(level = DeprecationLevel.WARNING, message = "This function is useless.")
        override suspend fun reject(isFiltered: Boolean, reason: String) {
            action.rejectGroupInvitation(groupId, invitationSeq, reason)
        }
    }
}