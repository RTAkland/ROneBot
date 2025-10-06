/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:20 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package test

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.milky.MilkyBotFactory
import cn.rtast.rob.milky.command.createCommand
import cn.rtast.rob.milky.command.register
import cn.rtast.rob.milky.command.session.startGroupSession
import cn.rtast.rob.milky.command.session.startPrivateSession
import cn.rtast.rob.milky.event.ws.raw.text
import cn.rtast.rob.milky.event.ws.raw.texts
import cn.rtast.rob.milky.milky.messageChain
import cn.rtast.rob.milky.milky.onGroupMessage
import cn.rtast.rob.milky.milky.text
import cn.rtast.rob.milky.segment.part.Text
import cn.rtast.rob.session.accept
import cn.rtast.rob.session.reject
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class Test {
    @Test
    fun `test api`() {
        runBlocking {
            val bot = MilkyBotFactory.createBot(
                "http://127.0.0.1:3000", "114514",
                logLevel = LogLevel.INFO,
                ignoreSelf = false
            )
//            with(bot.listener) {
//                onGroupMessage {
//                    println(it.event.texts)
//                    println(it.event.text)
//                }
//            }
            createCommand("test") {
                if (it.message.text.contains("start")) {
                    it.startGroupSession { session ->
                        if (session.message.text.contains("end")) {
                            session.accept()
                        } else {
                            session.reject(messageChain {
                                text("reject")
                            })
                        }
                    }
                    println("done")
                }
                println("outer")
            }.register()
            bot.addListeningGroup(985927054)
            bot.join()
        }
    }
}