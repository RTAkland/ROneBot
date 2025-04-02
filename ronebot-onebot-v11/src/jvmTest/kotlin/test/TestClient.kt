/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.command.BaseCommand
import cn.rtast.rob.event.packed.GroupMessageEvent
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.text
import cn.rtast.rob.event.subscribe
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class TestCommand : BaseCommand() {
    override val commandNames = listOf("test")

    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        if (message.text.contains("test")) {
            println("start")
            message.startSession("114514")
        }
    }

    override suspend fun onGroupSession(message: GroupMessage, initArg: Any) {
        println(initArg as String)
        message.skipSession()
        println("end")
    }
}

class TestClient {
    @Test
    fun testClient() {
        runBlocking {
            val isRemote = false
            val (wsAddress, wsPassword) = if (isRemote)
                (System.getenv("WS_ADDRESS")!! to System.getenv("WS_PASSWORD")!!)
            else ("ws://127.0.0.1:3002" to "114514")
            val qqGroupId = System.getenv("QQ_GROUP_ID").toLong()
            val instance1 = OneBotFactory.createClient(wsAddress, wsPassword)
            instance1.subscribe<GroupMessageEvent> {
            }
            instance1.addListeningGroup(qqGroupId)
            OneBotFactory.commandManager.register(TestCommand())
            while (true) {
                Thread.sleep(1000)
            }
        }
    }
}