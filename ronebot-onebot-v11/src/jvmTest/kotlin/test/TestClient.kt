/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.command.BaseCommand
import cn.rtast.rob.event.packed.PrivatePokeEvent
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.PrivateMessage
import cn.rtast.rob.event.raw.message.text
import cn.rtast.rob.event.subscribe
import cn.rtast.rob.onebot.BlockingOneBotListener
import cn.rtast.rob.segment.Text
import cn.rtast.rob.segment.toMessageChain
import cn.rtast.rob.session.accept
import cn.rtast.rob.session.reject
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class TestCommand : BaseCommand() {
    override val commandNames = listOf("test")

    @OptIn(ExperimentalROneBotApi::class)
    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        if (message.text.contains("start")) {
            println("start")
            startGroupSession(message) {
                println(it.message)
                if (it.message.text.contains("end")) {
                    it.accept("ended")
                } else {
                    it.reject(Text("reject").toMessageChain())
                }
            }
            println("ended")
        }
    }

    @OptIn(ExperimentalROneBotApi::class)
    override suspend fun executePrivate(message: PrivateMessage, args: List<String>) {
        if (message.text.contains("start")) {
            println("start")
            startPrivateSession(message) {
                println(it.message)
                if (it.message.text.contains("end")) {
                    it.accept("ended")
                } else {
                    it.reject(Text("reject").toMessageChain())
                }
            }
            println("ended")
        }
    }
}

class TestClient {
    @Test
    fun testClient() {
        runBlocking {
            val isRemote = false
            val (wsAddress, wsPassword) = ("ws://127.0.0.1:3001" to "114514")
//            val qqGroupId = System.getenv("QQ_GROUP_ID").toLong()
            val qqGroupId = 985927054L
            val instance1 = OneBotFactory.createClient(wsAddress, wsPassword, object : BlockingOneBotListener {
                override fun onGroupMessageBlocking(message: GroupMessage) {
                    println("normal")
                }
            }, logLevel = LogLevel.DEBUG)
            instance1.subscribe<PrivatePokeEvent> {
//                println(it.action.getStrangerInfo(3458671395))
            }
            instance1.addListeningGroup(qqGroupId)
            OneBotFactory.commandManager.register(TestCommand())
            while (true) {
                Thread.sleep(1000)
            }
        }
    }
}