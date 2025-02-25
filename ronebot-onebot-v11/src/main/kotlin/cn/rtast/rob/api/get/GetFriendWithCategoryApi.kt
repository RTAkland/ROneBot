/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.api.get

import java.util.*

internal data class GetFriendWithCategoryApi(
    val action: String = "get_friend_with_category",
    val echo: UUID
)