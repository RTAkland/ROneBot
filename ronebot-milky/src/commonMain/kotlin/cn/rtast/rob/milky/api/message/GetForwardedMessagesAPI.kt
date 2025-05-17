/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:35 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.message

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取合并转发消息内容
 */
@Serializable
internal data class GetForwardedMessagesAPI(
    @SerialName("forward_id")
    val forwardId: String,
)