/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/31/25, 3:00 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.event.group

import cn.rtast.rob.milky.enums.internal.ApiStatus
import cn.rtast.rob.milky.segment.ReceiveSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取群精华消息列表
 */
@Serializable
public data class GetGroupEssenceMessages(
    val message: String?,
    val status: ApiStatus,
    val data: GroupEssenceMessages?
) {
    @Serializable
    public data class GroupEssenceMessages(
        /**
         * 精华消息列表
         */
        val messages: List<GroupEssenceMessage>,
        /**
         * 是否已到最后一页
         */
        @SerialName("is_end")
        val isEnd: Boolean
    )

    @Serializable
    public data class GroupEssenceMessage(
        /**
         * 群号
         */
        @SerialName("group_id")
        val groupId: Long,
        /**
         * 消息序列号
         */
        @SerialName("message_seq")
        val messageSeq: Long,
        /**
         * 消息发送时的 Unix 时间戳（秒）
         */
        @SerialName("message_time")
        val messageTime: Long,
        /**
         * 发送者 QQ 号
         */
        @SerialName("sender_id")
        val senderId: Long,
        /**
         * 发送者名称
         */
        @SerialName("sender_name")
        val senderName: String,
        /**
         * 设置精华的操作者 QQ 号
         */
        @SerialName("operator_id")
        val operatorId: Long,
        /**
         * 设置精华的操作者名称
         */
        @SerialName("operator_name")
        val operatorName: String,
        /**
         * 消息被设置精华时的 Unix 时间戳（秒）
         */
        @SerialName("operation_time")
        val operationTime: Long,
        /**
         * 消息段列表
         */
        val segments: List<ReceiveSegment>
    )
}