/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

@file:Suppress("unused")

package cn.rtast.rob.util

import cn.rtast.rob.annotations.InternalROBApi
import cn.rtast.rob.platformName
import io.github.oshai.kotlinlogging.KotlinLogging

@InternalROBApi
public class Logger {
    private val logger = KotlinLogging.logger("ROneBot-$platformName")
    public fun debug(message: String): Unit = logger.debug { message }
    public fun info(message: String): Unit = logger.info { message }
    public fun warn(message: String): Unit = logger.warn { message }
    public fun error(message: String): Unit = logger.error { message }
}

@InternalROBApi
public fun getLogger(): Logger = Logger()