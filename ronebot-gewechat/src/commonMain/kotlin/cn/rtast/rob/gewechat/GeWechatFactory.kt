/*
 * Copyright © 2025 RTAkland
 * Date: 2025/4/6 09:13
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.gewechat

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.BotFactory

public class GeWechatFactory {
    public companion object : BotFactory {
        public val botInstances: MutableList<BotInstance> = mutableListOf<BotInstance>()

        public suspend fun createServer(
            hostUrl: String,
            port: Int,
            callbackUrl: String,
            logLevel: LogLevel = LogLevel.INFO
        ): BotInstance {
            return BotInstance(hostUrl, port, callbackUrl, logLevel)
                .apply {
                    botInstances.add(this)
                    createBot()
                }
        }

        override var totalCommandExecutionTimes: Int = 0
        override var privateCommandExecutionTimes: Int = 0
        override var groupCommandExecutionTimes: Int = 0
    }
}