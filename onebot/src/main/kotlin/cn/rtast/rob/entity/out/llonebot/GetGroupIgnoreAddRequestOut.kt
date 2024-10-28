/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package cn.rtast.rob.entity.out.llonebot

import cn.rtast.rob.enums.internal.MessageEchoType

internal data class GetGroupIgnoreAddRequestOut(
    val action: String = "get_group_ignore_add_request",
    val echo: MessageEchoType = MessageEchoType.GetGroupIgnoreAddRequest
)