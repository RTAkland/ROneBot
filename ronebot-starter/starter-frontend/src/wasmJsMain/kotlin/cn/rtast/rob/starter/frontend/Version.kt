/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.frontend

import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import org.w3c.fetch.Response

@Serializable
data class Version(
    val isSnapshot: Boolean,
    val version: String
)

inline fun <reified  T> JsString.fromJson(): T {
    return Json.decodeFromString<T>(this.toString())
}

suspend fun fetchLatestVersion(): String {
    val response: Response =
        window.fetch("https://repo.maven.rtast.cn/api/maven/latest/version/releases/cn/rtast/ronebot-onebot-v11")
            .await()
    return response.text().await<JsString>().fromJson<Version>().version
}