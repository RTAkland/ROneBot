/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.backend.util

import cn.rtast.rob.starter.backend.entity.Config
import cn.rtast.rob.starter.backend.resources.getResource

object Resources {

    fun load(path: String): ByteArray {
        return getResource("templates/$path").asByteArray()
    }

    fun loadAsString(path: String): String {
        return getResource("templates/$path").asString()
    }

    fun loadConfig(): Config {
        return getResource("config/config.json").asString().fromJson<Config>()
    }
}