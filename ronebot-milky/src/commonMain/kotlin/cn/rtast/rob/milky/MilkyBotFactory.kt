/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 2:53 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky

import cn.rtast.rob.BotFactory

public class MilkyBotFactory {
    public companion object : BotFactory {
        public suspend fun createBot(address: String, accessToken: String? = null): BotInstance {
            return BotInstance(address, accessToken).apply { createBot() }
        }

        override var totalCommandExecutionTimes: Int = 0
        override var privateCommandExecutionTimes: Int = 0
        override var groupCommandExecutionTimes: Int = 0
    }
}