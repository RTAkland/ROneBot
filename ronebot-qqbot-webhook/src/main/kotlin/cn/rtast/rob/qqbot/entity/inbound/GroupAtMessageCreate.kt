/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.entity.inbound

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.qqbot.actionable.GroupMessageActionable
import cn.rtast.rob.qqbot.entity.Markdown
import cn.rtast.rob.qqbot.enums.internal.MessageDispatchType
import cn.rtast.rob.qqbot.qbot.QQBotAction
import com.google.gson.annotations.SerializedName

data class GroupAtMessageCreate(
    val id: String,
    val d: MessageBody,
    val t: MessageDispatchType
) : GroupMessageActionable {
    override suspend fun reply(message: String) {
        TODO("Not yet implemented")
    }

    override suspend fun reply(message: Markdown) {
        TODO("Not yet implemented")
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