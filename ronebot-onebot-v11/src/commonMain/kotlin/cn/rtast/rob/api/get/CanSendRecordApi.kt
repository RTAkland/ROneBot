/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */


package cn.rtast.rob.api.get

import java.util.*

internal data class CanSendRecordApi(
    val echo: UUID,
    val action: String = "can_send_record",
)