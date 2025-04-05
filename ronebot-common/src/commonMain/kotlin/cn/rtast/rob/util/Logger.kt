/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

@file:Suppress("unused")

package cn.rtast.rob.util

import cn.rtast.klogging.KLogging
import cn.rtast.klogging.LogLevel
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.platformName

@InternalROneBotApi
internal fun getLogger(): KLogging = KLogging.getLogger("ROneBot-$platformName").apply {
    setLoggingLevel(LogLevel.INFO)
}