/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/9
 */


package cn.rtast.rob.satori.entity

import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.satori.entity.GroupMessage.Guild
import cn.rtast.rob.satori.entity.GroupMessage.Member
import cn.rtast.rob.satori.util.SatoriAction
import com.google.gson.annotations.SerializedName

data class GroupRevokeMessage(
    val op: Int,
    val body: RevokeMessage
) {
    data class RevokeMessage(
        @ExcludeField
        var action: SatoriAction,
        val id: Int,
        val type: String,
        @SerializedName("self_id")
        val selfId: String,
        val platform: String,
        val timestamp: Long,
        val message: BaseMessage.Message,
        val member: Member,
        val user: BaseMessage.User,
        val channel: GroupMessage.Channel,
        val guild: Guild,
        val operator: Operator
    )

    data class Operator(
        val id: String,
        val name: String,
        val nick: String,
        val avatar: String,
    )
}


data class PrivateRevokeMessage(
    val op: Int,
    val body: RevokeMessage
) {
    data class RevokeMessage(
        @ExcludeField
        var action: SatoriAction,
        val id: Int,
        val type: String,
        @SerializedName("self_id")
        val selfId: String,
        val platform: String,
        val timestamp: Long,
        val message: BaseMessage.Message,
        val user: BaseMessage.User,
        val channel: GroupMessage.Channel,
        val operator: Operator
    )

    data class Operator(
        val id: String,
        val name: String,
        val nick: String,
        val avatar: String,
    )
}