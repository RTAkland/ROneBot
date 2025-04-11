/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 13:02
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(DelicateCoroutinesApi::class, JsPlatformOnly::class)

package test

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.annotations.JsPlatformOnly
import cn.rtast.rob.event.packed.GroupMessageEvent
import cn.rtast.rob.event.subscribe
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.test.Test

class TestJs {
    @Test
    fun `Test Js Platform`() {
        val server = "http://127.0.0.1:8081/"
        val token = "114514"
        GlobalScope.launch {
            val bot = OneBotFactory.createHttpServer(server, 8889, token)
            bot.subscribe<GroupMessageEvent> {
                it.message.reply("111")
            }
        }
        while (true) {

        }
    }
}