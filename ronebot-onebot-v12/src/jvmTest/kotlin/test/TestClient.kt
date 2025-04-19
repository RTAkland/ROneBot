/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/19 07:40
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package test

import cn.rtast.rob.onebot.v12.OneBotV12Factory
import cn.rtast.rob.onebot.v12.onebot12.OneBot12Listener
import kotlinx.coroutines.runBlocking
import kotlin.test.Test

class TestClient {

    @Test
    fun `test client`() {
        runBlocking {
            OneBotV12Factory.creteClient("ws://127.0.0.1:6700", "114514", object : OneBot12Listener {
                override suspend fun onRawMessage(message: String) {
                    println(message)
                }
            })
        }
    }
}