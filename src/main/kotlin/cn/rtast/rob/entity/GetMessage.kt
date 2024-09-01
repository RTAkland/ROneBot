/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/1
 */


package cn.rtast.rob.entity

import cn.rtast.rob.enums.MessageType
import com.google.gson.annotations.SerializedName

data class GetMessage(
    val data: Data,
    val echo: String
) {
    data class Data(
        val time: Long,
        @SerializedName("message_type")
        val messageType: MessageType,
        val message: List<ArrayMessage>,
        @SerializedName("message_id")
        val messageId: Long,
        val sender: Sender,
    )
}