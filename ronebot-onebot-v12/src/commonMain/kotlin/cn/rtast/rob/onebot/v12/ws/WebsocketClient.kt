/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/18 20:07
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12.ws

import cn.rtast.rob.onebot.v12.BotInstance
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*
import io.ktor.client.request.*
import io.ktor.websocket.*

internal class WebsocketClient(
    private val botInstance: BotInstance
) {

    val client = HttpClient(nativeEngine) {
        install(WebSockets)
    }
    lateinit var clientSession: DefaultClientWebSocketSession

    suspend fun createClient(address: String, accessToken: String) {
        return client.webSocket("$address?access_token=$accessToken", request = {
            header("Authorization", "Bearer $accessToken")
        }) {
            clientSession = this
            botInstance.messageHandler.onOpen()
            try {
                for (frame in incoming) {
                    frame as? Frame.Text ?: continue
                    botInstance.messageHandler.onMessage(frame.readText())
                }
            } catch (e: Exception) {
                botInstance.messageHandler.onError(e)
            }
            botInstance.messageHandler.onClose()
        }
    }
}