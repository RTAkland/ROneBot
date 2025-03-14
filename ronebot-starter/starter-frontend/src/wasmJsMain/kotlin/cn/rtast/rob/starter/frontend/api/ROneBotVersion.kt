/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.frontend.api

import cn.rtast.rob.starter.frontend.util.fromJson
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import org.w3c.fetch.Response

@Serializable
public data class Version(
    val isSnapshot: Boolean,
    val version: String
)

public suspend fun fetchLatestVersion(): String {
    val response: Response =
        window.fetch("https://repo.maven.rtast.cn/api/maven/latest/version/releases/cn/rtast/ronebot-onebot-v11")
            .await()
    return response.text().await<JsString>().fromJson<Version>().version
}