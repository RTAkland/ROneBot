/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.qqbot.entity.inbound

import cn.rtast.rob.entity.IPrivateMessage
import cn.rtast.rob.qqbot.actionable.C2CMessageActionable
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent.Author
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent.MessageScene
import cn.rtast.rob.qqbot.qbot.QQBotAction
import cn.rtast.rob.qqbot.segment.Keyboard
import cn.rtast.rob.qqbot.segment.Markdown
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
public data class C2CMessageCreateEvent(
    val id: String,
    val d: MessageBody,
    override var sessionId: Uuid? = null
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

    @Serializable
    public data class MessageBody(
        val id: String,
        val content: String,
        val timestamp: String,
        val author: Author,
        val attachments: List<Attachment>,
        @SerialName("message_scene")
        val messageScene: MessageScene,
    ) {
        @Transient
        lateinit var action: QQBotAction
    }

    @Serializable
    public data class Attachment(
        val url: String,
        val filename: String,
        val width: Int,
        val height: Int,
        @SerialName("content_type")
        val contentType: String,
        val content: String
    )
}

internal val C2CMessageCreateEvent.command
    get() = if (d.content.startsWith(" ")) d.content.drop(1).split(" ").first() else this.d.content.split(" ").first()