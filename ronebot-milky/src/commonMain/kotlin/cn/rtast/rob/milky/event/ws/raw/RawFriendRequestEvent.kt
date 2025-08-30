/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:23 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.raw

import cn.rtast.rob.milky.actionable.RequestEventActionable
import cn.rtast.rob.milky.enums.RequestState
import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.milky.MilkyAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking

/**
 * 好友请求Json解析
 */
@Serializable
public data class RawFriendRequestEvent(
    val data: FriendRequest,
    @SerialName("event_type")
    val eventType: MilkyEvents,
) {
    @Serializable
    public data class FriendRequest(
        /**
         * 请求发起时的 Unix 时间戳（秒）
         */
        val time: Long,
        /**
         * 请求发起者 QQ 号
         */
        @SerialName("initiator_id")
        val initiatorId: Long,
        /**
         * 请求发起者 UID
         */
        @SerialName("initiator_uid")
        val initiatorUID: String,
        /**
         * 目标用户 QQ 号
         */
        @SerialName("target_user_id")
        val targetUserId: Long,
        /**
         * 目标用户 UID
         */
        @SerialName("target_user_uid")
        val targetUserUID: String,
        /**
         * 请求状态
         */
        val state: RequestState,
        /**
         * 申请附加信息
         */
        val comment: String,
        /**
         * 申请来源
         */
        val via: String,
        /**
         * 请求是否被过滤（发起自风险账户）
         */
        @SerialName("is_filtered")
        val isFiltered: Boolean,
    ) : RequestEventActionable {
        @Transient
        lateinit var action: MilkyAction

        @JvmAsync
        @JvmBlocking
        override suspend fun accept() {
            action.acceptFriendRequest(initiatorUID, isFiltered)
        }
        @JvmAsync
        @JvmBlocking
        override suspend fun accept(isFiltered: Boolean) {
            action.acceptFriendRequest(initiatorUID, isFiltered)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject() {
            action.rejectFriendRequest(initiatorUID, isFiltered)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject(isFiltered: Boolean) {
            action.rejectFriendRequest(initiatorUID, isFiltered)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject(reason: String) {
            action.rejectFriendRequest(initiatorUID, isFiltered, reason)
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reject(isFiltered: Boolean, reason: String) {
            action.rejectFriendRequest(initiatorUID, isFiltered, reason)
        }
    }
}