/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 2:53 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.BotFactory
import cn.rtast.rob.milky.milky.MilkyListener
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

public class MilkyBotFactory {
    public companion object : BotFactory {

//        @JvmStatic
//        public val globalScheduler = GlobalCoroutineScheduler()

        @JvmStatic
        @JvmOverloads
        @JvmBlocking
        public suspend fun createBot(
            address: String,
            accessToken: String? = null,
            listener: MilkyListener = object : MilkyListener {},
            logLevel: LogLevel = LogLevel.INFO
        ): BotInstance {
            return BotInstance(address, accessToken, listener, logLevel).apply { createBot() }
        }

        @JvmStatic
        override var totalCommandExecutionTimes: Int = 0

        @JvmStatic
        override var privateCommandExecutionTimes: Int = 0

        @JvmStatic
        override var groupCommandExecutionTimes: Int = 0
    }
}