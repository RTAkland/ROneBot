/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package test

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.onebot.OneBotListener
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class TestServer {
    @Test
    fun `test server`() = runBlocking {
        val bot = OneBotFactory.createServer(7071, "114514", object : OneBotListener {
            override suspend fun onGroupMessage(message: GroupMessage) {
                println(message)
            }
        })
        while (true) {

        }
    }
}