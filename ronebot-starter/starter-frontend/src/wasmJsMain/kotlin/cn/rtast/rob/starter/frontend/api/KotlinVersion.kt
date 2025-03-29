/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/18
 */

package cn.rtast.rob.starter.frontend.api

import cn.rtast.rob.starter.frontend.client
import cn.rtast.rob.starter.frontend.defaultKotlinVersion
import cn.rtast.rob.starter.frontend.util.fromJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
public data class KotlinVersion(
    @SerialName("tag_name")
    val tagName: String
)

public suspend fun getLatestKotlinVersion(): String {
    return try {
        client.get("https://api.github.com/repos/JetBrains/kotlin/releases/latest").bodyAsText()
            .fromJson<KotlinVersion>().tagName
    } catch (_: Exception) {
        println("Kotlin版本获取失败, 使用默认值")
        defaultKotlinVersion
    }
}
