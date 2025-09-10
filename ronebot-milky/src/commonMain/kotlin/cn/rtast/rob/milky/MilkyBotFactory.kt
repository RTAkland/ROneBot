/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 2:53 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.BotFactory
import cn.rtast.rob.milky.command.CommandManagerImpl
import cn.rtast.rob.milky.milky.MilkyListener
import cn.rtast.rob.scheduler.GlobalCoroutineScheduler
import cn.rtast.rob.util.IBotManager
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

public class MilkyBotFactory {
    public companion object : BotFactory {

        @JvmStatic
        public val botInstances: MutableMap<IBotManager.ID, BotInstance> = mutableMapOf()

        @JvmStatic
        public val globalScheduler: GlobalCoroutineScheduler<BotInstance> =
            GlobalCoroutineScheduler(botInstances.values.toList())

        @JvmStatic
        public val commandManager: CommandManagerImpl = CommandManagerImpl()

        @JvmStatic
        @JvmOverloads
        @JvmBlocking
        public suspend fun createBot(
            address: String,
            accessToken: String? = null,
            logLevel: LogLevel = LogLevel.INFO,
            /**
             * 这个参数并没有任何作用
             * 暂时没有 2025 Sep. 5th
             */
            ignoreSelf: Boolean = true,
        ): BotInstance {
            return BotInstance(
                address, accessToken,
                logLevel, ignoreSelf
            ).apply { createBot() }
        }

        @JvmStatic
        override var totalCommandExecutionTimes: Int = 0

        @JvmStatic
        override var privateCommandExecutionTimes: Int = 0

        @JvmStatic
        override var groupCommandExecutionTimes: Int = 0
    }
}