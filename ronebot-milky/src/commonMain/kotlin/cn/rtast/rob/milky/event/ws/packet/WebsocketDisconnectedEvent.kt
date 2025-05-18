/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 10:56 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.packet

import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.milky.MilkyAction

/**
 * Websocket断开连接服务器
 */
public data class WebsocketDisconnectedEvent(
    override val action: MilkyAction
) : MilkyEvent