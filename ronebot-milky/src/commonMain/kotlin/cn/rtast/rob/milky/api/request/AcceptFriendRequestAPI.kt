/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/30/25, 10:41 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.api.request

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * 同意好友请求
 */
@Serializable
internal data class AcceptFriendRequestAPI(
    @SerialName("initiator_uid")
    val initiatorUID: String,
    @SerialName("is_filtered")
    val isFiltered: Boolean
)