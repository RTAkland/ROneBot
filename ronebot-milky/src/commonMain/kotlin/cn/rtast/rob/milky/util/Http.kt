/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 2:58 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.milky.util

import cn.rtast.rob.common.http.Http
import cn.rtast.rob.milky.BotInstance
import cn.rtast.rob.milky.enums.APIEndpoint

/**
 * 对API端点发起请求
 * @param endpoint api端点
 * @param payload json body
 */
public inline fun <reified T> BotInstance.requestAPI(endpoint: APIEndpoint, payload: String? = null): T {
    return Http.post<T>(
        "$address/api/${endpoint.endpoint}", payload ?: "{}", mapOf(
            "Authorization" to "Bearer $accessToken",
            "Content-Type" to "application/json"
        )
    )
}

public fun BotInstance.requestAPI(endpoint: APIEndpoint, payload: String? = null) {
    Http.post(
        "$address/api/${endpoint.endpoint}", payload ?: "{}", mapOf(
            "Authorization" to "Bearer $accessToken",
            "Content-Type" to "application/json"
        )
    )
}