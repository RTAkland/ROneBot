/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.annotations.command.functional.GroupCommandHandlerIntercepted
import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.interceptor.CommandExecutionResult
import cn.rtast.rob.interceptor.FunctionalCommandInterceptor

class TestLocalInterceptor : FunctionalCommandInterceptor<GroupMessage>() {
    override suspend fun before(message: GroupMessage): CommandExecutionResult {
        println("before")
        return CommandExecutionResult.CONTINUE
    }
}

@GroupCommandHandlerIntercepted(["/test"], TestLocalInterceptor::class)
suspend fun testCommand(message: GroupMessage) {
    println(message)
}

suspend fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken)
    instance1.addListeningGroup(985927054)
    ROneBotFactory.interceptor = CustomInterceptor()
    ROneBotFactory.commandManager.registerFunction(::testCommand)
    ROneBotFactory.commandManager.register(EchoCommand())
}