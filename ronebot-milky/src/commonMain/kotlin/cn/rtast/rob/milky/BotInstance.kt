/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 2:54 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.milky.milky.MilkyAction
import cn.rtast.rob.milky.util.http.clientEngine
import io.ktor.client.*
import io.ktor.client.plugins.websocket.*

public class BotInstance internal constructor(
    public val address: String,
    public val accessToken: String?
) : BaseBotInstance {
    public val action: MilkyAction = MilkyAction(this)

    @InternalROneBotApi
    public val httpClient: HttpClient = HttpClient(clientEngine) {
        install(WebSockets)
    }

    override suspend fun createBot(): BotInstance {
        return this
    }

    override suspend fun disposeBot() {
    }
}