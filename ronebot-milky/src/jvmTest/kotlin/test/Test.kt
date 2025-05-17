/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 3:20 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package test

import cn.rtast.rob.milky.MilkyBotFactory
import cn.rtast.rob.milky.enums.internal.APIEndpoint
import cn.rtast.rob.milky.event.system.GetLoginInfo
import cn.rtast.rob.milky.util.requestAPI
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class Test {
    @Test
    fun `test api`() {
        runBlocking {
            val bot = MilkyBotFactory.createBot("http://127.0.0.1:8080", "114514")
            println(bot.requestAPI<GetLoginInfo>(APIEndpoint.System.GetLoginInfo))
        }
    }
}