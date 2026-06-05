/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/24
 */


package cn.rtast.rob.api.get

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
internal data class GetFriendWithCategoryApi(
    val action: String = "get_friends_with_category",
    val echo: Uuid,
)