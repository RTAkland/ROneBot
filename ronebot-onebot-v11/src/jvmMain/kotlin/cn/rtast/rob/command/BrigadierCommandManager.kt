/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/7 20:46
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(JvmOnly::class)

package cn.rtast.rob.command

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.OneBotFactory.Companion.botManager
import cn.rtast.rob.annotations.JvmOnly

@JvmOnly
@get:JvmSynthetic
public val OneBotFactory.Companion.brigadierCommandManager: BrigadierCommandManagerImpl by lazy {
    BrigadierCommandManagerImpl(botManager.allBots())
}

@JvmOnly
public class BrigadierCommandManager {
    public companion object {
        @JvmOnly
        @JvmStatic
        public fun getBrigadierCommandManager(): BrigadierCommandManagerImpl {
            return OneBotFactory.brigadierCommandManager
        }
    }
}