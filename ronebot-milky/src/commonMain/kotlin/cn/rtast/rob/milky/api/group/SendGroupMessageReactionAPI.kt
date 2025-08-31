/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:39 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 发送消息回应reaction
 */
@Serializable
internal data class SendGroupMessageReactionAPI(
    @SerialName("group_id")
    val groupId: Long,
    @SerialName("message_seq")
    val messageSeq: Long,
    /**
     * 表情 ID
     */
    val reaction: String,
    @SerialName("is_add")
    val isAdd: Boolean = true
)