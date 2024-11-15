/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.qbot

import cn.rtast.rob.qqbot.entity.inbound.C2CMessageCreate
import cn.rtast.rob.qqbot.entity.inbound.FriendAddEvent
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreate

interface QQBotListener {

    suspend fun onGroupMessage(message: GroupAtMessageCreate.MessageBody)

    suspend fun onC2CMessage(message: C2CMessageCreate.MessageBody)

    suspend fun onFriendAdd(event: FriendAddEvent.AddEvent)
}