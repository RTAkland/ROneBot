/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/22/25, 12:07 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.raw

import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.milky.MilkyAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * 群成员增加Json解析
 */
@Serializable
public data class RawGroupMemberIncreaseEvent(
    val data: GroupMemberIncrease,
    @SerialName("event_type")
    val eventType: MilkyEvents
) {
    @Serializable
    public data class GroupMemberIncrease(
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
         * 	管理员 QQ 号，如果是管理员同意入群（可选）
         */
        @SerialName("operator_id")
        val operatorId: Long?,
        /**
         * 	邀请者 QQ 号，如果是邀请入群（可选）
         */
        @SerialName("invitor_id")
        val invitorId: Long?
    ) {
        @Transient
        lateinit var action: MilkyAction
    }
}