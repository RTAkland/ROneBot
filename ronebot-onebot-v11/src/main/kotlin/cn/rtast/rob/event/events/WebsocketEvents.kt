/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package cn.rtast.rob.event.events

import cn.rtast.rob.entity.custom.IWebsocketCloseEvent
import cn.rtast.rob.entity.custom.IWebsocketErrorEvent
import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * Websocket链接关闭后触发
 */
data class WebsocketCloseEvent(
    override val action: OneBotAction,
    val event: IWebsocketCloseEvent
) : OneBotEvent

/**
 * Websocket抛出异常时触发
 */
data class WebsocketErrorEvent(
    override val action: OneBotAction,
    val event: IWebsocketErrorEvent
) : OneBotEvent

/**
 * Websocket连接成功后触发
 */
data class WebsocketConnectedEvent(
    override val action: OneBotAction
): OneBotEvent