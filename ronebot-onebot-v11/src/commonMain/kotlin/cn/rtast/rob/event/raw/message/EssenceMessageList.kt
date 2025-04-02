/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.event.raw.message

import cn.rtast.rob.enums.EssenceMessageType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class EssenceMessageList(
    val data: List<EssenceMessage>
) {
    @Serializable
    public data class EssenceMessage(
        /**
         * 精华消息发送者QQ号
         */
        @SerialName("sender_id")
        val senderId: Long,
        /**
         * 发送者昵称
         */
        @SerialName("sender_nick")
        val senderNick: String,
        /**
         * 发送时间戳
         */
        @SerialName("sender_time")
        val senderTime: Long,
        /**
         * 设置者QQ号
         */
        @SerialName("operator_id")
        val operatorId: Long,
        /**
         * 设置者昵称
         */
        @SerialName("operator_nick")
        val operatorNick: String,
        /**
         * 设置时间
         */
        @SerialName("operator_time")
        val operatorTime: Long,
        /**
         * 消息ID
         */
        @SerialName("message_id")
        val messageId: Long,
        /**
         * 消息内容
         */
        val content: List<Content>
    )

    @Serializable
    public data class Content(
        val type: EssenceMessageType,
        val data: ContentData
    )

    @Serializable
    public data class ContentData(
        /**
         * 该参数会在type为text时出现
         */
        val text: String?,
        /**
         * 该参数会在type为video或image时出现
         */
        val file: String?,
        /**
         * 该参数会在type为video或image时出现
         */
        val url: String?,
        /**
         * 该参数会在type为image时出现
         */
        val summary: String?,
        /**
         * 该参数会在type为image时出现
         */
        val subType: Int,
        /**
         * 该参数会在type为image时出现
         */
        val filename: String?
    )
}