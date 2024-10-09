/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/9
 */


package cn.rtast.rob.satori.entity

import cn.rtast.rob.satori.enums.MessageType
import com.google.gson.annotations.SerializedName

class BaseMessage(
    val id: Int,
    val content: String,
    val type: MessageType,
    @SerializedName("self_id")
    val selfId: String,
    val platform: String,
    val timestamp: Long,
    val message: Message,
    val member: Member?,
    val user: User,
    val channel: Channel,
    val guild: Guild?
) {
    data class Message(
        val id: String,
        val content: String,
        @SerializedName("create_at")
        val createAt: Long,
    )

    data class Member(
        val user: User,
        val nick: String,
    )

    data class User(
        val id: String,
        val name: String,
        val nick: String,
        val avatar: String,
    )

    data class Channel(
        val id: String,
        val name: String,
        val type: Int,
    )

    data class Guild(
        val id: String,
        val name: String,
        val avatar: String,
    )
}