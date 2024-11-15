/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.entity.inbound

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreate.Author
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreate.MessageScene
import cn.rtast.rob.qqbot.qbot.QQBotAction
import com.google.gson.annotations.SerializedName

data class C2CMessageCreate(
    val id: String,
    val d: MessageBody
) {
    data class MessageBody(
        @ExcludeField
        var action: QQBotAction,
        val content: String,
        val timestamp: String,
        val author: Author,
        val attachments: List<Attachment>,
        @SerializedName("message_scene")
        val messageScene: MessageScene,
    )

    data class Attachment(
        val url: String,
        val filename: String,
        val width: Int,
        val height: Int,
        @SerializedName("content_type")
        val contentType: String,
        val content: String
    )
}