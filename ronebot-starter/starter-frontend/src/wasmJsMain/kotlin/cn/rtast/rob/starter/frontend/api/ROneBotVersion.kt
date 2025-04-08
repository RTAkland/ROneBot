/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.frontend.api

import cn.rtast.rob.starter.frontend.client
import cn.rtast.rob.starter.frontend.defaultROBVersion
import cn.rtast.rob.starter.frontend.util.fromJson
import io.ktor.client.request.*
import io.ktor.client.statement.*

public suspend fun fetchLatestROBVersion(): String {
    return try {
        client.get("https://api.rtast.cn/api/ronebot").bodyAsText().fromJson<Version>().version
    } catch (_: Exception) {
        defaultROBVersion
    }
}