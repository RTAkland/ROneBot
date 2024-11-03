/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.entity.out.llonebot

import java.util.UUID

internal data class GetFriendWithCategoryOut(
    val action: String = "get_friend_with_category",
    val echo: UUID
)