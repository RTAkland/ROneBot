/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */


package cn.rtast.rob.entity.out

import cn.rtast.rob.enums.internal.MessageEchoType

internal data class CanSendRecordOut(
    val action: String = "can_send_record",
    val echo: MessageEchoType = MessageEchoType.CanSendRecord
)