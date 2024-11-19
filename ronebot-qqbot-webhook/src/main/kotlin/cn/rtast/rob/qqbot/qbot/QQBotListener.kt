/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.qbot

import cn.rtast.rob.qqbot.entity.inbound.*

interface QQBotListener {

    suspend fun onGroupMessage(message: GroupAtMessageCreateEvent) {}

    suspend fun onC2CMessage(message: C2CMessageCreateEvent) {}

    suspend fun onFriendAdd(event: FriendAddEvent) {}

    suspend fun onFriendDelete(event: FriendDelEvent) {}

    suspend fun onGroupDeleteRobot(event: GroupDeleteRobotEvent) {}

    suspend fun onGroupAddRobot(event: GroupAddRobotEvent) {}

    suspend fun onC2CMessageRejectEvent(event: C2CMessageRejectEvent) {}

    suspend fun onC2CMessageReceiveEvent(event: C2CMessageReceiveEvent) {}

    suspend fun onGroupMessageRejectEvent(event: GroupMessageRejectEvent) {}

    suspend fun onGroupMessageReceiveEvent(event: GroupMessageReceiveEvent) {}
}