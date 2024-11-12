/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/29
 */

package test

import cn.rtast.rob.annotations.CommandMatchingStrategy
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.enums.MatchingStrategy
import cn.rtast.rob.interceptor.CommandResult
import cn.rtast.rob.interceptor.ExecutionInterceptor
import cn.rtast.rob.util.BaseCommand
import kotlinx.coroutines.delay
import kotlin.collections.joinToString

@CommandMatchingStrategy(MatchingStrategy.REGEX)
class EchoCommand : BaseCommand() {
    // A simple echo message command
    override val commandNames = listOf("/echo", "/eee")

    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        message.reply(args.joinToString(" "))
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

class CustomInterceptor : ExecutionInterceptor {
    override suspend fun beforeGroupExecute(message: GroupMessage, command: BaseCommand): CommandResult {
        return CommandResult.CONTINUE
    }

    override suspend fun afterGroupExecute(message: GroupMessage, command: BaseCommand) {
    }
}