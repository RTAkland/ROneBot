/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.event.raw.lagrange

import cn.rtast.rob.enums.EssenceMessageType
import com.google.gson.annotations.SerializedName

public data class EssenceMessageList(
    val data: List<EssenceMessage>
) {
    public data class EssenceMessage(
        /**
         * 精华消息发送者QQ号
         */
        @SerializedName("sender_id")
        val senderId: Long,
        /**
         * 发送者昵称
         */
        @SerializedName("sender_nick")
        val senderNick: String,
        /**
         * 发送时间戳
         */
        @SerializedName("sender_time")
        val senderTime: Long,
        /**
         * 设置者QQ号
         */
        @SerializedName("operator_id")
        val operatorId: Long,
        /**
         * 设置者昵称
         */
        @SerializedName("operator_nick")
        val operatorNick: String,
        /**
         * 设置时间
         */
        @SerializedName("operator_time")
        val operatorTime: Long,
        /**
         * 消息ID
         */
        @SerializedName("message_id")
        val messageId: Long,
        /**
         * 消息内容
         */
        val content: List<Content>
    )

    public data class Content(
        val type: EssenceMessageType,
        val data: ContentData
    )

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