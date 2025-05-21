/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/22/25, 12:29 AM
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
 * 群戳一戳Json解析
 */
@Serializable
public data class RawGroupNudgeEvent(
    val data: GroupNudge,
    @SerialName("event_type")
    val eventType: MilkyEvents
) {
    @Serializable
    public data class GroupNudge(
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 发送者 QQ 号
         */
        @SerialName("sender_id")
        val senderId: Long,
        /**
         * 接收者 QQ 号
         */
        @SerialName("receiver_id")
        val receiverId: Long
    ) {
        @Transient
        lateinit var action: MilkyAction
    }
}