/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/22/25, 12:17 AM
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
 * 群消息表情回应Json解析
 */
@Serializable
public data class RawGroupMessageReactionEvent(
    val data: GroupMessageReaction,
    @SerialName("event_type")
    val eventType: MilkyEvents
) {
    @Serializable
    public data class GroupMessageReaction(
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 发送回应者 QQ 号
         */
        @SerialName("user_id")
        val userId: Long,
        /**
         * 消息序列号
         */
        @SerialName("message_seq")
        val messageSeq: Long,
        /**
         * 表情 ID
         */
        @SerialName("face_id")
        val faceId: String,
        /**
         * 是否为添加，false 表示取消回应（可选）
         */
        @SerialName("is_add")
        val isAdd: Boolean
    ) {
        @Transient
        lateinit var action: MilkyAction
    }
}