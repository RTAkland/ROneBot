/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 8:22 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package test

import cn.rtast.rob.milky.MilkyBotFactory
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class LinuxTest {
    @Test
    fun `test milky on linux`() {
        runBlocking {
            val bot = MilkyBotFactory.createBot("http://127.0.0.1:8080", "114514")
        }
    }
}