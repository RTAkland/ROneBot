/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.entity.inbound

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.entity.IPrivateMessage
import cn.rtast.rob.qqbot.actionable.C2CMessageActionable
import cn.rtast.rob.qqbot.entity.Keyboard
import cn.rtast.rob.qqbot.entity.Markdown
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent.Author
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent.MessageScene
import cn.rtast.rob.qqbot.qbot.QQBotAction
import com.google.gson.annotations.SerializedName

data class C2CMessageCreateEvent(
    val id: String,
    val d: MessageBody
) : C2CMessageActionable, IPrivateMessage {
    override suspend fun reply(message: String) {
        d.action.sendPrivatePlainTextMessage(d.author.unionOpenId, message, id, d.id)
    }

    override suspend fun reply(message: Markdown) {
        d.action.sendPrivateMarkdownMessage(d.author.unionOpenId, message, id, d.id)
    }

    override suspend fun reply(message: Keyboard) {
        d.action.sendPrivateKeyboardMessage(d.author.unionOpenId, message, id, d.id)
    }

    override suspend fun revoke() {
        d.action.revokePrivateMessage(d.author.unionOpenId, d.id)
    }

    data class MessageBody(
        @ExcludeField
        var action: QQBotAction,
        val id: String,
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