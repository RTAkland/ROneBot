/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/4/5
 */

package native.test

import cn.rtast.rob.OneBotFactory
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class TestServer {
    @Test
    fun `Test onebot11 server`() {
        runBlocking {
            OneBotFactory.createServer(9999, "114514")
        }
        while (true) {

        }
    }
}