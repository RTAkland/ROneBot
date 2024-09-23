/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.actionable.GroupUserActionable
import cn.rtast.rob.enums.UserRole
import cn.rtast.rob.util.ob.MessageChain
import com.google.gson.annotations.SerializedName

data class Sender(
    @SerializedName("user_id")
    val userId: Long,
    val nickname: String,
    val sex: String,
    val role: UserRole?,
    val card: String?,
    val level: String,
    val age: String,
    val groupId: Long = 114514L
) : GroupUserActionable {
    override suspend fun kick(rejectJoinRequest: Boolean) {
        ROneBotFactory.action.kickGroupMember(groupId, userId, rejectJoinRequest)
    }

    override suspend fun ban(duration: Int) {
        super.ban(duration)
        ROneBotFactory.action.setGroupBan(groupId, userId, duration)
    }

    override suspend fun setGroupCard(card: String?) {
        ROneBotFactory.action.setGroupMemberCard(groupId, userId, card ?: "")
    }

    override suspend fun setGroupAdmin(enable: Boolean) {
        ROneBotFactory.action.setGroupAdmin(groupId, userId, enable)
    }

    override suspend fun sendLike(times: Int) {
        super.sendLike(times)
        ROneBotFactory.action.sendLike(userId, times)
    }

    override suspend fun sendMessage(content: String) {
        ROneBotFactory.action.sendPrivateMessage(userId, content)
    }

    override suspend fun sendMessage(content: MessageChain) {
        ROneBotFactory.action.sendPrivateMessage(userId, content)
    }
}