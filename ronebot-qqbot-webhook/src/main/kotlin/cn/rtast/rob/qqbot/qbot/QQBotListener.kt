/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.qbot

import cn.rtast.rob.qqbot.entity.inbound.*

/**
 * 所有事件的监听器, 继承此类重写需要的事件接口
 * 就能获取到对应事件的实体类
 */
interface QQBotListener {

    /**
     * 群聊消息
     */
    suspend fun onGroupMessage(message: GroupAtMessageCreateEvent) {}

    /**
     * 单聊消息
     */
    suspend fun onC2CMessage(message: C2CMessageCreateEvent) {}

    /**
     * 被添加好友
     */
    suspend fun onFriendAdd(event: FriendAddEvent) {}

    /**
     * 被删除好友
     */
    suspend fun onFriendDelete(event: FriendDelEvent) {}

    /**
     * 被群聊移除
     */
    suspend fun onGroupDeleteRobot(event: GroupDeleteRobotEvent) {}

    /**
     * 被群聊添加
     */
    suspend fun onGroupAddRobot(event: GroupAddRobotEvent) {}

    /**
     * 拒绝机器人主动消息
     */
    suspend fun onC2CMessageRejectEvent(event: C2CMessageRejectEvent) {}

    /**
     * 允许机器人主动消息
     */
    suspend fun onC2CMessageReceiveEvent(event: C2CMessageReceiveEvent) {}

    /**
     * 群聊拒绝机器人主动消息
     */
    suspend fun onGroupMessageRejectEvent(event: GroupMessageRejectEvent) {}

    /**
     * 群聊接受机器人主动消息
     */
    suspend fun onGroupMessageReceiveEvent(event: GroupMessageReceiveEvent) {}
}