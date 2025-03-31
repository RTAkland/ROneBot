/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.backend.util

import cn.rtast.rkmbed.runtime.RKMBedResource
import cn.rtast.rkmbed.runtime.getResource
import cn.rtast.rob.starter.backend.entity.Config
import cn.rtast.rob.starter.backend.resources.GeneratedResource

object Resources {

    init {
        RKMBedResource.setResource(GeneratedResource)
    }

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