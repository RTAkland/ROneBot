/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 12:20
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("unused")
@file:OptIn(InternalROneBotApi::class, DelicateCoroutinesApi::class)

package cn.rtast.rob.common.http

import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.util.fromJson
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.await
import kotlinx.coroutines.promise

public object JsHttp {

    public val client: HttpClient = HttpClient(CIO)

    public fun HttpRequestBuilder.buildParams(params: Map<String, Any>?) {
        params?.forEach {
            parameter(it.key, it.value)
        }
    }

    public fun HttpRequestBuilder.buildHeaders(headers: Map<String, String>?) {
        headers?.forEach {
            header(it.key, it.value)
        }
    }

    public suspend fun get(
        url: String,
        params: Map<String, Any>?,
        headers: Map<String, String>?,
    ): String {
        return GlobalScope.promise {
            client.get(url) {
                buildParams(params)
                buildHeaders(headers)
            }.bodyAsText()
        }.await()
    }

    public suspend inline fun <reified T> get(
        url: String,
        params: Map<String, Any>?,
        headers: Map<String, String>?,
    ): T {
        return this.get(url, params, headers).fromJson<T>()
    }

    public suspend inline fun <reified T> post(
        url: String,
        formBody: Map<String, Any>?,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): T {
        return GlobalScope.promise {
            client.post(url) {
                buildParams(params)
                buildHeaders(headers)
                setBody(FormDataContent(Parameters.build {
                    formBody?.forEach {
                        append(it.key, it.value.toString())
                    }
                }))
            }.bodyAsText().fromJson<T>()
        }.await()
    }

    public suspend inline fun <reified T> post(
        url: String,
        jsonBody: String,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): T {
        return this.post(url, jsonBody, headers, params).fromJson<T>()
    }

    public suspend fun post(
        url: String,
        jsonBody: String,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): String {
        return GlobalScope.promise {
            client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(jsonBody)
                buildHeaders(headers)
                buildParams(params)
            }.bodyAsText()
        }.await()
    }

    public suspend inline fun <reified T> put(
        url: String,
        jsonBody: String,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): T {
        return this.put(url, jsonBody, headers, params).fromJson<T>()
    }

    public suspend fun put(
        url: String,
        jsonBody: String,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): String {
        return GlobalScope.promise {
            client.put(url) {
                contentType(ContentType.Application.Json)
                setBody(jsonBody)
                buildHeaders(headers)
                buildParams(params)
            }.bodyAsText()
        }.await()
    }

    public suspend inline fun <reified T> delete(
        url: String,
        jsonBody: String?,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): T {
        return this.delete(url, jsonBody, headers, params).fromJson<T>()
    }

    public suspend fun delete(
        url: String,
        jsonBody: String?,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): String {
        return GlobalScope.promise {
            client.delete(url) {
                contentType(ContentType.Application.Json)
                setBody(jsonBody)
                buildHeaders(headers)
                buildParams(params)
            }.bodyAsText()
        }.await()
    }
}