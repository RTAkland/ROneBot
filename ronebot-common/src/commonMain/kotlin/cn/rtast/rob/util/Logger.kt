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
public fun getLogger(prefix: String = ""): KLogging = KLogging.getLogger("ROneBot-$platformName", prefix).apply {
    setLoggingLevel(LogLevel.INFO)
}