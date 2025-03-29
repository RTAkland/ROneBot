/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/11
 */

package cn.rtast.rob.starter.frontend.api

import cn.rtast.rob.starter.frontend.defaultROBVersion
import cn.rtast.rob.starter.frontend.util.fromJson
import io.ktor.util.*
import kotlinx.browser.window
import kotlinx.coroutines.await
import kotlinx.serialization.Serializable
import org.w3c.fetch.Response

@Serializable
public data class GithubRepositoryContent(
    val content: String
)

public suspend fun fetchLatestROBVersionContent(): String {
    val response: Response =
        window.fetch("https://api.github.com/repos/RTAkland/ROneBot/contents/gradle.properties")
            .await()
    return response.text().await<JsString>().fromJson<GithubRepositoryContent>().content.decodeBase64String()
}

public suspend fun fetchLatestROBVersion(): String {
    return try {
        fetchLatestROBVersionContent()
            .split("\n").first { it.startsWith("libVersion") }
            .split("=").last()
    } catch (_: Exception) {
        defaultROBVersion
    }
}