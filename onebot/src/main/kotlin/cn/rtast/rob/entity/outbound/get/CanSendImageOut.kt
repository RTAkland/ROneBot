/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */


package cn.rtast.rob.entity.outbound.get

import java.util.UUID

internal data class CanSendImageOut(
    val echo: UUID,
    val action: String = "can_send_image"
)