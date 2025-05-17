/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:36 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.api.friend

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 发送名片点赞
 */
@Serializable
internal data class SendProfileLikeAPI(
    @SerialName("user_id")
    val userId: Long,
    val count: Int = 1
)