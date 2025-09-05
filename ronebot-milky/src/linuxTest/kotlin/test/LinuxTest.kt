/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:22 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package test

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.event.subscribe
import cn.rtast.rob.milky.MilkyBotFactory
import cn.rtast.rob.milky.event.ws.packed.GroupMessageEvent
import cn.rtast.rob.milky.milky.MilkyListener
import io.ktor.client.request.post
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class LinuxTest {
    @Test
    fun `test milky on linux`() {
        runBlocking {
            val bot = MilkyBotFactory.createBot("http://127.0.0.1:3000", "114514", logLevel = LogLevel.DEBUG, listener = object : MilkyListener {
                override suspend fun onGroupMessageEvent(event: GroupMessageEvent) {
//                    println(event.reply("114514"))
                }
            })
            bot.subscribe<GroupMessageEvent> {
                println(it.event.reply("114514"))
            }
            bot.join()
        }
    }
}