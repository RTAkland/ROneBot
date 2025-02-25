/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/9/18
 */

@file:Suppress("unused")

package cn.rtast.rob.actionable

import cn.rtast.rob.entity.GroupMemberList
import cn.rtast.rob.exceptions.IllegalDurationException
import cn.rtast.rob.exceptions.IllegalLikeTimesException
import cn.rtast.rob.onebot.MessageChain
import cn.rtast.rob.segment.Segment

/**
 * 对一个用户快速进行操作, 例如: 点赞名片、发送私聊消息、戳一戳
 */
internal interface UserActionable {

    /**
     * 赞用户的名片
     */
    suspend fun sendLike(times: Int) {
        if (times !in 1..10) {
            throw IllegalLikeTimesException("Like times must in 1 ~ 10 >>> $times")
        }
    }

    /**
     * 发送纯文本消息
     */
    suspend fun sendMessage(content: String): Long?

    /**
     * 发送纯文本消息, 但是异步
     */
    suspend fun sendMessageAsync(content: String)

    /**
     * 发送[MessageChain]消息链消息
     */
    suspend fun sendMessage(content: MessageChain): Long?

    /**
     * 发送[MessageChain]消息链消息, 但是异步
     */
    suspend fun sendMessageAsync(content: MessageChain)

    /**
     * 发送单个[Segment]消息
     */
    suspend fun sendMessage(content: Segment): Long?

    /**
     * 发送单个[Segment]消息, 但是异步
     */
    suspend fun sendMessageAsync(content: Segment)

    /**
     * 发送[Segment]列表
     */
    suspend fun sendMessage(content: List<Segment>): Long?

    /**
     * 发送[Segment]列表, 但是异步
     */
    suspend fun sendMessageAsync(content: List<Segment>)

    /**
     * 发送戳一戳(xxx戳了你)
     */
    suspend fun poke()

    /**
     * 没有任何实际作用的invoke操作符()
     * 但是还是要写一个接口
     */
    operator fun invoke(): Long
}

/**
 * 对私聊的用户进行拓展用于群聊用户, 拓展了: 踢出群聊、禁言、设置群名片、获取成员信息等
 */
internal interface GroupUserActionable : UserActionable {

    /**
     * 判断sender是否为管理员或群主
     */
    val isAdmin: Boolean

    /**
     * 判断一个sender是否为群主
     */
    val isOwner: Boolean

    /**
     * 获取用户的群昵称如果群昵称为空或者为空字符串则返回该账号的昵称
     */
    val name: String

    /**
     * 将用户踢出群聊可以设置是否拒绝加群请求
     */
    suspend fun kick(rejectJoinRequest: Boolean)

    /**
     * 带有默认值的踢出群员(允许加群请求)
     */
    suspend fun kick() {
        this.kick(false)
    }

    /**
     * 设置群员禁言,时长单位为秒(s)
     */
    suspend fun ban(duration: Int) {
        if (duration < 0) {
            throw IllegalDurationException("Duration must great than 0 seconds >>> $duration")
        }
        if (duration > 2674859) {  // 29 days 23 hours 59 seconds
            throw IllegalDurationException("Duration must less than 2674859 seconds >>> $duration")
        }
    }

    /**
     * 带有默认值的禁言(30分钟 30 * 60s)
     */
    suspend fun ban() {
        this.ban(30 * 60)
    }

    /**
     * 取消禁言
     */
    suspend fun unban() {
        this.ban(0)
    }

    /**
     * 设置群员的群昵称
     */
    suspend fun setGroupCard(card: String?)

    /**
     * 取消设置群昵称
     */
    suspend fun unsetGroupCard() {
        this.setGroupCard(null)
    }

    /**
     * 设置群员管理员, enable为true则为设置为false则取消设置
     */
    suspend fun setGroupAdmin(enable: Boolean)

    /**
     * 取消设置管理员
     */
    suspend fun unsetGroupAdmin() {
        this.setGroupAdmin(false)
    }

    /**
     * 群聊戳一戳
     */
    override suspend fun poke()

    /**
     * 在群聊中触发私聊的戳一戳
     */
    suspend fun privatePoke()

    /**
     * 获取该成员在群聊中的信息
     */
    suspend fun getMemberInfo(): GroupMemberList.MemberInfo
}
