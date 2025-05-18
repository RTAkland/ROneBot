/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:55 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.message

import cn.rtast.rob.milky.enums.internal.ApiStatus
import cn.rtast.rob.milky.event.common.Message
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取私聊历史消息
 */
@Serializable
public data class GetHistoryPrivateMessage(
    val data: PrivateHistoryMessage?,
    val status: ApiStatus,
    val message: String?
) {
    @Serializable
    public data class PrivateHistoryMessage(
        /**
         * 消息列表
         */
        val messages: List<Message>,
        /**
         * 下一页起始消息序列号
         */
        @SerialName("next_start_message_seq")
        val nextStartMessageSeq: String
    )
}