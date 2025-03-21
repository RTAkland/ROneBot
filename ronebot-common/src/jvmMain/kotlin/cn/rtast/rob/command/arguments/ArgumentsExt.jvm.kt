/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/1/19
 */

@file:Suppress("unused")


package cn.rtast.rob.command.arguments

import cn.rtast.rob.command.ICommandSource
import com.mojang.brigadier.context.CommandContext


/**
 * 快速的从命令上下文中获取一个[AnyStringArgumentType]类型的命令参数输入, 并返回一个[String]
 */
public fun <T : ICommandSource> CommandContext<T>.getAnyString(name: String): String {
    return this.getArgument(name, String::class.java)
}

/**
 * 快速的从命令上下文中获取一个[CharArgumentType]类型的命令参数输入, 并返回一个[String]
 */
public fun <T : ICommandSource> CommandContext<T>.getChar(name: String): Char {
    return this.getArgument(name, Char::class.java)
}

/**
 * 快速的从命令上下文中获取一个[com.mojang.brigadier.arguments.IntegerArgumentType]
 * 类型的命令参数输入, 并返回一个[Int]
 */
public fun <T : ICommandSource> CommandContext<T>.getInteger(name: String): Int {
    return this.getArgument(name, Int::class.java)
}

/**
 * 快速的从命令上下文中获取一个[com.mojang.brigadier.arguments.BoolArgumentType]
 * 类型的命令参数输入, 并返回一个[Boolean]
 */
public fun <T : ICommandSource> CommandContext<T>.getBoolean(name: String): Boolean {
    return this.getArgument(name, Boolean::class.java)
}

/**
 * 快速的从命令上下文中获取一个[com.mojang.brigadier.arguments.StringArgumentType]
 * 类型的命令参数输入, 并返回一个[String]
 */
public fun <T : ICommandSource> CommandContext<T>.getString(name: String): String {
    return this.getArgument(name, String::class.java)
}

/**
 * 快速的从命令上下文中获取一个[com.mojang.brigadier.arguments.LongArgumentType]
 * 类型的命令参数输入, 并返回一个[Long]
 */
public fun <T : ICommandSource> CommandContext<T>.getLong(name: String): Long {
    return this.getArgument(name, Long::class.java)
}

/**
 * 快速的从命令上下文中获取一个[com.mojang.brigadier.arguments.FloatArgumentType]
 * 类型的命令参数输入, 并返回一个[Float]
 */
public fun <T : ICommandSource> CommandContext<T>.getFloat(name: String): Float {
    return this.getArgument(name, Float::class.java)
}

/**
 * 快速的从命令上下文中获取一个[com.mojang.brigadier.arguments.DoubleArgumentType]
 * 类型的命令参数输入, 并返回一个[Double]
 */
public fun <T : ICommandSource> CommandContext<T>.getDouble(name: String): Double {
    return this.getArgument(name, Double::class.java)
}
