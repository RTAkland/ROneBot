/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.annotations.command.functional.GroupCommandHandler
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.entity.PrivateMessage
import cn.rtast.rob.interceptor.CommandExecutionResult
import cn.rtast.rob.interceptor.FunctionalCommandInterceptor
import cn.rtast.rob.interceptor.FunctionalGlobalCommandInterceptor
import cn.rtast.rob.onebot.OneBotListener
import kotlin.reflect.KFunction

class TestLocalInterceptor : FunctionalCommandInterceptor<GroupMessage>() {
    override suspend fun before(message: GroupMessage): CommandExecutionResult {
        return CommandExecutionResult.CONTINUE
    }
}

@GroupCommandHandler(["/test"], interceptor = TestLocalInterceptor::class)
suspend fun testCommand(message: GroupMessage) {
    println(message)
}

class TestGlobalFunctionInter : FunctionalGlobalCommandInterceptor() {
    override suspend fun beforeGroup(message: GroupMessage, func: KFunction<*>): CommandExecutionResult {
        return CommandExecutionResult.CONTINUE
    }
}

suspend fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = OneBotFactory.createClient(wsAddress, wsAccessToken, object : OneBotListener {
        override suspend fun onPrivateMessage(message: PrivateMessage, json: String) {
            println(message.sender.getStrangerInfo())
        }

        override suspend fun onGroupMessage(message: GroupMessage) {
            println(message.sender.getStrangerInfo())
            println(message.sender.getMemberInfo())
        }
    })
    instance1.addListeningGroup(985927054)
    OneBotFactory.interceptor = CustomInterceptor()
    OneBotFactory.functionalInterceptor = TestGlobalFunctionInter()
    OneBotFactory.commandManager.registerFunction(::testCommand)
    OneBotFactory.commandManager.register(EchoCommand())
}