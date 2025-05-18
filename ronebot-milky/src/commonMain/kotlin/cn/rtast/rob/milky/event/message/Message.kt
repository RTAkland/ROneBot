/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:13 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.message

import cn.rtast.rob.milky.enums.MessageScene
import cn.rtast.rob.milky.segment.ReceiveSegment
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 接受的消息的结构
 */
@Serializable
public data class Message(
    /**
     * 消息场景
     */
    @SerialName("message_scene")
    val messageScene: MessageScene,
    /**
     * 好友 QQ 号或群号
     */
    @SerialName("peer_id")
    val peerId: Long,
    /**
     * 消息ID
     */
    @SerialName("message_seq")
    val messageSeq: Long,
    /**
     * 发送者qq号
     */
    @SerialName("sender_id")
    val senderId: Long,
    /**
     * 消息时间戳
     * UNIX 秒
     */
    val time: Long,
    /**
     * 消息段列表
     */
    val segments: List<ReceiveSegment>
)