/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/18 19:45
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.v12.util

import cn.rtast.rob.onebot.v12.BotInstance

public class MessageHandler internal constructor(
    private val botInstance: BotInstance
) {
    public suspend fun onOpen() {

    }

    public suspend fun onMessage(message: String) {

    }

    public suspend fun onClose() {

    }

    public suspend fun onError(exception: Exception) {

    }
}