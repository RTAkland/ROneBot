/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.util

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.command.ICommandContext
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.enums.BrigadierMessageType

/**
 * 指令上下文
 */
data class CommandContext(
    override val botInstance: BaseBotInstance,
    override val message: IMessage,
    override val messageType: BrigadierMessageType
) : ICommandContext