/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

@file:Suppress("UNUSED")

package cn.rtast.rob.events

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.SendAction
import kotlin.reflect.KClass

/**
 * 一个map存储了所有注册的消息处理器
 */
val eventHandlers =
    mutableMapOf<BaseBotInstance, MutableMap<KClass<out DispatchEvent<*>>, suspend (DispatchEvent<*>) -> Unit>>()

/**
 * 注册事件
 */
inline fun <reified T : DispatchEvent<*>> BaseBotInstance.onEvent(crossinline handler: suspend (T) -> Unit) {
    val eventType = T::class
    val botEventHandlers = eventHandlers[this]

    if (botEventHandlers != null) {
        botEventHandlers[eventType] = { event -> handler(event as T) }
    } else {
        eventHandlers[this] = mutableMapOf(eventType to { event -> handler(event as T) })
    }
}

/**
 * 分发事件
 */
suspend fun BaseBotInstance.dispatchEvent(event: DispatchEvent<out SendAction>) {
    val botEventHandlers = eventHandlers[this]
    if (botEventHandlers != null) {
        val handler = botEventHandlers[event::class]
        handler?.invoke(event)
    }
}
