/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/17 23:55
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.actionable

import cn.rtast.rob.BotInstance

public interface BotStatusActionable {
    /**
     * 获取Bot的QQ号
     */
    public suspend fun getBotUserId(): Long

    /**
     * 获取Bot实例
     */
    public suspend fun getBotInstance(): BotInstance
}