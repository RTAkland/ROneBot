/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

@file:Suppress("unused")

package cn.rtast.rob.util

import cn.rtast.platform.logging.LogLevel
import cn.rtast.platform.logging.Logger
import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.platformName

@InternalROneBotApi
public fun getLogger(prefix: String = "", level: LogLevel = LogLevel.INFO): Logger =
    Logger.getLogger("ROneBot-$platformName") {
        logLevel = level
    }