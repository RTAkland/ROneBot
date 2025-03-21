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

/**
 * 定义一个任意类型输入的Brigadier类型
 * 输入任意类型最后都会解析成字符串[String]
 */
public class AnyStringArgumentType : ArgumentType<Any> {
    override fun parse(reader: StringReader): String {
        return reader.readUnquotedString().toString()
    }

    public companion object {
        public fun anyStringType(): AnyStringArgumentType {
            return AnyStringArgumentType()
        }

        public fun getAnyString(context: CommandContext<*>, name: String): String {
            return context.getArgument(name, Any::class.java).toString()
        }
    }
}