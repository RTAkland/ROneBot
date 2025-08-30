/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/30/25, 10:05 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.api.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 撤回私聊消息
 */
@Serializable
internal data class RecallPrivateMessageAPI(
    @SerialName("user_id")
    val userId: Long,
    @SerialName("message_seq")
    val messageSeq: Long
)