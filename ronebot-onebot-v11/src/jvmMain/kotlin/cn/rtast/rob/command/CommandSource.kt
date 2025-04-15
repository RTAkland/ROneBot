/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/7 20:18
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.command

import cn.rtast.rob.BotInstance
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.enums.BrigadierMessageType
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage

public data class CommandSource(
    val botInstance: BotInstance,
    val message: IMessage,
    val messageType: BrigadierMessageType,
    val groupMessage: GroupMessage?,
    val privateMessage: PrivateMessage?,
    val firedUser: Long
)