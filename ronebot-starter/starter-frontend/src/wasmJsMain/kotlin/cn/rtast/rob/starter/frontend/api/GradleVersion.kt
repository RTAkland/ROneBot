/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/19
 */

package cn.rtast.rob.starter.frontend.api

import cn.rtast.rob.starter.frontend.client
import cn.rtast.rob.starter.frontend.util.fromJson
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.Serializable

@Serializable
public data class GradleVersion(
    val name: String
)

public suspend fun getLatestGradleVersion(): String {
    return try {
        client.get("https://api.github.com/repos/gradle/gradle/releases/latest").bodyAsText()
            .fromJson<GradleVersion>().name
    } catch (_: Exception) {
        println("Gradle版本获取失败, 使用默认值: 8.13")
        "8.13"
    }
}