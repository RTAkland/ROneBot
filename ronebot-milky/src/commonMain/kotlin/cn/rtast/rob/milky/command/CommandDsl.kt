/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/8/25, 10:40 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.milky.command

import cn.rtast.rob.milky.MilkyBotFactory
import kotlinx.coroutines.runBlocking

/**
 * 为了使用到Kotlin DSL风格而创建的类
 * 没有任何实际作用
 */
public class Command internal constructor(
    internal val aliases: List<String>,
    internal val block: suspend (CommandRequest) -> Unit,
    internal val type: List<BaseCommand.ExecuteType>,
)

/**
 * 创建一个命令
 * @param aliases 指令别名列表
 * @param types 可触发的聊天情景列表, 留空为任何聊天都能触发
 * @param block 指令内部lambda
 */
public fun createCommand(
    aliases: List<String>,
    types: List<BaseCommand.ExecuteType>,
    block: suspend (CommandRequest) -> Unit,
): Command = Command(aliases, block, types)

/**
 * 创建一个命令, 适用于所有的聊天场景
 * @param aliases 指令别名列表
 * @param block 指令内部lambda
 */
public fun createCommand(
    aliases: List<String>,
    block: suspend (CommandRequest) -> Unit,
): Command = Command(aliases, block, listOf())

/**
 * 创建一个命令
 * @param aliases 指令别名
 * @param type 可触发的聊天情景
 * @param block 指令内部lambda
 */
public fun createCommand(
    aliases: List<String>,
    type: BaseCommand.ExecuteType,
    block: suspend (CommandRequest) -> Unit,
): Command = Command(aliases, block, listOf(type))

/**
 * 创建一个命令
 * @param command 指令名称
 * @param types 可触发的聊天情景列表, 留空为任何聊天都能触发
 * @param block 指令内部lambda
 */
public fun createCommand(
    command: String,
    types: List<BaseCommand.ExecuteType>,
    block: suspend (CommandRequest) -> Unit,
): Command = Command(listOf(command), block, types)

/**
 * 创建一个命令, 适用于所有聊天场景
 * @param command 指令名称
 * @param block 指令内部lambda
 */
public fun createCommand(
    command: String,
    block: suspend (CommandRequest) -> Unit,
): Command = Command(listOf(command), block, listOf())

/**
 * 创建一个命令
 * @param command 指令名称
 * @param type 可触发的聊天情景
 * @param block 指令内部lambda
 */
public fun createCommand(
    command: String,
    type: BaseCommand.ExecuteType,
    block: suspend (CommandRequest) -> Unit,
): Command = Command(listOf(command), block, listOf(type))

/**
 * 注册指令
 * @sample createCommand("/hello") { println("Hello world") }.register()
 */
public fun Command.register(): Unit =
    runBlocking { MilkyBotFactory.commandManager.registerCommandByDsl(aliases, type, block) }