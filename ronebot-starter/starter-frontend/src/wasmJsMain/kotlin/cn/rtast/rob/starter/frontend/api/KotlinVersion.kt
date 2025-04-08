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

public suspend fun getLatestKotlinVersion(): String {
    return try {
        client.get("https://api.rtast.cn/api/kotlin").bodyAsText().fromJson<Version>().version
    } catch (_: Exception) {
        println("Kotlin版本获取失败, 使用默认值")
        defaultKotlinVersion
    }
}
