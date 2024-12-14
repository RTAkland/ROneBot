/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.util

import cn.rtast.rob.BotInstance
import cn.rtast.rob.command.BrigadierCommandManager
import com.mojang.brigadier.CommandDispatcher

class BrigadierCommandManagerImpl internal constructor(
    override val botInstances: List<BotInstance>
) : BrigadierCommandManager<BotInstance> {

    override val dispatcher = CommandDispatcher<BotInstance>()
    override fun execute(command: String) {
        try {
            botInstances.forEach {
                dispatcher.execute(command, it)
            }
        } catch (_: Exception) {
        }
    }
}