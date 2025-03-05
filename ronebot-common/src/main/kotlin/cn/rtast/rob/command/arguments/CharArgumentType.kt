/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/16
 */

@file:Suppress("unused")

package cn.rtast.rob.command.arguments

import com.mojang.brigadier.StringReader
import com.mojang.brigadier.arguments.ArgumentType
import com.mojang.brigadier.context.CommandContext
import com.mojang.brigadier.exceptions.CommandSyntaxException

/**
 * [Char]类型的Brigadier输入参数
 */
public class CharArgumentType : ArgumentType<Char> {
    @Throws(CommandSyntaxException::class)
    override fun parse(reader: StringReader): Char {
        return reader.readChar()
    }

    public companion object {
        public fun char(): CharArgumentType {
            return CharArgumentType()
        }

        public fun getChar(context: CommandContext<*>, name: String): Char {
            return context.getArgument(name, Char::class.java)
        }
    }
}