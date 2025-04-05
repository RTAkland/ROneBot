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
import cn.rtast.rob.commonCoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

public val flowEventChannel: MutableMap<BaseBotInstance, Channel<BaseDispatchEvent<out SendAction>>> =
    mutableMapOf()

/**
 * 传入一个lambda来接收这个事件
 */
@Deprecated("该API已不可用", ReplaceWith("cn.rtast.rob.event.subscribe"))
public inline fun <reified T : BaseDispatchEvent<*>> BaseBotInstance.flowEvent(
    crossinline init: suspend Flow<T>.() -> Unit
) {
    commonCoroutineScope.launch {
        init(flowEventChannel.getOrPut(this@flowEvent) { Channel(Channel.UNLIMITED) }
            .receiveAsFlow()
            .filterIsInstance<T>()
        )
    }
}

/**
 * 生产消息
 * @see dispatchEvent
 */
public suspend fun <T : BaseDispatchEvent<out SendAction>> BaseBotInstance.emitFlowEvent(event: T): Unit =
    flowEventChannel.getOrPut(this) { Channel(Channel.UNLIMITED) }.send(event)
