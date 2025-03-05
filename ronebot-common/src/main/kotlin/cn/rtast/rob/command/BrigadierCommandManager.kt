/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */

@file:Suppress("unused")

package cn.rtast.rob.command

import cn.rtast.rob.BaseBotInstance
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.enums.BrigadierMessageType
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder

public interface BrigadierCommandManager<C : ICommandSource, B : BaseBotInstance> {
    /**
     * 命令分发器
     */
    public val dispatcher: CommandDispatcher<C>

    /**
     * 所有Bot实例
     */
    public val botInstances: List<B>

    /**
     * 注册命令
     */
    public fun register(command: IBrigadierCommand<C>) {
        command.register(dispatcher)
    }

    /**
     * 注册一个封装后的命令
     */
    public fun register(node: LiteralArgumentBuilder<C>)

    /**
     * 注册一个封装后的命令
     * 但是可以添加指令别名
     */
    public fun register(node: LiteralArgumentBuilder<C>, alias: List<String>)

    /**
     * 执行命令
     */
    public fun execute(command: String, message: IMessage, messageType: BrigadierMessageType)
}