/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/29
 */

package test

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.interceptor.CommandExecutionResult
import cn.rtast.rob.interceptor.ExecutionInterceptor
import cn.rtast.rob.util.BaseCommand
import kotlinx.coroutines.delay

class EchoCommand : BaseCommand() {
    // A simple echo message command
    override val commandNames = listOf("/echo", "/eee")

    override suspend fun executePrivate(message: PrivateMessage, args: List<String>) {
        message.reply("你的accessToken输入错误，请检查！您的accessToken：1")
    }
}

class DelayCommand : BaseCommand() {
    override val commandNames = listOf("d", "/d")

    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        delay(3000L)
        message.reply("延迟3秒")
        println("延迟3秒")
    }
}

class MatchedCommand : BaseCommand() {
    override val commandNames = listOf("match", "matches")

    override suspend fun executeGroup(message: GroupMessage, args: List<String>, matchedCommand: String) {
        println(matchedCommand)
    }
}

class CustomInterceptor : ExecutionInterceptor() {
    override suspend fun beforeGroupExecute(message: GroupMessage, command: BaseCommand): CommandExecutionResult {
        println("before")
        return CommandExecutionResult.CONTINUE
    }

    override suspend fun afterGroupExecute(message: GroupMessage, command: BaseCommand) {
        println("after")
    }
}