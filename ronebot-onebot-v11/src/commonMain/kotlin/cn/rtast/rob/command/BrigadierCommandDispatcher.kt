/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/7 22:05
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.command

import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.enums.MessageType

public expect fun dispatchBrigadierCommand(commandString: String, message: IMessage, type: MessageType)