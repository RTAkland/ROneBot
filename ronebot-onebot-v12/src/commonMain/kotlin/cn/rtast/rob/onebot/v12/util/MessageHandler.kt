/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/18 19:45
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.onebot.v12.util

import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.onebot.v12.BotInstance
import cn.rtast.rob.onebot.v12.enums.internal.MessageType
import cn.rtast.rob.onebot.v12.enums.internal.MetaEventType
import cn.rtast.rob.onebot.v12.enums.internal.PostType
import cn.rtast.rob.onebot.v12.event.packed.WebsocketClosedEvent
import cn.rtast.rob.onebot.v12.event.packed.WebsocketErrorEvent
import cn.rtast.rob.onebot.v12.event.packed.WebsocketOpenEvent
import cn.rtast.rob.onebot.v12.event.raw.RawEvent
import cn.rtast.rob.onebot.v12.event.raw.message.RawGroupMessageEvent
import cn.rtast.rob.onebot.v12.event.raw.message.RawPrivateMessageEvent
import cn.rtast.rob.onebot.v12.event.raw.onebot.RawHeartbeatEvent
import cn.rtast.rob.onebot.v12.onebot12.OneBot12Listener
import cn.rtast.rob.util.fromJson

public class MessageHandler internal constructor(
    private val botInstance: BotInstance,
    private val listener: OneBot12Listener
) {
    public suspend fun onOpen() {
        val event = WebsocketOpenEvent(botInstance.action)
        listener.onWebsocketOpen(event)
    }

    public suspend fun onMessage(message: String) {
        listener.onRawMessage(message)
        val baseDeserializedMessage = message.fromJson<RawEvent>()
        when (baseDeserializedMessage.postType) {
            PostType.meta_event -> {
                if (baseDeserializedMessage.metaEventType!! == MetaEventType.heartbeat) {
                    val event = message.fromJson<RawHeartbeatEvent>().apply {
                        action = botInstance.action
                    }
                    listener.onHeartbeat(event)
                }
            }

            PostType.message -> {
                when (baseDeserializedMessage.messageType!!) {
                    MessageType.group -> {
                        val event = message.fromJson<RawGroupMessageEvent>().apply {
                            action = botInstance.action
                            sender.action = botInstance.action
                        }
                        listener.onGroupMessage(event)
                    }

                    MessageType.private -> {
                        val event = message.fromJson<RawPrivateMessageEvent>().apply {
                            action = botInstance.action
                            sender.action = botInstance.action
                        }
                        listener.onPrivateMessage(event)
                    }
                }
            }
        }
    }

    public suspend fun onClose() {
        val event = WebsocketClosedEvent(botInstance.action)
        listener.onWebsocketClosed(event)
    }

    public suspend fun onError(exception: Exception) {
        val event = WebsocketErrorEvent(botInstance.action, exception)
        listener.onWebsocketError(event)
        exception.printStackTrace()
    }
}