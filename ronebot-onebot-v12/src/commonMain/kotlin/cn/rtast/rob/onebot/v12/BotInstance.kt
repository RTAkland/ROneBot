/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/18 19:45
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.onebot.v12

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.onebot.v12.enums.InstanceType
import cn.rtast.rob.onebot.v12.onebot12.OneBot12Action
import cn.rtast.rob.onebot.v12.onebot12.OneBot12Listener
import cn.rtast.rob.onebot.v12.util.MessageHandler
import cn.rtast.rob.onebot.v12.ws.WebsocketClient
import cn.rtast.rob.util.getLogger

public class BotInstance internal constructor(
    private val address: String,
    private val accessToken: String,
    private val instanceType: InstanceType,
    private val listener: OneBot12Listener,
    private val logLevel: LogLevel
) : BaseBotInstance {

    internal val logger = getLogger(if (instanceType == InstanceType.Client) "[C]" else "S")
        .apply { setLoggingLevel(logLevel) }

    internal val messageHandler = MessageHandler(this, listener)

    public val action: OneBot12Action = OneBot12Action(this)

    override suspend fun createBot(): BotInstance {
        when (instanceType) {
            InstanceType.Client -> WebsocketClient(this).createClient(address, accessToken)
            InstanceType.Server -> TODO()
        }
        return this
    }

    override suspend fun disposeBot() {
        TODO("Not yet implemented")
    }
}