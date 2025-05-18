/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 10:45 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.milky

import cn.rtast.rob.milky.event.ws.packet.RawMessageEvent
import cn.rtast.rob.milky.event.ws.packet.WebsocketConnectedEvent
import cn.rtast.rob.milky.event.ws.packet.WebsocketDisconnectedEvent
import kotlin.jvm.JvmSynthetic

public interface MilkyListener {
    /**
     * websocket连接上服务器时
     */
    @JvmSynthetic
    public suspend fun onConnected(event: WebsocketConnectedEvent) {
    }

    public fun onConnectedJvm(event: WebsocketConnectedEvent) {}

    /**
     * websocket连接从服务器断开时
     */
    @JvmSynthetic
    public suspend fun onDisconnected(event: WebsocketDisconnectedEvent) {
    }

    public fun onDisconnectedJvm(event: WebsocketDisconnectedEvent) {}

    /**
     * websocket服务器下发原始(raw)消息时
     */
    @JvmSynthetic
    public suspend fun onRawMessage(event: RawMessageEvent) {
    }

    public fun onRawMessageJvm(event: RawMessageEvent) {}
}