/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/4
 */

package cn.rtast.rob.event.listener

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.event.DispatchEvent
import cn.rtast.rob.event.onEvent

/**
 * 事件监听器
 */
abstract class AbstractListener(val botInstance: BaseBotInstance) {
    operator fun invoke(block: AbstractListener.() -> Unit) {
        this.block()
    }
}

/**
 * 注册事件
 */
inline fun <reified T : DispatchEvent<*>> AbstractListener.registerEvent(crossinline handler: suspend (T) -> Unit) =
    this.botInstance.onEvent<T>(handler)