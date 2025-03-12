/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/13
 */

package cn.rtast.rob.event

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.SendAction
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.util.concurrent.ConcurrentHashMap

public val scope: CoroutineScope = CoroutineScope(Dispatchers.IO)

public val eventChannel: MutableMap<BaseBotInstance, Channel<BaseDispatchEvent<out SendAction>>> =
    ConcurrentHashMap()

/**
 * 传入一个lambda来接收这个事件
 */
public inline fun <reified T : BaseDispatchEvent<*>> BaseBotInstance.flowEvent(
    crossinline init: suspend Flow<T>.() -> Unit
) {
    scope.launch {
        init(eventChannel.getOrPut(this@flowEvent) { Channel(Channel.UNLIMITED) }
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
    eventChannel.getOrPut(this) { Channel(Channel.UNLIMITED) }.send(event)
