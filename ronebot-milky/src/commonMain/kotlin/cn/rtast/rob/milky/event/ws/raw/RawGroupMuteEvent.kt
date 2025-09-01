/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/22/25, 12:22 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.raw

import arrow.core.Either
import cn.rtast.rob.milky.actionable.CommonGroupEventActionable
import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.event.common.Group
import cn.rtast.rob.milky.milky.MilkyAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import love.forte.plugin.suspendtrans.annotation.JvmBlocking

/**
 * 邀请自己入群请求Json解析
 */
@Serializable
public data class RawGroupMuteEvent(
    val data: GroupMute,
    @SerialName("event_type")
    val eventType: MilkyEvents
) {
    @Serializable
    public data class GroupMute(
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 发生变更的用户 QQ 号
         */
        @SerialName("user_id")
        val userId: Long,
        /**
         * 禁言时长（秒），为 0 表示取消禁言
         */
        val duration: Int,

        /**
         * 操作者QQ号
         */
        @SerialName("operator_id")
        val operatorId: Long,
    ): CommonGroupEventActionable {
        @Transient
        lateinit var action: MilkyAction

        @JvmBlocking
        override suspend fun getGroupInfo(): Either<String, Group> {
            return action.getGroupInfo(groupId, true)
        }
    }
}