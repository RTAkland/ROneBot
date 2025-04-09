/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/7 20:15
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(JvmOnly::class)

package cn.rtast.rob.command

import cn.rtast.jvmonly.linter.JvmOnly
import cn.rtast.rob.BotInstance
import cn.rtast.rob.entity.IMessage
import cn.rtast.rob.enums.BrigadierMessageType
import cn.rtast.rob.enums.MessageType
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage
import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder


@JvmOnly
public class BrigadierCommandManagerImpl(private val botInstances: List<BotInstance>) {
    private val dispatcher = CommandDispatcher<CommandSource>()

    @JvmOnly
    @JvmOverloads
    public fun register(node: LiteralArgumentBuilder<CommandSource>, alias: List<String> = listOf()) {
        dispatcher.register(node)
        alias.forEach { dispatcher.register(LiteralArgumentBuilder.literal<CommandSource>(it).redirect(node.build())) }
    }

    @JvmOnly
    @JvmSynthetic
    public fun dispatch(commandString: String, message: IMessage, type: MessageType) {
        val messageType = BrigadierMessageType.fromMessageType(type)
        botInstances.forEach {
            try {
                val context = when (messageType) {
                    BrigadierMessageType.Group -> CommandSource(
                        it,
                        message,
                        messageType,
                        message as GroupMessage,
                        null,
                        message.sender.userId
                    )

                    BrigadierMessageType.Private -> CommandSource(
                        it,
                        message,
                        messageType,
                        null,
                        message as PrivateMessage,
                        message.sender.userId
                    )
                }
                dispatcher.execute(commandString, context)
            } catch (_: Exception) {
            }
        }
    }
}