/*
 * Copyright © 2025 RTAkland
 * Date: 2025/10/22 00:50
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package test

import cn.rtast.rob.milky.MilkyBotFactory
import cn.rtast.rob.milky.event.MilkyEventRegistry
import org.junit.Test

class TestEventRegistry {

    @Test

    fun `test registry search`() {
        println(MilkyBotFactory.eventRegistry.search(MilkyEventRegistry.Topics.FRAMEWORK).first().type.qualifiedName)
    }
}