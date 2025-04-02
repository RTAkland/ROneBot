/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

@file:OptIn(ExperimentalForeignApi::class)

package native.test

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.event.packed.GroupMessageEvent
import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.subscribe
import cn.rtast.rob.onebot.OneBotListener
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlinx.cinterop.*
import platform.posix.*

class Main {
    @Test
    fun main() {
        runBlocking {
            val wsAddress = getenv("WS_ADDRESS_PLAIN")?.toKString() ?: return@runBlocking
            val wsPassword = getenv("WS_PASSWORD")?.toKString() ?: return@runBlocking
            val qqGroupId = getenv("QQ_GROUP_ID")?.toKString()?.toLong() ?: return@runBlocking
            val instance1 =
                OneBotFactory.createClient(wsAddress.toString(), wsPassword, object : OneBotListener {
                    override suspend fun onGroupMessage(message: GroupMessage) {
                        println(message)
                    }
                })
            instance1.subscribe<GroupMessageEvent> {
                it.message.reply("1111")
            }
            instance1.addListeningGroup(qqGroupId)

            while (true) {

            }
        }
    }
}