/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.util

import cn.rtast.rob.BotInstance
import cn.rtast.rob.command.ICommandSource
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.enums.BrigadierMessageType

/**
 * 指令上下文
 */
data class CommandSource(
    override val botInstance: BotInstance,
    override val message: IMessage,
    override val messageType: BrigadierMessageType,
    override val groupMessage: GroupMessage?,
    override val privateMessage: PrivateMessage?
) : ICommandSource