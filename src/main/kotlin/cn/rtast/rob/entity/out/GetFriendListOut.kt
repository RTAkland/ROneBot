/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity.out

import cn.rtast.rob.enums.internal.MessageEchoType

internal data class GetFriendListOut(
    val action: String = "get_friend_list",
    val echo: MessageEchoType = MessageEchoType.GetFriendList,
)