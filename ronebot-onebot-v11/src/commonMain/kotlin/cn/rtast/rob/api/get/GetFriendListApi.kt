/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.api.get

import java.util.*

internal data class GetFriendListApi(
    val echo: UUID,
    val action: String = "get_friend_list",
)