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
import cn.rtast.rob.util.ob.MessageChain


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
    suspend fun sendMessage(content: String)

    /**
     * 发送数组消息链消息
     */
    suspend fun sendMessage(content: MessageChain)

    /**
     * 发送戳一戳(xxx戳了你)
     */
    suspend fun poke()
}

internal interface GroupUserActionable : UserActionable {

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
    suspend fun getMemberInfo(): GroupMemberList.Data
}
