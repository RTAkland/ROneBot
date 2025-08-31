/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/31/25, 2:20 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.api.group

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class SetGroupEssenceMessageAPI(
    @SerialName("group_id")
    val groupId: Long,
    /**
     * 消息序列号
     */
    @SerialName("message_seq")
    val messageSeq: Long,
    /**
     * 是否设置为精华消息，`false` 表示取消精华
     */
    @SerialName("is_set")
    val isSet: Boolean = true
)