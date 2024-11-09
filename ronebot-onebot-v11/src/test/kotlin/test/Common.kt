/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/29
 */

package test

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.enums.Permission
import cn.rtast.rob.interceptor.CommandResult
import cn.rtast.rob.interceptor.ExecutionInterceptor
import cn.rtast.rob.util.BaseCommand
import cn.rtast.rob.util.PermissionCommand
import kotlinx.coroutines.delay
import kotlin.collections.joinToString

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

class PCommand : PermissionCommand() {
    override val commandNames: List<String> = listOf("p")
    override val permissions: List<Permission> = listOf(Permission.OWNER, Permission.ADMIN)
    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        println("result")
    }

    override suspend fun noPermission(message: GroupMessage) {
        println("无权限")
    }
}

class CustomInterceptor: ExecutionInterceptor {
    override suspend fun beforeGroupExecute(message: GroupMessage): CommandResult {
        return CommandResult.CONTINUE
    }

    override suspend fun afterGroupExecute(message: GroupMessage) {
    }
}