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
import org.junit.jupiter.api.Test

class TestClient {
    @Test
    fun testClient() {
        runBlocking {
            val wsAddress = System.getenv("WS_ADDRESS")
            val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
            val instance1 = OneBotFactory.createClient("wss://mc.dgtmc.top:8081/ws", "114514ghpA@1919810")
            instance1.subscribe<GroupMessageEvent> {
                println(it.action.getLoginInfo())
            }
            instance1.addListeningGroup(985927054)
            while (true) {
                Thread.sleep(1000)
            }
        }
    }
}