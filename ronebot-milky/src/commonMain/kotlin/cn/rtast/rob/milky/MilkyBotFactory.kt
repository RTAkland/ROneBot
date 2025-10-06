/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 2:53 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky

import cn.rtast.klogging.LogLevel
import cn.rtast.rob.BotFactory
import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.milky.command.CommandManagerImpl
import cn.rtast.rob.milky.session.SessionManager
import cn.rtast.rob.scheduler.GlobalCoroutineScheduler
import cn.rtast.rob.util.IBotManager
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic
import kotlin.jvm.JvmSynthetic

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
        @ExperimentalROneBotApi
        public val sessionManager: SessionManager = SessionManager()

        @JvmStatic
        @JvmOverloads
        @JvmBlocking
        public suspend fun createBot(
            address: String,
            accessToken: String? = null,
            logLevel: LogLevel = LogLevel.INFO,
            /**
             * 选择是否忽略Bot自身发送的消息
             */
            ignoreSelf: Boolean = true,
        ): BotInstance {
            return BotInstance(
                address, accessToken,
                logLevel, ignoreSelf
            ).apply { createBot() }
        }

        @JvmSynthetic
        @Deprecated("没用了", level = DeprecationLevel.HIDDEN)
        override var totalCommandExecutionTimes: Int = 0

        @JvmSynthetic
        @Deprecated("没用了", level = DeprecationLevel.HIDDEN)
        override var privateCommandExecutionTimes: Int = 0

        @JvmSynthetic
        @Deprecated("没用了", level = DeprecationLevel.HIDDEN)
        override var groupCommandExecutionTimes: Int = 0
    }
}