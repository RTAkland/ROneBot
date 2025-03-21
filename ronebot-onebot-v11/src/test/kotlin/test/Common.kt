/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/29
 */

package test

import cn.rtast.rob.command.BaseCommand
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.interceptor.CommandExecutionResult
import cn.rtast.rob.interceptor.CommandInterceptor

class EchoCommand : BaseCommand(LocalBaseInter()) {
    override val commandNames = listOf("/echo", "/eee")

    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        message.reply("11")
    }
}

class CustomInterceptor : CommandInterceptor() {
    override suspend fun beforeGroupExecute(message: GroupMessage, command: BaseCommand): CommandExecutionResult {
        println("全局拦截器放行")
        return CommandExecutionResult.CONTINUE
    }
}

class LocalBaseInter : CommandInterceptor() {
    override suspend fun beforeGroupExecute(message: GroupMessage, command: BaseCommand): CommandExecutionResult {
        println("局部拦截器组阻止")
        return CommandExecutionResult.CONTINUE
    }
}