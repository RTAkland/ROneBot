/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/18 19:42
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12

import cn.rtast.rob.BotFactory
import cn.rtast.rob.onebot.v12.onebot12.OneBot12Listener
import love.forte.plugin.suspendtrans.annotation.JvmBlocking
import kotlin.jvm.JvmOverloads
import kotlin.jvm.JvmStatic

public class OneBotV12Factory {
    public companion object : BotFactory {

        @JvmStatic
        @JvmOverloads
        @JvmBlocking(suffix = "JvmBlocking")
        public suspend fun creteClient(
            address: String,
            accessToken: String,
            listener: OneBot12Listener = object : OneBot12Listener {}
        ) {

        }

        @JvmStatic
        override var totalCommandExecutionTimes: Int = 0

        @JvmStatic
        override var privateCommandExecutionTimes: Int = 0

        @JvmStatic
        override var groupCommandExecutionTimes: Int = 0
    }
}