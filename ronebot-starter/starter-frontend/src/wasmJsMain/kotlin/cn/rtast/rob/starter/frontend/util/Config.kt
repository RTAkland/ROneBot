/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

package cn.rtast.rob.starter.frontend.util

import cn.rtast.rob.starter.frontend.client
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable

@Serializable
public data class Config(
    val backend: String,
    val font: String,
)

public suspend fun loadConfig(): Config {
    return client.get("config/config.json").bodyAsText().fromJson<Config>()
}