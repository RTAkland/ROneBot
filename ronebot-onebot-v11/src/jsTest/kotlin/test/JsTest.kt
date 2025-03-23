/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

@file:OptIn(DelicateCoroutinesApi::class)

package test

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.event.packed.GroupMessageEvent
import cn.rtast.rob.event.subscribe
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlin.test.Test

class JsTest {
    @Test
    fun test() {
        GlobalScope.launch {
            val instance1 = OneBotFactory.createClient("ws://127.0.0.1:8081", "114514ghpA@1919810")
            instance1.subscribe<GroupMessageEvent> {
                println(it.action.getLoginInfo())
            }
            instance1.addListeningGroup(985927054)
            while (true) {

            }
        }
    }
}