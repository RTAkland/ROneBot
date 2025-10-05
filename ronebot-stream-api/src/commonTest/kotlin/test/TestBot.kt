/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 5:19 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package test

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.onebot.stream.downloadFileStream
import cn.rtast.rob.onebot.stream.testDownloadStream
import cn.rtast.rob.onebot.stream.uploadFileStream
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.test.Test

class TestBot {

    private val testBytes = ByteArray(819200)

    @Test
    fun `test bot`() {
        runBlocking {
            val bot = OneBotFactory.createClient("ws://127.0.0.1:3001", "114514", logLevel = LogLevel.DEBUG)
            println(bot.action.uploadFileStream(testBytes, "test.bin", fileRetention = 300 * 1000))
            bot.action.downloadFileStream("test.bin")
        }
//        while (true) {
//
//        }
    }
}