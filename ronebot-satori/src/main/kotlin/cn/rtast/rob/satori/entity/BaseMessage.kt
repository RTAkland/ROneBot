/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/9
 */

@file:Suppress("unused")


package cn.rtast.rob.satori.entity

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.satori.entity.BaseMessage.User
import cn.rtast.rob.satori.entity.guild.inbound.GetGuild
import cn.rtast.rob.satori.satori.SatoriAction
import com.google.gson.annotations.SerializedName

data class BaseMessage(
    val op: Int,
    val body: Body
) {
    data class Body(
        @ExcludeField
        var action: SatoriAction,
        val id: Int,
        val content: String,
        val type: String,
        @SerializedName("self_id")
        val selfId: String,
        val platform: String,
        val timestamp: Long,
        val message: Message,
        val member: Any?,
        val user: User,
        val channel: Any,
        val guild: Any?
    )

    data class User(
        val id: String,
        val name: String,
        val nick: String,
        val avatar: String,
        @SerializedName("is_bot")
        val isBot: Boolean?,
    )

    data class Message(
        val id: String,
        val content: String,
        @SerializedName("create_at")
        val createAt: Long,
    )
}

data class PrivateMessage(
    val op: Int,
    val body: Message
) {
    data class Message(
        @ExcludeField
        var action: SatoriAction,
        val id: Int,
        val content: String,
        val type: String,
        @SerializedName("self_id")
        val selfId: String,
        val platform: String,
        val timestamp: Long,
        val message: BaseMessage.Message,
        val user: User,
        val channel: Any,
    )
}

data class GuildMessage(
    val op: Int,
    val body: Message
) {
    data class Message(
        @ExcludeField
        var action: SatoriAction,
        val id: Int,
        val content: String,
        val type: String,
        @SerializedName("self_id")
        val selfId: String,
        val platform: String,
        val timestamp: Long,
        val message: BaseMessage,
        val member: Member?,
        val user: User,
        val channel: Channel,
        val guild: GetGuild?
    )

    data class Member(
        val user: User,
        val nick: String,
    )

    data class Channel(
        val id: String,
        val name: String,
        val type: Int,
    )
}