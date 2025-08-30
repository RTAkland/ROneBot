/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 8/30/25, 10:29 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.event.friend

import cn.rtast.rob.milky.enums.internal.ApiStatus
import cn.rtast.rob.milky.event.ws.raw.RawFriendRequestEvent
import kotlinx.serialization.Serializable

/**
 * 获取好友请求列表
 */
@Serializable
public data class GetFriendRequests(
    val data: List<RawFriendRequestEvent.FriendRequest>?,
    val status: ApiStatus,
    val message: String?
)