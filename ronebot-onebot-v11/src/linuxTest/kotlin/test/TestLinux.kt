/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/10 18:40
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package test

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.event.packed.GroupMessageEvent
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.subscribe
import cn.rtast.rob.onebot.OneBotListener
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class TestLinux {
    @Test
    fun main() {
        runBlocking {
//            val wsAddress = getenv("WS_ADDRESS")?.toKString() ?: return@runBlocking
//            val wsPassword = getenv("WS_PASSWORD")?.toKString() ?: return@runBlocking
//            val qqGroupId = getenv("QQ_GROUP_ID")?.toKString()?.toLong() ?: return@runBlocking
            val wsAddress = "ws://127.0.0.1:8081"
            val wsPassword = "114514"
            val qqGroupId = 985927054L
            val instance1 =
                OneBotFactory.createClient(wsAddress.toString(), wsPassword, object : OneBotListener {
                    override suspend fun onGroupMessage(message: GroupMessage) {
                        println(message)
                    }
                })
            instance1.subscribe<GroupMessageEvent> {
                it.message.reply("1111")
            }
            instance1.addListeningGroup(qqGroupId)

            while (true) {

            }
        }
    }
}