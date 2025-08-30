/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/30/25, 10:11 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.api.message

import cn.rtast.rob.milky.enums.MessageScene
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 将消息标记为已读
 */
@Serializable
internal data class MarkMessageAsReadAPI(
    @SerialName("message_scene")
    val messageScene: MessageScene,
    @SerialName("peer_id")
    val peerId: Long,
    @SerialName("message_seq")
    val messageSeq: Long
)