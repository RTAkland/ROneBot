/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity.metadata

import cn.rtast.rob.enums.MessageEchoType

data class ResponseMessage(
    val echo: MessageEchoType?,
)