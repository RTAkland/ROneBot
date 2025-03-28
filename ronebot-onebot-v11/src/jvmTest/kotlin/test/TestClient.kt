/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.event.packed.GroupMessageEvent
import cn.rtast.rob.event.subscribe
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class TestClient {
    @Test
    fun testClient() {
        runBlocking {
            println(System.getenv("QQ_GROUP_ID"))
            val wsAddress = System.getenv("WS_ADDRESS")
            val wsPassword = System.getenv("WS_PASSWORD")
            val qqGroupId = System.getenv("QQ_GROUP_ID").toLong()
            val instance1 = OneBotFactory.createClient(wsAddress, wsPassword)
            instance1.subscribe<GroupMessageEvent> {
                println(it.action.getGroupRequests())
                println(it.message.sender.getMemberInfo().age)
            }
            instance1.addListeningGroup(qqGroupId)
            while (true) {
                Thread.sleep(1000)
            }
        }
    }
}