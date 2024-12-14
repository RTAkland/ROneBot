/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.util

import cn.rtast.rob.BotInstance
import cn.rtast.rob.command.BrigadierCommandManager
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.enums.BrigadierMessageType
import com.mojang.brigadier.CommandDispatcher

class BrigadierCommandManagerImpl internal constructor(
    override val botInstances: List<BotInstance>
) : BrigadierCommandManager<CommandContext, BotInstance> {

    override val dispatcher = CommandDispatcher<CommandContext>()
    override fun execute(command: String, message: IMessage, messageType: BrigadierMessageType) {
        try {
            botInstances.forEach {
                val context = CommandContext(it, message, messageType)
                dispatcher.execute(command, context)
            }
        } catch (_: Exception) {
        }
    }
}