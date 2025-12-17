/*
 * Copyright © 2025 RTAkland
 * Date: 2025/12/16 18:32
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.serverless

import cn.rtast.rob.BotInstance
import cn.rtast.rob.annotations.InternalROneBotApi
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*

public class ServerlessSender : ServerlessWebsocketClient<BotInstance> {
    private val client = HttpClient(CIO)
    override suspend fun serverlessSend(message: String, bot: BotInstance) {
        client.post(bot.forwarderHost!!) { setBody(message) }
    }
}

