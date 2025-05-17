/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:40 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 拒绝请求
 */
@Serializable
internal data class RejectRequestAPI(
    @SerialName("request_id")
    val requestId: String,
    val reason: String
)