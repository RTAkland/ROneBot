/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/3
 */


package cn.rtast.rob.event.raw.message

import cn.rtast.rob.enums.MessageType
import cn.rtast.rob.enums.SegmentType
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class ForwardMessage(
    val data: ForwardMessage
) {
    @Serializable
    public data class ForwardMessage(
        val messages: List<MessageContent>
    )

    @Serializable
    public data class MessageContent(
        val content: List<Content>,
        val sender: Sender,
        @SerialName("message_format")
        val messageFormat: String?,
        @SerialName("message_type")
        val messageType: MessageType
    )

    @Serializable
    public data class Sender(
        val nickname: String,
        @SerialName("user_id")
        val userId: Long
    )

    @Serializable
    public data class Content(
        val type: SegmentType,
        val data: ArrayMessage.Data
    )
}