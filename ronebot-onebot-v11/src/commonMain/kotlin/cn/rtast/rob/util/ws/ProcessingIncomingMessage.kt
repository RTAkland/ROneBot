/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/10
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.util.ws

import cn.rtast.rob.BotInstance
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.commonCoroutineScope
import cn.rtast.rob.event.dispatchEvent
import cn.rtast.rob.event.packed.MessageTimeoutEvent
import cn.rtast.rob.onebot.OneBotListener
import cn.rtast.rob.onebot.callEvent
import cn.rtast.rob.util.MessageHandler
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout
import kotlin.time.Duration

public fun processIncomingMessage(
    botInstance: BotInstance,
    listener: OneBotListener,
    message: String,
    executeDuration: Duration,
    messageHandler: MessageHandler,
) {
    commonCoroutineScope.launch {
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
                listener.callEvent(
                    suspendCall = { onMessageTimeout(event) },
                    blockingCall = { onMessageTimeoutBlocking(event) }
                )
            }
        }
    }
}