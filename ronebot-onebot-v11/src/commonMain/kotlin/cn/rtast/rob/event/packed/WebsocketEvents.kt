/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/28
 */

package cn.rtast.rob.event.packed

import cn.rtast.rob.event.OneBotEvent
import cn.rtast.rob.event.raw.internal.RawWebsocketCloseEvent
import cn.rtast.rob.event.raw.internal.RawWebsocketErrorEvent
import cn.rtast.rob.onebot.OneBotAction

/**
 * WebsocketCloseEvent的类型别名
 */
@Deprecated("改名称已弃用, 请使用新的名称", replaceWith = ReplaceWith("cn.rtast.rob.event.packed.WebsocketCloseEvent"))
public typealias WebsocketDisconnectedEvent = WebsocketCloseEvent

/**
 * Websocket链接关闭后触发
 */
public data class WebsocketCloseEvent(
    override val action: OneBotAction,
    val event: RawWebsocketCloseEvent
) : OneBotEvent

/**
 * Websocket抛出异常时触发
 */
public data class WebsocketErrorEvent(
    override val action: OneBotAction,
    val event: RawWebsocketErrorEvent
) : OneBotEvent

/**
 * Websocket连接成功后触发
 */
public data class WebsocketConnectedEvent(
    override val action: OneBotAction
) : OneBotEvent

/**
 * 当ROB以反向Websocket运行, WS服务器启动后触发
 */
public data class WebsocketServerStartedEvent(
    override val action: OneBotAction,
    val port: Int,
) : OneBotEvent

public data class WebsocketEvents(
    override val action: OneBotAction
) : OneBotEvent