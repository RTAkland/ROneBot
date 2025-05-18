/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:52 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.message

import cn.rtast.rob.milky.enums.internal.ApiStatus
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 发送消息后返回的消息序列
 */
@Serializable
public data class SendMessageResponse(
    val data: SendMessage?,
    val status: ApiStatus,
    val message: String?
) {
    @Serializable
    public data class SendMessage(
        /**
         * 消息序列
         */
        @SerialName("message_seq")
        val messageSeq: Long,
        /**
         * 时间戳
         * UNIX 秒
         */
        val time: Long
    )
}