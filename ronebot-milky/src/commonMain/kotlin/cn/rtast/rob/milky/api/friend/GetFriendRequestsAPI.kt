/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/30/25, 10:26 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.api.friend

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 获取好友请求列表
 */
@Serializable
internal data class GetFriendRequestsAPI(
    val limit: Int = 20,
    /**
     * `true` 表示只获取被过滤（由风险账号发起）的通知，`false` 表示只获取未被过滤的通知
     */
    @SerialName("is_filtered")
    val isFiltered: Boolean = false
)