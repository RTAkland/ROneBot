/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 5/18/25, 2:58 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.milky.util

import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.milky.BotInstance
import cn.rtast.rob.milky.enums.internal.APIEndpoint
import cn.rtast.rob.util.fromJson
import cn.rtast.rob.util.toJson
import io.ktor.client.request.*
import io.ktor.client.statement.*


/**
 * 对API端点发起请求
 * @param endpoint api端点
 * @param payload json body
 */
@InternalROneBotApi
public suspend fun <K> BotInstance.httpRequest(endpoint: APIEndpoint, payload: K?): String =
    httpClient.post("$address/api/${endpoint.endpoint}") {
        setBody(payload?.toJson() ?: "{}")
        accessToken?.let { header("Authorization", "Bearer $it") }
    }.bodyAsText()

/**
 * 对于有返回值的端点自动反序列化
 */
public suspend inline fun <reified T, K> BotInstance.requestAPI(endpoint: APIEndpoint, payload: K? = null): T =
    httpRequest(endpoint, payload).fromJson<T>()

/**
 * 对于一些没有返回值的端点进行请求
 */
public suspend fun <K> BotInstance.requestAPI(endpoint: APIEndpoint, payload: K? = null) {
    httpRequest(endpoint, payload)
}