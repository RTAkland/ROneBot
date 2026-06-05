/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.api.get

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
internal data class GetFriendListApi(
    val echo: Uuid,
    val action: String = "get_friend_list",
)