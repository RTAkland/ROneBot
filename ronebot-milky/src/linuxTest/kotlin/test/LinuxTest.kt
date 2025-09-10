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
import cn.rtast.rob.milky.command.BaseCommand
import cn.rtast.rob.milky.command.createCommand
import cn.rtast.rob.milky.command.register
import cn.rtast.rob.milky.event.ws.packed.GroupMessageEvent
import cn.rtast.rob.milky.event.ws.raw.ReceiveMessage
import cn.rtast.rob.milky.event.ws.raw.text
import cn.rtast.rob.milky.milky.onGroupMessage
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class LinuxTest {
    @Test
    fun `test milky on linux`() {
        runBlocking {
            val bot = MilkyBotFactory.createBot("http://127.0.0.1:3000", "114514", logLevel = LogLevel.DEBUG)
            bot.subscribe<GroupMessageEvent> {
                println(it.event.reply("114514"))
            }
            with(bot.listener) {
                onGroupMessage {
                    println(it.event.segments.text)
                }
            }
            createCommand("/hello", BaseCommand.ExecuteType.Group) {
                println("Hello world")
            }.register()
            bot.addListeningGroup(985927054)
            bot.join()
        }
    }
}