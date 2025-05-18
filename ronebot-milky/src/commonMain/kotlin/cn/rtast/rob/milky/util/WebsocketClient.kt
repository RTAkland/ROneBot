/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 10:40 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.milky.util

import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.milky.BotInstance
import cn.rtast.rob.milky.event.ws.packet.RawMessageEvent
import cn.rtast.rob.milky.event.ws.packet.WebsocketConnectedEvent
import cn.rtast.rob.milky.event.ws.packet.WebsocketDisconnectedEvent
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*

internal suspend fun BotInstance.connectToEventEndpoint() {
    val wsAddress = if (address.startsWith("http://")) "ws://${address.removePrefix("http://")}/event"
    else if (address.startsWith("https://")) "wss://${address.removePrefix("https://")}/event"
    else throw IllegalArgumentException("$address 不是一个正确的URI")
    httpClient.webSocket(wsAddress) {
        val connectedEvent = WebsocketConnectedEvent(action)
        listener.onConnected(connectedEvent)
        listener.onConnectedJvm(connectedEvent)
        for (frame in incoming) {
            frame as? Frame.Text ?: continue
            val content = frame.readText()
            val rawMessageEvent = RawMessageEvent(action, content)
            listener.onRawMessage(rawMessageEvent)
            listener.onRawMessageJvm(rawMessageEvent)
            handleDispatchEvent(content)
        }
        val disconnectedEvent = WebsocketDisconnectedEvent(action)
        listener.onDisconnected(disconnectedEvent)
        listener.onDisconnectedJvm(disconnectedEvent)
    }
}