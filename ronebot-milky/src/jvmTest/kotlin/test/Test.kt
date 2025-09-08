/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:20 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package test

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.event.subscribe
import cn.rtast.rob.milky.MilkyBotFactory
import cn.rtast.rob.milky.command.BaseCommand
import cn.rtast.rob.milky.command.createCommand
import cn.rtast.rob.milky.command.register
import cn.rtast.rob.milky.event.ws.packed.GroupMessageEvent
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class Test {
    @Test
    fun `test api`() {
        runBlocking {
            val bot = MilkyBotFactory.createBot(
                "http://127.0.0.1:3000", "114514",
                logLevel = LogLevel.INFO,
            )
            createCommand("/hello", BaseCommand.ExecuteType.Private) {
                println("Hello world")
            }.register()
            bot.subscribe<GroupMessageEvent> {
//                it.event.reply("11")
            }
            bot.addListeningGroup(985927054)
            bot.join()
        }
    }
}