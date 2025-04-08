/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/19
 */

package cn.rtast.rob.starter.frontend.api

import cn.rtast.rob.starter.frontend.client
import cn.rtast.rob.starter.frontend.defaultGradleVersion
import cn.rtast.rob.starter.frontend.util.fromJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable

@Serializable
public data class GradleVersion(
    val current: Boolean,
    val version: String
)

public suspend fun getLatestGradleVersion(): String {
    return try {
        client.get("https://services.gradle.org/versions/all").bodyAsText().fromJson<List<GradleVersion>>()
            .first { it.current }.version
    } catch (_: Exception) {
        println("Gradle版本获取失败, 使用默认值")
        defaultGradleVersion
    }
}