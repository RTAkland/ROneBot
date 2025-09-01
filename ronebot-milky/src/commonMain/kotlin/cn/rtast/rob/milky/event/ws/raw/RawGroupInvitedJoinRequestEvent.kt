/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:40 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.raw

import arrow.core.Either
import cn.rtast.rob.milky.actionable.CommonGroupEventActionable
import cn.rtast.rob.milky.actionable.RequestEventActionable
import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.event.common.Group
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
        @SerialName("notification_seq")
        val notificationSeq: Long,
        /**
         * 被邀请者QQ号
         */
        @SerialName("target_user_id")
        val targetUserId: Long,
        /**
         * 邀请者 QQ 号
         */
        @SerialName("initiator_id")
        val initiatorId: Long,
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
    ) : RequestEventActionable, CommonGroupEventActionable {
        @Transient
        lateinit var action: MilkyAction

        @JvmAsync
        @JvmBlocking
        override suspend fun accept() {
            action.acceptGroupInvitation(groupId, notificationSeq)
        }

        @JvmAsync
        @JvmBlocking
        @Deprecated(level = DeprecationLevel.WARNING, message = "This function is useless.")
        override suspend fun accept(isFiltered: Boolean) {
            action.acceptGroupInvitation(groupId, notificationSeq)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject() {
            action.rejectGroupInvitation(groupId, notificationSeq)
        }

        @JvmAsync
        @JvmBlocking
        @Deprecated(level = DeprecationLevel.WARNING, message = "This function is useless.")
        override suspend fun reject(isFiltered: Boolean) {
            action.rejectGroupInvitation(groupId, notificationSeq)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject(reason: String) {
            action.rejectGroupInvitation(groupId, notificationSeq, reason)
        }

        @JvmAsync
        @JvmBlocking
        @Deprecated(level = DeprecationLevel.WARNING, message = "This function is useless.")
        override suspend fun reject(isFiltered: Boolean, reason: String) {
            action.rejectGroupInvitation(groupId, notificationSeq, reason)
        }

        @JvmBlocking
        override suspend fun getGroupInfo(): Either<String, Group> {
            return action.getGroupInfo(groupId, true)
        }
    }
}