/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

package cn.rtast.rob.starter.frontend.util

import kotlinx.serialization.Serializable

@Serializable
public data class Config(
    val backend: String,
    val font: String,
)

public val DEFAULT_CONFIG: Config = Config(
    backend = "https://rob-starter-backend.rtast.cn",
    font = "https://s-cd-10194-stasdijnhuia.oss.dogecdn.com/%E5%BE%AE%E8%BD%AF%E9%9B%85%E9%BB%91.ttf"
)

public external fun loadConfigExternal(): String

public fun loadConfig(): Config = loadConfigExternal().fromJson<Config>()