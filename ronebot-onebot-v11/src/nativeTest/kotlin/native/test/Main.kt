/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

package native.test

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.event.packed.GroupMessageEvent
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.event.subscribe
import cn.rtast.rob.onebot.OneBotListener
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class Main {
    @Test
    fun main() {
        runBlocking {
            val instance1 =
                OneBotFactory.createClient("ws://127.0.0.1:8087/ws", "13666466104ghpA@@@", object : OneBotListener {
                    override suspend fun onGroupMessage(message: GroupMessage) {
                        println(message)
                    }
                })
//                OneBotFactory.createServer(8888, "114514ghpA@1919810", object : OneBotListener {
//                    override suspend fun onGroupMessage(message: GroupMessage) {
//                        println(message)
//                    }
//                })
            instance1.subscribe<GroupMessageEvent> {
                it.message.reply("1111")
            }
            instance1.addListeningGroup(985927054)

            while (true) {

            }
        }
    }
}