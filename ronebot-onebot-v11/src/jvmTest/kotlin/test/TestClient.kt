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
            val instance1 = OneBotFactory.createClient("wss://127.0.0.1:8081/ws", "13666466104ghpA@@@")
            instance1.subscribe<GroupMessageEvent> {
                println(it.message.sender.getMemberInfo().age)
            }
            instance1.addListeningGroup(985927054)
            while (true) {
                Thread.sleep(1000)
            }
        }
    }
}