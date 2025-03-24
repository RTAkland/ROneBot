/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

@file:Suppress("UNUSED")

package cn.rtast.rob.event

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.SendAction
import kotlin.reflect.KClass

/**
 * 一个map存储了所有注册的消息处理器
 */
public val eventHandlers: MutableMap<BaseBotInstance, MutableMap<KClass<out BaseDispatchEvent<*>>, suspend (BaseDispatchEvent<*>) -> Unit>> =
    mutableMapOf()

/**
 * 注册事件
 */
public inline fun <reified T : BaseDispatchEvent<*>> BaseBotInstance.onEvent(crossinline handler: suspend (T) -> Unit) {
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
public suspend fun BaseBotInstance.dispatchEvent(event: BaseDispatchEvent<out SendAction>) {
//    this.emitFlowEvent(event)
    val botEventHandlers = eventHandlers[this]
    if (botEventHandlers != null) {
        val handler = botEventHandlers[event::class]
        handler?.invoke(event)
    }
}

/**
 * 注册事件
 */
public inline fun <reified T : BaseDispatchEvent<*>> BaseBotInstance.subscribe(crossinline handler: suspend (T) -> Unit): Unit =
    this.onEvent<T>(handler)