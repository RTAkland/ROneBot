/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.qqbot.entity.inbound

import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.qqbot.actionable.GroupMessageActionable
import cn.rtast.rob.qqbot.qbot.QQBotAction
import cn.rtast.rob.qqbot.segment.Keyboard
import cn.rtast.rob.qqbot.segment.Markdown
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

@Serializable
public data class GroupAtMessageCreateEvent(
    val id: String,
    val d: MessageBody,
    override var sessionId: Uuid? = null
) : GroupMessageActionable, IGroupMessage {
    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reply(message: String) {
        d.action.sendGroupPlainTextMessage(d.groupOpenId, message, id, d.id)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reply(message: Markdown) {
        d.action.sendGroupMarkdownMessage(d.groupOpenId, message, id, d.id)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun reply(message: Keyboard) {
        d.action.sendGroupKeyboardMessage(d.groupOpenId, message, id, d.id)
    }

    @JvmAsync(suffix = "JvmAsync")
    @JvmBlocking(suffix = "JvmBlocking")
    override suspend fun revoke() {
        d.action.revokePrivateMessage(d.groupOpenId, d.id)
    }

    @Serializable
    public data class MessageBody(
        val id: String,
        val content: String,
        val timestamp: String,
        val author: Author,
        @SerialName("group_id")
        val groupId: String,
        @SerialName("group_openid")
        val groupOpenId: String,
        @SerialName("message_scene")
        val messageScene: MessageScene,
    ) {
        @Transient
        lateinit var action: QQBotAction
    }

    @Serializable
    public data class Author(
        val id: String,
        @SerialName("member_openid")
        val memberOpenId: String,
        @SerialName("union_openid")
        val unionOpenId: String,
    )

    @Serializable
    public data class MessageScene(
        val source: String,
    )
}

internal val GroupAtMessageCreateEvent.command
    get() = if (d.content.startsWith(" ")) d.content.drop(1).split(" ").first() else this.d.content.split(" ").first()