/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 4:20 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.system

import cn.rtast.rob.milky.event.common.Friend
import kotlinx.serialization.Serializable

/**
 * 获取好友列表
 */
@Serializable
public data class GetFriendList(
    val data: List<Friend>
)