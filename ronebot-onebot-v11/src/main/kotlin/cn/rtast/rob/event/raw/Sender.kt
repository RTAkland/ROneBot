/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.event.raw

import cn.rtast.rob.actionable.GroupUserActionable
import cn.rtast.rob.actionable.UserActionable
import cn.rtast.rob.annotations.ExcludeField
import cn.rtast.rob.entity.IGroupSender
import cn.rtast.rob.entity.IPrivateSender
import cn.rtast.rob.enums.UserRole
import cn.rtast.rob.enums.UserSex
import cn.rtast.rob.onebot.MessageChain
import cn.rtast.rob.onebot.OneBotAction
import cn.rtast.rob.segment.Segment
import com.google.gson.annotations.SerializedName

public data class PrivateSender(
    @ExcludeField
    var action: OneBotAction,
    /**
     * QQ号
     */
    @SerializedName("user_id")
    override val userId: Long,
    /**
     * 昵称
     */
    val nickname: String,
    /**
     * 性别
     */
    val sex: UserSex,
    /**
     * 年龄
     */
    val age: Int,
) : UserActionable, IPrivateSender {
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

    override operator fun invoke(): Long = userId

    override suspend fun getStrangerInfo(): StrangerInfo.StrangerInfo =
        action.getStrangerInfo(userId, true)

    override suspend fun deleteFriend(): Unit = this.deleteFriend(true)

    override suspend fun deleteFriend(block: Boolean): Unit = action.deleteFriend(userId, block)
}

/**
 * 所有的非空字段在Lagrange.OneBot中都是存在的
 * 可能为空是为了兼容LLOneBot和NapCat
 */
public data class GroupSender(
    @ExcludeField
    val action: OneBotAction,
    /**
     * 发送者QQ号
     */
    @SerializedName("user_id")
    override val userId: Long,
    /**
     * 名字, 这个名字是在资料卡的名字, 不是群聊昵称
     */
    val nickname: String,
    /**
     * 性别
     */
    val sex: UserSex?,
    /**
     * 用户组
     */
    val role: UserRole,
    /**
     * 群聊昵称
     */
    val card: String?,
    /**
     * 等级
     */
    val level: String?,
    /**
     * 年龄
     */
    val age: Int,
    /**
     * 头衔
     * title 字段在LLOneBot和Lagrange.OneBot中是存在的, 但是在NapCat中不存在
     */
    val title: String?,
    /**
     * 群号
     */
    val groupId: Long = 114514L
) : GroupUserActionable, IGroupSender {
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

    override suspend fun getMemberInfo(): GroupMemberList.MemberInfo =
        action.getGroupMemberInfo(groupId, userId)

    override operator fun invoke(): Long = userId

    override suspend fun getStrangerInfo(): StrangerInfo.StrangerInfo =
        action.getStrangerInfo(userId)

    override suspend fun deleteFriend(): Unit = this.deleteFriend(true)

    override suspend fun deleteFriend(block: Boolean): Unit = action.deleteFriend(userId, block)

    override val isAdmin: Boolean = role == UserRole.admin

    override val isOwner: Boolean = role == UserRole.owner

    override val name: String = if (card.isNullOrBlank()) nickname else card
}

/**
 * 快速获取一个QQ号的资料卡信息
 */
public suspend fun Long.getStranger(action: OneBotAction): StrangerInfo.StrangerInfo =
    action.getStrangerInfo(this, true)