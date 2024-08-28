/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/27
 */


package cn.rtast.rob.entity.out

import cn.rtast.rob.enums.MessageEchoType

internal data class GetVersionInfo(
    val action: String = "get_version_info",
    val echo: MessageEchoType = MessageEchoType.GetVersionInfo
)
