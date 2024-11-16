/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/16
 */


package cn.rtast.rob.qqbot.entity.outbound

import cn.rtast.rob.qqbot.entity.Markdown
import cn.rtast.rob.qqbot.enums.MsgType
import com.google.gson.annotations.SerializedName

data class SendPrivatePlainTextMessage(
    val content: String,
    @SerializedName("event_id")
    val eventId: String,
    @SerializedName("msg_id")
    val msgId: String,
    @SerializedName("msg_seq")
    val msgSeq: Int,
    @SerializedName("msg_type")
    val msgType: Int = MsgType.PlainText.type,
)

data class SendPrivateMarkdownMessage(
    val content: Markdown,
    @SerializedName("event_id")
    val eventId: String,
    @SerializedName("msg_id")
    val msgId: String,
    @SerializedName("msg_seq")
    val msgSeq: Int,
    @SerializedName("msg_type")
    val msgType: Int = MsgType.PlainText.type,
)