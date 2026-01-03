/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/13
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.event

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.SendAction
import cn.rtast.rob.annotations.InternalROneBotApi
import kotlinx.coroutines.channels.Channel

public val flowEventChannel: MutableMap<BaseBotInstance, Channel<BaseDispatchEvent<out SendAction>>> =
    mutableMapOf()

/**
 * 生产消息
 * @see dispatchEvent
 */
public suspend fun <T : BaseDispatchEvent<out SendAction>> BaseBotInstance.emitFlowEvent(event: T): Unit =
    flowEventChannel.getOrPut(this) { Channel(Channel.UNLIMITED) }.send(event)
