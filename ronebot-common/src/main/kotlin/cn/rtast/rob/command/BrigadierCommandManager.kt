/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.command

import cn.rtast.rob.BaseBotInstance
import com.mojang.brigadier.CommandDispatcher

interface BrigadierCommandManager<B : BaseBotInstance> {
    /**
     * 命令分发器
     */
    val dispatcher: CommandDispatcher<B>

    /**
     * 所有Bot实例
     */
    val botInstances: List<B>

    /**
     * 注册命令
     */
    fun register(command: IBrigadierCommand<B>) {
        command.register(dispatcher)
    }

    /**
     * 执行命令
     */
    fun execute(command: String)
}