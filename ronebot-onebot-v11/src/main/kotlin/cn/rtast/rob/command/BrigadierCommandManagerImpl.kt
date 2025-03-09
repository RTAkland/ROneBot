/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.command

import cn.rtast.rob.BotInstance
import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.event.raw.FiredUser
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.event.raw.PrivateMessage
import cn.rtast.rob.enums.BrigadierMessageType
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder

public class BrigadierCommandManagerImpl : BrigadierCommandManager<CommandSource, BotInstance> {

    private val botInstances: List<BotInstance> get() = OneBotFactory.botManager.allBots()

    override val dispatcher: CommandDispatcher<CommandSource> = CommandDispatcher<CommandSource>()

    override fun register(node: LiteralArgumentBuilder<CommandSource>) {
        dispatcher.register(node)
    }

    override fun register(node: LiteralArgumentBuilder<CommandSource>, alias: List<String>) {
        dispatcher.register(node)
        alias.forEach {
            dispatcher.register(LiteralArgumentBuilder.literal<CommandSource>(it).redirect(node.build()))
        }
    }

    override fun execute(command: String, message: IMessage, messageType: BrigadierMessageType) {
        try {
            botInstances.forEach {
                val context = when (messageType) {
                    BrigadierMessageType.Group -> CommandSource(
                        it,
                        message,
                        messageType,
                        message as GroupMessage,
                        null,
                        FiredUser(message.sender.userId.toString(), true, message.groupId)
                    )

                    BrigadierMessageType.Private -> CommandSource(
                        it,
                        message,
                        messageType,
                        null,
                        message as PrivateMessage,
                        FiredUser(message.sender.userId.toString(), false, null),
                    )
                }
                dispatcher.execute(command, context)
            }
        } catch (_: Exception) {
        }
    }
}