/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.command

import cn.rtast.rob.BotInstance
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.entity.IFiredUser
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.event.raw.PrivateMessage
import cn.rtast.rob.enums.BrigadierMessageType

/**
 * 指令上下文
 */
public data class CommandSource(
    override val botInstance: BotInstance,
    override val message: IMessage,
    override val messageType: BrigadierMessageType,
    override val groupMessage: GroupMessage?,
    override val privateMessage: PrivateMessage?,
    override val firedUser: IFiredUser
) : ICommandSource