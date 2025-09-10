/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/11/25, 2:55 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:JvmName("CommandUtil")


package cn.rtast.rob.milky.command

import cn.rtast.rob.milky.MilkyBotFactory
import kotlinx.coroutines.runBlocking

/**
 * Java使用的函数式接口
 */
public fun interface CommandBlock {
    public fun execute(message: CommandRequest)
}

/**
 * 将函数式接口转换成Kotlin Lambda
 */
public fun CommandBlock.asKotlinLambda(): suspend (CommandRequest) -> Unit = { msg ->
    this.execute(msg)
}

public data class JCommand(
    val aliases: List<String>,
    val block: CommandBlock,
    val type: List<BaseCommand.ExecuteType>,
) {
    public fun register(): Unit = runBlocking {
        MilkyBotFactory.commandManager.registerCommandByDsl(aliases, type, block.asKotlinLambda())
    }
}

/**
 * 创建一个命令
 * @param aliases 指令别名列表
 * @param types 可触发的聊天情景列表, 留空为任何聊天都能触发
 * @param block 指令内部lambda
 */
public fun createCommand(
    aliases: List<String>,
    types: List<BaseCommand.ExecuteType>,
    block: CommandBlock,
): JCommand = JCommand(aliases, block, types)

/**
 * 创建一个命令, 适用于所有的聊天场景
 * @param aliases 指令别名列表
 * @param block 指令内部lambda
 */
public fun createCommand(
    aliases: List<String>,
    block: CommandBlock,
): JCommand = JCommand(aliases, block, listOf())

/**
 * 创建一个命令
 * @param aliases 指令别名
 * @param type 可触发的聊天情景
 * @param block 指令内部lambda
 */
public fun createCommand(
    aliases: List<String>,
    type: BaseCommand.ExecuteType,
    block: CommandBlock,
): JCommand = JCommand(aliases, block, listOf(type))

/**
 * 创建一个命令
 * @param command 指令名称
 * @param types 可触发的聊天情景列表, 留空为任何聊天都能触发
 * @param block 指令内部lambda
 */
public fun createCommand(
    command: String,
    types: List<BaseCommand.ExecuteType>,
    block: CommandBlock,
): JCommand = JCommand(listOf(command), block, types)

/**
 * 创建一个命令, 适用于所有聊天场景
 * @param command 指令名称
 * @param block 指令内部lambda
 */
public fun createCommand(
    command: String,
    block: CommandBlock,
): JCommand = JCommand(listOf(command), block, listOf())

/**
 * 创建一个命令
 * @param command 指令名称
 * @param type 可触发的聊天情景
 * @param block 指令内部lambda
 */
public fun createCommand(
    command: String,
    type: BaseCommand.ExecuteType,
    block: CommandBlock,
): JCommand = JCommand(listOf(command), block, listOf(type))