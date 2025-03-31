/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/16
 */


package cn.rtast.rob.qqbot.api

import cn.rtast.rob.qqbot.enums.MsgType
import cn.rtast.rob.qqbot.segment.Keyboard
import cn.rtast.rob.qqbot.segment.Markdown
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
internal data class SendPlainTextMessage(
    val content: String,
    @SerialName("event_id")
    val eventId: String,
    @SerialName("msg_id")
    val msgId: String,
    @SerialName("msg_seq")
    val msgSeq: Int,
    @SerialName("msg_type")
    val msgType: Int = MsgType.PlainText.type,
)

internal data class SendMarkdownMessage(
    val content: Markdown,
    @SerialName("event_id")
    val eventId: String,
    @SerialName("msg_id")
    val msgId: String,
    @SerialName("msg_seq")
    val msgSeq: Int,
    @SerialName("msg_type")
    val msgType: Int = MsgType.Markdown.type,
)

internal data class SendKeyboardMessage(
    val content: Keyboard,
    @SerialName("event_id")
    val eventId: String,
    @SerialName("msg_id")
    val msgId: String,
    @SerialName("msg_seq")
    val msgSeq: Int,
    @SerialName("msg_type")
    val msgType: Int = MsgType.Markdown.type,
)