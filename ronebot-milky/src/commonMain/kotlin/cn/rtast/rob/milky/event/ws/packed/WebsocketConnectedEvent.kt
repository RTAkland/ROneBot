/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 10:51 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.packed

import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.milky.MilkyAction

/**
 * Websocket连接上服务器触发的事件
 */
public data class WebsocketConnectedEvent(
    override val action: MilkyAction
) : MilkyEvent
