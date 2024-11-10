/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity

import cn.rtast.rob.actionable.GroupUserActionable
import cn.rtast.rob.actionable.UserActionable
import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.enums.UserRole
import cn.rtast.rob.enums.UserSex
import cn.rtast.rob.onebot.MessageChain
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.segment.Segment
import com.google.gson.annotations.SerializedName

data class PrivateSender(
    @ExcludeField
    val action: OneBotAction,
    @SerializedName("user_id")
    val userId: Long,
    val nickname: String,
    val sex: UserSex,
    val age: Int,
) : UserActionable {
    override suspend fun poke() {
        action.sendFriendPoke(userId)
    }

    override suspend fun sendLike(times: Int) {
        super.sendLike(times)
        action.sendLike(userId, times)
    }

    override suspend fun sendMessage(content: String): Long? {
        return action.sendPrivateMessage(userId, content)
    }

    override suspend fun sendMessageAsync(content: String) {
        action.sendPrivateMessageAsync(userId, content)
    }

    override suspend fun sendMessage(content: MessageChain): Long? {
        return action.sendPrivateMessage(userId, content)
    }

    override suspend fun sendMessageAsync(content: MessageChain) {
        action.sendPrivateMessageAsync(userId, content)
    }

    override suspend fun sendMessage(content: Segment): Long? {
        val builder = MessageChain.Builder()
            .addSegment(content)
            .build()
        return action.sendPrivateMessage(userId, builder)
    }

    override suspend fun sendMessageAsync(content: Segment) {
        val builder = MessageChain.Builder()
            .addSegment(content)
            .build()
        action.sendPrivateMessageAsync(userId, builder)
    }

    override suspend fun sendMessage(content: List<Segment>): Long? {
        val builder = MessageChain.Builder().apply {
            content.forEach { addSegment(it) }
        }
        return action.sendPrivateMessage(userId, builder.build())
    }

    override suspend fun sendMessageAsync(content: List<Segment>) {
        val builder = MessageChain.Builder().apply {
            content.forEach { addSegment(it) }
        }
        action.sendPrivateMessageAsync(userId, builder.build())
    }

    override operator fun invoke() = userId
}

/**
 * 所有的非空字段在Lagrange.OneBot中都是存在的
 * 可能为空是为了兼容LLOneBot和NapCat
 */
data class GroupSender(
    @ExcludeField
    val action: OneBotAction,
    @SerializedName("user_id")
    val userId: Long,
    val nickname: String,
    val sex: UserSex?,
    val role: UserRole,
    val card: String?,
    val level: String?,
    val age: Int,
    // title 字段在LLOneBot和Lagrange.OneBot中是存在的, 但是在NapCat中不存在
    val title: String?,
    val groupId: Long = 114514L
) : GroupUserActionable {
    override suspend fun kick(rejectJoinRequest: Boolean) {
        action.kickGroupMember(groupId, userId, rejectJoinRequest)
    }

    override suspend fun ban(duration: Int) {
        super.ban(duration)
        action.setGroupBan(groupId, userId, duration)
    }

    override suspend fun setGroupCard(card: String?) {
        action.setGroupMemberCard(groupId, userId, card ?: "")
    }

    override suspend fun setGroupAdmin(enable: Boolean) {
        action.setGroupAdmin(groupId, userId, enable)
    }

    override suspend fun sendLike(times: Int) {
        super.sendLike(times)
        action.sendLike(userId, times)
    }

    override suspend fun sendMessage(content: String): Long? {
        return action.sendPrivateMessage(userId, content)
    }

    override suspend fun sendMessageAsync(content: String) {
        action.sendPrivateMessageAsync(userId, content)
    }

    override suspend fun sendMessage(content: MessageChain): Long? {
        return action.sendPrivateMessage(userId, content)
    }

    override suspend fun sendMessageAsync(content: MessageChain) {
        action.sendPrivateMessageAsync(userId, content)
    }

    override suspend fun sendMessage(content: Segment): Long? {
        val builder = MessageChain.Builder()
            .addSegment(content)
            .build()
        return action.sendPrivateMessage(userId, builder)
    }

    override suspend fun sendMessageAsync(content: Segment) {
        val builder = MessageChain.Builder()
            .addSegment(content)
            .build()
        action.sendPrivateMessageAsync(userId, builder)
    }

    override suspend fun sendMessage(content: List<Segment>): Long? {
        val builder = MessageChain.Builder().apply {
            content.forEach { addSegment(it) }
        }
        return action.sendPrivateMessage(userId, builder.build())
    }

    override suspend fun sendMessageAsync(content: List<Segment>) {
        val builder = MessageChain.Builder().apply {
            content.forEach { addSegment(it) }
        }
        action.sendPrivateMessageAsync(userId, builder.build())
    }

    override suspend fun poke() {
        action.sendGroupPoke(groupId, userId)
    }

    override suspend fun privatePoke() {
        action.sendFriendPoke(userId)
    }

    override suspend fun getMemberInfo(): GroupMemberList.MemberInfo {
        return action.getGroupMemberInfo(groupId, userId)
    }

    override operator fun invoke() = userId

    override val isAdmin: Boolean = role == UserRole.admin

    override val isOwner: Boolean = role == UserRole.owner

    override val name: String = if (card.isNullOrBlank()) nickname else card
}