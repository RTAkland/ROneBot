/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/7 20:46
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:JvmName("BrigadierCommandManager")

package cn.rtast.rob.command

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.OneBotFactory.Companion.botManager

private val brigadierCommandManagerInstance by lazy {
    BrigadierCommandManagerImpl(botManager.allBots())
}

public val OneBotFactory.Companion.brigadierCommandManager: BrigadierCommandManagerImpl
    get() = brigadierCommandManagerInstance

public fun getBrigadierCommandManager(): BrigadierCommandManagerImpl {
    return brigadierCommandManagerInstance
}