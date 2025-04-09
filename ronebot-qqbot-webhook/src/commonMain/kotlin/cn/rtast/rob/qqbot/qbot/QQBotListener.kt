/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.qbot

import cn.rtast.jvmonly.linter.JvmOnly
import cn.rtast.rob.qqbot.entity.inbound.*
import kotlin.jvm.JvmSynthetic

/**
 * 所有事件的监听器, 继承此类重写需要的事件接口
 * 就能获取到对应事件的实体类
 */
public interface QQBotListener {

    /**
     * 群聊消息
     */
    @JvmSynthetic
    public suspend fun onGroupMessage(message: GroupAtMessageCreateEvent) {
    }

    /**
     * 群聊消息
     */
    @JvmOnly
    public fun onGroupMessageJvm(message: GroupAtMessageCreateEvent) {
    }

    /**
     * 单聊消息
     */
    @JvmSynthetic
    public suspend fun onC2CMessage(message: C2CMessageCreateEvent) {
    }

    /**
     * 单聊消息
     */
    @JvmOnly
    public fun onC2CMessageJvm(message: C2CMessageCreateEvent) {
    }

    /**
     * 被添加好友
     */
    @JvmSynthetic
    public suspend fun onFriendAdd(event: FriendAddEvent) {
    }

    /**
     * 被添加好友
     */
    @JvmOnly
    public fun onFriendAddJvm(event: FriendAddEvent) {
    }

    /**
     * 被删除好友
     */
    @JvmSynthetic
    public suspend fun onFriendDelete(event: FriendDelEvent) {
    }

    /**
     * 被删除好友
     */
    @JvmOnly
    public fun onFriendDeleteJvm(event: FriendDelEvent) {
    }

    /**
     * 被群聊移除
     */
    @JvmSynthetic
    public suspend fun onGroupDeleteRobot(event: GroupDeleteRobotEvent) {
    }

    /**
     * 被群聊移除
     */
    @JvmOnly
    public fun onGroupDeleteRobotJvm(event: GroupDeleteRobotEvent) {
    }

    /**
     * 被群聊添加
     */
    @JvmSynthetic
    public suspend fun onGroupAddRobot(event: GroupAddRobotEvent) {
    }

    /**
     * 被群聊添加
     */
    @JvmOnly
    public fun onGroupAddRobotJvm(event: GroupAddRobotEvent) {
    }

    /**
     * 拒绝机器人主动消息
     */
    @JvmSynthetic
    public suspend fun onC2CMessageRejectEvent(event: C2CMessageRejectEvent) {
    }

    /**
     * 拒绝机器人主动消息
     */
    @JvmOnly
    public fun onC2CMessageRejectEventJvm(event: C2CMessageRejectEvent) {
    }

    /**
     * 允许机器人主动消息
     */
    @JvmSynthetic
    public suspend fun onC2CMessageReceiveEvent(event: C2CMessageReceiveEvent) {
    }

    /**
     * 允许机器人主动消息
     */
    @JvmOnly
    public fun onC2CMessageReceiveEventJvm(event: C2CMessageReceiveEvent) {
    }

    /**
     * 群聊拒绝机器人主动消息
     */
    @JvmSynthetic
    public suspend fun onGroupMessageRejectEvent(event: GroupMessageRejectEvent) {
    }

    /**
     * 群聊拒绝机器人主动消息
     */
    @JvmOnly
    public fun onGroupMessageRejectEventJvm(event: GroupMessageRejectEvent) {
    }

    /**
     * 群聊接受机器人主动消息
     */
    @JvmSynthetic
    public suspend fun onGroupMessageReceiveEvent(event: GroupMessageReceiveEvent) {
    }

    /**
     * 群聊接受机器人主动消息
     */
    @JvmOnly
    public fun onGroupMessageReceiveEventJvm(event: GroupMessageReceiveEvent) {
    }
}