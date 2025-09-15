/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 1:04 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.frontend.util

import cn.rtast.rob.starter.frontend.client
import cn.rtast.rob.starter.frontend.entity.APIGradleVersion
import cn.rtast.rob.starter.frontend.entity.APIKotlinVersion
import cn.rtast.rob.starter.frontend.entity.APIROneBotVersion
import io.ktor.client.request.*
import io.ktor.client.statement.*

public suspend fun getKotlinVersion(): String =
    client.get("https://api.rtast.cn/api/kotlin").bodyAsText()
        .fromJson<APIKotlinVersion>().version

public suspend fun getGradleVersion(): String {
    return try {
        client.get("https://services.gradle.org/versions/all").bodyAsText()
            .fromJson<List<APIGradleVersion>>().first { it.current }.version
    } catch (_: Exception) {
        "9.0.0"
    }
}

public suspend fun getROneBotVersion(): String =
    client.get("https://api.rtast.cn/api/ronebot").bodyAsText()
        .fromJson<APIROneBotVersion>().version
