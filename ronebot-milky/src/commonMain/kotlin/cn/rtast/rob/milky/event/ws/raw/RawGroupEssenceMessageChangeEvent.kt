/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/22/25, 12:03 AM
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
 * 群精华消息变更Json解析
 */
@Serializable
public data class RawGroupEssenceMessageChangeEvent(
    val data: GroupEssenceMessageChange,
    @SerialName("event_type")
    val eventType: MilkyEvents
) {
    @Serializable
    public data class GroupEssenceMessageChange(
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 发生变更的消息序列号
         */
        @SerialName("message_seq")
        val messageSeq: Long,
        /**
         * 是否被设置为精华，false 表示被取消精华
         */
        @SerialName("is_set")
        val isSet: Boolean
    ) {
        @Transient
        lateinit var action: MilkyAction
    }
}