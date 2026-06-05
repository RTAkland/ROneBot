/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */


package cn.rtast.rob.api.get

import kotlinx.serialization.Serializable
import kotlin.uuid.Uuid

@Serializable
internal data class CanSendRecordApi(
    val echo: Uuid,
    val action: String = "can_send_record",
)