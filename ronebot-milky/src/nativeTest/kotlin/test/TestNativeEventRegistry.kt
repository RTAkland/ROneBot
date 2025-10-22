/*
 * Copyright © 2025 RTAkland
 * Date: 2025/10/22 00:57
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(ExperimentalROneBotApi::class)

package test

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.event.EventRegistry
import cn.rtast.rob.milky.MilkyBotFactory
import cn.rtast.rob.milky.event.FRAMEWORK
import kotlin.test.Test

class TestNativeEventRegistry {

    @Test
    fun `test search event registry on native platform`() {
        println(MilkyBotFactory.eventRegistry.search(EventRegistry.FRAMEWORK).first().cls.qualifiedName)
        println(MilkyBotFactory.eventRegistry.formatToJson())
    }

}