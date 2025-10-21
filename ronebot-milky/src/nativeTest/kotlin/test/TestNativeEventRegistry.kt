/*
 * Copyright © 2025 RTAkland
 * Date: 2025/10/22 00:57
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package test

import cn.rtast.rob.milky.MilkyBotFactory
import cn.rtast.rob.milky.event.MilkyEventRegistry
import kotlin.test.Test

class TestNativeEventRegistry {

    @Test
    fun `test search event registry on native platform`() {
        println(MilkyBotFactory.eventRegistry.search(MilkyEventRegistry.Topics.FRAMEWORK).first().type.qualifiedName)
    }

}