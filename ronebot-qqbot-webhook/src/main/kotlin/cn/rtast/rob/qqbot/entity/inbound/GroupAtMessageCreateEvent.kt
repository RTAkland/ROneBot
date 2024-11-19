/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.entity.inbound

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.entity.IGroupMessage
import cn.rtast.rob.qqbot.actionable.GroupMessageActionable
import cn.rtast.rob.qqbot.entity.Keyboard
import cn.rtast.rob.qqbot.entity.Markdown
import cn.rtast.rob.qqbot.qbot.QQBotAction
import com.google.gson.annotations.SerializedName

data class GroupAtMessageCreateEvent(
    val id: String,
    val d: MessageBody
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

    data class MessageBody(
        @ExcludeField
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

    data class Author(
        val id: String,
        @SerializedName("member_openid")
        val memberOpenId: String,
        @SerializedName("union_openid")
        val unionOpenId: String,
    )

    data class MessageScene(
        val source: String,
    )
}