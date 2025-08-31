/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 11:10 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.ws.raw

import cn.rtast.rob.milky.enums.MessageScene
import cn.rtast.rob.milky.enums.internal.MilkyEvents
import cn.rtast.rob.milky.milky.MilkyAction
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

/**
 * 撤回消息Json解析
 */
@Serializable
public data class RawMessageRecallEvent(
    val data: MessageRecall,
    @SerialName("event_type")
    val eventType: MilkyEvents,
) {
    @Serializable
    public data class MessageRecall(
        /**
         * 消息场景（可能值：friend, group, temp）
         */
        @SerialName("message_scene")
        val messageScene: MessageScene,
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
         * 被撤回的消息的发送者 QQ 号
         */
        @SerialName("sender_id")
        val senderId: Long,
        /**
         * 操作者 QQ 号
         */
        @SerialName("operator_id")
        val operatorId: Long,

        /**
         * 撤回提示的后缀文本
         * @sample: xxx撤回了一条消息, 因为有错别字
         * @sample `因为有错别字就是后缀`
         */
        @SerialName("display_suffix")
        val displaySuffix: String
    ) {
        @Transient
        lateinit var action: MilkyAction
    }
}