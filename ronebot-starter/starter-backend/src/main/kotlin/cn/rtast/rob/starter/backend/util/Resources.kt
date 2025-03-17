/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.backend.util

import cn.rtast.rob.starter.backend.entity.Config
import cn.rtast.rutil.string.fromJson

public object Resources {
    public fun load(path: String): ByteArray {
        return this::class.java.classLoader.getResourceAsStream("templates/$path")!!.use { it.readBytes() }
    }

    public fun loadConfig(): Config {
        return String(
            this::class.java.classLoader.getResourceAsStream("config/config.json")!!
                .use { it.readBytes() }).fromJson<Config>()
    }
}