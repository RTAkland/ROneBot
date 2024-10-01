/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/1
 */


package cn.rtast.rob.entity.lagrange

import cn.rtast.rob.enums.EssenceMessageType
import com.google.gson.annotations.SerializedName

data class EssenceMessageList(
    val data: List<Data>
) {
    data class Data(
        @SerializedName("sender_id")
        val senderId: Long,
        @SerializedName("sender_nick")
        val senderNick: String,
        @SerializedName("sender_time")
        val senderTime: Long,
        @SerializedName("operator_id")
        val operatorId: Long,
        @SerializedName("operator_nick")
        val operatorNick: String,
        @SerializedName("operator_time")
        val operatorTime: Long,
        @SerializedName("message_id")
        val messageId: Long,
        val content: List<Content>
    )

    data class Content(
        val type: EssenceMessageType,
        val data: ContentData
    )

    data class ContentData(
        // 该参数会在type为text时出现
        val text: String?,
        // 该参数会在type为video或image时出现
        val file: String?,
        // 该参数会在type为video或image时出现
        val url: String?,
        // 该参数会在type为image时出现
        val summary: String?,
        // 该参数会在type为image时出现
        val subType: Int,
        // 该参数会在type为image时出现
        val filename: String?
    )
}