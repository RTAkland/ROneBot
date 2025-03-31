/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.backend.util

import cn.rtast.rob.starter.backend.entity.Config
import cn.rtast.rob.starter.backend.resources.getResources
import kotlinx.io.files.Path

object Resources {

    fun load(path: String): ByteArray {
        return getResources("templates/$path").asByteArray()
    }

    fun loadAsString(path: String): String {
        return getResources("templates/$path").asString()
    }

    fun loadConfig(): Config {
        return getResources("config/config.json").asString().fromJson<Config>()
    }
}