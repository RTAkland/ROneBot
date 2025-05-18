/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 10:40 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.milky.util

import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.event.dispatchEvent
import cn.rtast.rob.milky.BotInstance
import cn.rtast.rob.milky.event.ws.packed.RawMessageEvent
import cn.rtast.rob.milky.event.ws.packed.WebsocketConnectedEvent
import cn.rtast.rob.milky.event.ws.packed.WebsocketDisconnectedEvent
import io.ktor.client.plugins.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.delay

internal suspend fun BotInstance.connectToEventEndpoint() {
    val wsAddress = when {
        address.startsWith("http://") -> "ws://${address.removePrefix("http://")}/event"
        address.startsWith("https://") -> "wss://${address.removePrefix("https://")}/event"
        else -> throw IllegalArgumentException("$address 不是一个正确的URI")
    }
    while (true) {
        try {
            httpClient.webSocket("$wsAddress${if (accessToken != null) "?access_token=$accessToken" else ""}") {
                val connectedEvent = WebsocketConnectedEvent(action)
                this@connectToEventEndpoint.dispatchEvent(connectedEvent)
                listener.onConnected(connectedEvent)
                listener.onConnectedJvm(connectedEvent)
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    val content = frame.readText()
                    logger.debug(content)
                    val rawMessageEvent = RawMessageEvent(action, content)
                    this@connectToEventEndpoint.dispatchEvent(rawMessageEvent)
                    listener.onRawMessage(rawMessageEvent)
                    listener.onRawMessageJvm(rawMessageEvent)
                    handleDispatchEvent(content)
                }
                val disconnectedEvent = WebsocketDisconnectedEvent(action)
                this@connectToEventEndpoint.dispatchEvent(disconnectedEvent)
                listener.onDisconnected(disconnectedEvent)
                listener.onDisconnectedJvm(disconnectedEvent)
            }
        } catch (e: Exception) {
            logger.error("连接失败,5秒后重试", e)
        }
        delay(5000)
    }
}
