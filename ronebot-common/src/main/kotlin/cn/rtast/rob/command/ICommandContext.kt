/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.command

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.enums.BrigadierMessageType

/**
 * 指令上下文接口
 */
interface ICommandContext {
    val botInstance: BaseBotInstance
    val message: IMessage
    val messageType: BrigadierMessageType
}