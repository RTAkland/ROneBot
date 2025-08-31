/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:34 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.raw

import cn.rtast.rob.milky.actionable.RequestEventActionable
import cn.rtast.rob.milky.enums.NotificationType
import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.milky.MilkyAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking

/**
 * 入群请求Json解析
 */
@Serializable
public data class RawGroupJoinRequestEvent(
    val data: GroupJoinRequestEvent,
    @SerialName("event_type")
    val eventType: MilkyEvents,
) {
    @Serializable
    public data class GroupJoinRequestEvent(
        /**
         * 请求 ID，用于同意 / 拒绝请求
         */
        @SerialName("notification_seq")
        val notificationSeq: Long,
        /**
         * 入群请求附加信息
         */
        @SerialName("comment")
        val comment: String,
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 请求是否被过滤（发起自风险账户）
         */
        @SerialName("is_filtered")
        val isFiltered: Boolean,
        /**
         * 申请入群的用户 QQ 号
         */
        @SerialName("initiator_id")
        val initiatorId: Long
    ) : RequestEventActionable {
        @Transient
        lateinit var action: MilkyAction

        @JvmAsync
        @JvmBlocking
        override suspend fun accept() {
            action.acceptGroupRequest(groupId, notificationSeq, NotificationType.JoinRequest, isFiltered)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun accept(isFiltered: Boolean) {
            action.acceptGroupRequest(groupId, notificationSeq, NotificationType.JoinRequest, isFiltered)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject() {
            action.rejectGroupRequest(groupId, notificationSeq, NotificationType.JoinRequest, isFiltered)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject(isFiltered: Boolean) {
            action.rejectGroupRequest(groupId, notificationSeq, NotificationType.JoinRequest, isFiltered, null)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject(reason: String) {
            action.rejectGroupRequest(groupId, notificationSeq, NotificationType.JoinRequest, isFiltered, reason)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject(isFiltered: Boolean, reason: String) {
            action.rejectGroupRequest(groupId, notificationSeq, NotificationType.JoinRequest, isFiltered, reason)
        }
    }
}