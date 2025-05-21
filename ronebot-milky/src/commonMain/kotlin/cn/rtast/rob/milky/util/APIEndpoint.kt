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
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*


/**
 * 对API端点发起请求
 * @param endpoint api端点
 * @param payload json body
 */
@InternalROneBotApi
public suspend fun BotInstance.httpRequest(endpoint: APIEndpoint, payload: String): String =
    httpClient.post("$address/api/${endpoint.endpoint}") {
        contentType(ContentType.Application.Json)
        setBody(payload)
        accessToken?.let { header("Authorization", "Bearer $it") }
    }.bodyAsText().apply { logger.debug(this) }

/**
 * 对于有返回值的端点自动反序列化
 */
public suspend inline fun <reified T> BotInstance.requestAPI(endpoint: APIEndpoint, payload: String): T =
    httpRequest(endpoint, payload).fromJson<T>()

/**
 * 对于一些没有返回值的端点进行请求
 */
@Suppress("unused")
public suspend fun BotInstance.requestAPI(endpoint: APIEndpoint, payload: String, dummy: Unit = Unit) {
    httpRequest(endpoint, payload)
}