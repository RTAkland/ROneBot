/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/7 22:06
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(JvmOnly::class)

package cn.rtast.rob.command

import cn.rtast.rob.annotations.JvmOnly
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.enums.MessageType

public actual fun dispatchBrigadierCommand(
    commandString: String,
    message: IMessage,
    type: MessageType
) {
    BrigadierCommandManager.getBrigadierCommandManager().dispatch(commandString, message, type)
}