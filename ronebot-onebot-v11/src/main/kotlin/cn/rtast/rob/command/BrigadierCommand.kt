/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/12/14
 */


package cn.rtast.rob.command

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder

abstract class BrigadierCommand : IBrigadierCommand<CommandSource>

object Commands {
    fun literal(literal: String): LiteralArgumentBuilder<CommandSource> {
        return LiteralArgumentBuilder.literal<CommandSource>(literal)
    }

    fun <T> argument(name: String, argumentType: ArgumentType<T>): RequiredArgumentBuilder<CommandSource, T> {
        return RequiredArgumentBuilder.argument<CommandSource, T>(name, argumentType)
    }
}