/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:59 PM
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
 * 群管理员变更Json解析
 */
@Serializable
public data class RawGroupAdminChangeEvent(
    val data: GroupAdminChange,
    @SerialName("event_type")
    val eventType: MilkyEvents
) {
    @Serializable
    public data class GroupAdminChange(
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
         * 是否被设置为管理员，false 表示被取消管理员
         */
        @SerialName("is_set")
        val isSet: Boolean
    ) {
        @Transient
        lateinit var action: MilkyAction
    }
}