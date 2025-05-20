/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/21/25, 7:46 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.event.milky

import cn.rtast.rob.milky.BotInstance
import cn.rtast.rob.milky.event.MilkyEvent
import cn.rtast.rob.milky.milky.MilkyAction

/**
 * Bot实例被释放时
 */
public data class BotInstanceDisposedEvent(
    override val action: MilkyAction,
    val botInstance: BotInstance
): MilkyEvent