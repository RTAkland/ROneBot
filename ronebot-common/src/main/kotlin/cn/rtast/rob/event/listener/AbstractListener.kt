/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/4
 */

package cn.rtast.rob.event.listener

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.event.BaseDispatchEvent
import cn.rtast.rob.event.onEvent

/**
 * 事件监听器
 */
public abstract class AbstractListener(public val botInstance: BaseBotInstance) {
    public operator fun invoke(block: AbstractListener.() -> Unit) {
        this.block()
    }
}

/**
 * 注册事件
 */
public inline fun <reified T : BaseDispatchEvent<*>> AbstractListener.subscribe(crossinline handler: suspend (T) -> Unit) =
    this.botInstance.onEvent<T>(handler)