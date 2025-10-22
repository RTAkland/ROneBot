/*
 * Copyright © 2025 RTAkland
 * Date: 2025/10/22 00:50
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(ExperimentalROneBotApi::class)

package test

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.event.EventRegistry
import cn.rtast.rob.milky.MilkyBotFactory
import cn.rtast.rob.milky.event.FRIEND
import org.junit.Test

class TestEventRegistry {

    @Test

    fun `test registry search`() {
        println(MilkyBotFactory.eventRegistry.search(EventRegistry.FRIEND).first().cls.qualifiedName)
        println(MilkyBotFactory.eventRegistry.formatToJson())
    }
}