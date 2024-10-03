/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/2
 */


package cn.rtast.rob.entity.out.lagrange.get

import cn.rtast.rob.enums.internal.MessageEchoType

internal data class GetCSRFTokenOut(
    val action: String = "get_csrf_token",
    val echo: MessageEchoType = MessageEchoType.GetCSRFToken
)