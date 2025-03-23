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
            val wsAddress = System.getenv("WS_ADDRESS")
            val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
            val instance1 = OneBotFactory.createClient("ws://127.0.0.1:8081", "114514ghpA@1919810")
            instance1.subscribe<GroupMessageEvent> {
                println(it.action.getLoginInfo())
            }
            instance1.addListeningGroup(985927054)
//            while (true) {
//                Thread.sleep(1000)
//            }
        }
    }
}