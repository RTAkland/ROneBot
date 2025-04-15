/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/8 18:07
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.frontend.api

import cn.rtast.rob.starter.frontend.JVM_ONLY_LINTER_VERSION
import cn.rtast.rob.starter.frontend.client
import cn.rtast.rob.starter.frontend.util.fromJson
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.serialization.Serializable

@Serializable
public data class JvmOnlyLinterVersion(
    val latest: String
)

public suspend fun getJvmOnlyLinterVersion(): String {
    return try {
        client.get("https://repo.maven.rtast.cn/@/api/artifacts/versions/latest/releases/cn/rtast/jvmonly-linter/cn.rtast.jvmonly-linter.gradle.plugin")
            .bodyAsText().fromJson<JvmOnlyLinterVersion>().latest
    } catch (_: Exception) {
        JVM_ONLY_LINTER_VERSION
    }
}