/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:04 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.raw

import arrow.core.Either
import cn.rtast.rob.milky.actionable.MessageActionable
import cn.rtast.rob.milky.enums.MessageScene
import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.event.message.SendMessageResponse
import cn.rtast.rob.milky.milky.MessageChain
import cn.rtast.rob.milky.milky.MilkyAction
import cn.rtast.rob.milky.milky.messageChain
import cn.rtast.rob.milky.milky.text
import cn.rtast.rob.milky.segment.ReceiveSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import love.forte.plugin.suspendtrans.annotation.JvmAsync
import love.forte.plugin.suspendtrans.annotation.JvmBlocking

/**
 * 消息接收Json解析
 */
@Serializable
public data class RawMessageReceiveEvent(
    val data: IncomingMessage,
    @SerialName("event_type")
    val eventType: MilkyEvents,
) {
    @Serializable
    public data class IncomingMessage(
        /**
         * 好友 QQ 号或群号
         */
        @SerialName("peer_id")
        val peerId: Long,
        /**
         * 消息序列号
         */
        @SerialName("message_seq")
        val messageSeq: Long,
        /**
         * 发送者 QQ 号
         */
        @SerialName("sender_id")
        val senderId: Long,
        /**
         * 消息 Unix 时间戳（秒）
         */
        val time: Long,
        /**
         * 消息段列表
         */
        val segments: List<ReceiveSegment>,
        /**
         * 类型标识符
         */
        @SerialName("message_scene")
        val messageScene: MessageScene
    ) : MessageActionable {
        @Transient
        lateinit var action: MilkyAction

        @JvmBlocking
        override suspend fun reply(message: MessageChain): Either<String, SendMessageResponse.SendMessage> {
            return when (messageScene) {
                MessageScene.Friend -> action.sendPrivateMessage(peerId, message)
                MessageScene.Group -> action.sendGroupMessage(peerId, message)
                MessageScene.Temp -> throw IllegalStateException()
            }
        }

        @JvmBlocking
        override suspend fun reply(text: Any): Either<String, SendMessageResponse.SendMessage> {
            val msg = messageChain { text(text) }
            return when (messageScene) {
                MessageScene.Friend -> action.sendPrivateMessage(peerId, msg)
                MessageScene.Group -> action.sendGroupMessage(peerId, msg)
                MessageScene.Temp -> throw IllegalStateException()
            }
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun reaction(faceId: String) {
            if (messageScene == MessageScene.Group) {
                action.reaction(peerId, messageSeq, faceId, true)
            }
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun unsetReaction(faceId: String) {
            if (messageScene == MessageScene.Group) {
                action.reaction(peerId, messageSeq, faceId, false)
            }
        }

        @JvmAsync
        @JvmBlocking
        override suspend fun recall() {
            action.recallMessage(messageScene, peerId, messageSeq)
        }
    }
}

