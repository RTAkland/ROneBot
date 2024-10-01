/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.entity.out

import cn.rtast.rob.enums.internal.MessageEchoType

internal data class GetLoginInfoOut(
    val action: String = "get_login_info",
    val echo: MessageEchoType = MessageEchoType.GetLoginInfo
)