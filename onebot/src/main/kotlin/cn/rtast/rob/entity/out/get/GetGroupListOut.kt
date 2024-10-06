/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity.out.get

import cn.rtast.rob.enums.internal.MessageEchoType

internal data class GetGroupListOut(
    val action: String = "get_group_list",
    val echo: MessageEchoType = MessageEchoType.GetGroupList
)