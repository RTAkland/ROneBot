/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/31/25, 2:12 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.event.message

import cn.rtast.rob.milky.event.ws.raw.RawMessageReceiveEvent
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 合并转发消息
 */
@Serializable
public data class IncomingForwardedMessage(
    /**
     * 发送者名称
     */
    @SerialName("sender_name")
    val senderName: String,
    /**
     * 发送者头像 URL
     */
    @SerialName("avatar_url")
    val avatarURL: String,
    /**
     * 消息 Unix 时间戳（秒）
     */
    val time: Long,
    /**
     * 消息段列表
     */
    val segments: List<RawMessageReceiveEvent.IncomingMessage>
)