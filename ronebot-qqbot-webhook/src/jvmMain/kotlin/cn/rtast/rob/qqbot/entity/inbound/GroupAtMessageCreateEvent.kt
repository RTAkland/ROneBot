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
import com.google.gson.annotations.SerializedName
import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

public data class GroupAtMessageCreateEvent(
    val id: String,
    val d: MessageBody,
    override var sessionId: Uuid? = null
) : GroupMessageActionable, IGroupMessage {
    override suspend fun reply(message: String) {
        d.action.sendGroupPlainTextMessage(d.groupOpenId, message, id, d.id)
    }

    override suspend fun reply(message: Markdown) {
        d.action.sendGroupMarkdownMessage(d.groupOpenId, message, id, d.id)
    }

    override suspend fun reply(message: Keyboard) {
        d.action.sendGroupKeyboardMessage(d.groupOpenId, message, id, d.id)
    }

    override suspend fun revoke() {
        d.action.revokePrivateMessage(d.groupOpenId, d.id)
    }

    public data class MessageBody(
        @Transient
        var action: QQBotAction,
        val id: String,
        val content: String,
        val timestamp: String,
        val author: Author,
        @SerializedName("group_id")
        val groupId: String,
        @SerializedName("group_openid")
        val groupOpenId: String,
        @SerializedName("message_scene")
        val messageScene: MessageScene,
    )

    public data class Author(
        val id: String,
        @SerializedName("member_openid")
        val memberOpenId: String,
        @SerializedName("union_openid")
        val unionOpenId: String,
    )

    public data class MessageScene(
        val source: String,
    )
}

internal val GroupAtMessageCreateEvent.command
    get() = if (d.content.startsWith(" ")) d.content.drop(1).split(" ").first() else this.d.content.split(" ").first()