/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/7 20:16
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.command

import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.RequiredArgumentBuilder

public interface Commands {
    public companion object {
        @JvmStatic
        public fun literal(literal: String): LiteralArgumentBuilder<CommandSource> {
            return LiteralArgumentBuilder.literal<CommandSource>(literal)
        }

        @JvmStatic
        public fun <T> argument(
            name: String,
            argumentType: ArgumentType<T>
        ): RequiredArgumentBuilder<CommandSource, T> {
            return RequiredArgumentBuilder.argument<CommandSource, T>(name, argumentType)
        }
    }
}