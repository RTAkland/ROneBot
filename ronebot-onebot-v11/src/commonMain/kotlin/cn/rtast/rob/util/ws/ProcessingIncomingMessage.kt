/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/10
 */

package cn.rtast.rob.util.ws

import cn.rtast.rob.IBotInstance
import cn.rtast.rob.event.dispatchEvent
import cn.rtast.rob.event.packed.MessageTimeoutEvent
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.util.MessageHandler
import kotlinx.coroutines.*
import kotlin.time.Duration

public val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.IO)

internal fun processIncomingMessage(
    botInstance: IBotInstance,
    listener: OneBotListener,
    message: String,
    executeDuration: Duration,
    messageHandler: MessageHandler
) {
    coroutineScope.launch {
        if (executeDuration.inWholeMilliseconds == 0L) {
            messageHandler.onMessage(listener, message)
        } else {
            try {
                withTimeout(executeDuration) {
                    messageHandler.onMessage(listener, message)
                }
            } catch (_: TimeoutCancellationException) {
                val event = MessageTimeoutEvent(botInstance.action, message)
                botInstance.dispatchEvent(event)
                listener.onMessageTimeout(event)
            }
        }
    }
}