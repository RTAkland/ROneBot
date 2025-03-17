/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

package cn.rtast.rob.starter.frontend.util

import kotlinx.serialization.Serializable

@Serializable
public data class Config(
    val backend: String
)

public val DEFAULT_CONFIG: Config = Config(
    backend = "https://rob-starter-backend.rtast.cn"
)

public external fun loadConfigExternal(): String

public fun loadConfig(): Config = loadConfigExternal().fromJson<Config>()