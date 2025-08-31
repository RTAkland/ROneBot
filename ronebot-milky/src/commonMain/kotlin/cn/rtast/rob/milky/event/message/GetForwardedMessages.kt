/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:59 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.message

import cn.rtast.rob.milky.enums.internal.ApiStatus
import cn.rtast.rob.milky.segment.ReceiveSegment
import kotlinx.serialization.Serializable

/**
 * 获取合并转发消息内容
 */
@Serializable
public data class GetForwardedMessages(
    val data: ForwardedMessages?,
    val status: ApiStatus,
    val message: String?
) {
    @Serializable
    public data class ForwardedMessages(
        /**
         * 转发消息内容
         */
        val messages: List<IncomingForwardedMessage>
    )
}