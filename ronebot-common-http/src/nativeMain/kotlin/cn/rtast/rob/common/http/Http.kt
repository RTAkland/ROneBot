/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/31
 */

@file:OptIn(InternalROneBotApi::class)

package cn.rtast.rob.common.http

import cn.rtast.rob.annotations.InternalROneBotApi
import cn.rtast.rob.util.fromJson
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.request.forms.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

public actual object Http {

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

    public actual fun get(
        url: String,
        params: Map<String, Any>?,
        headers: Map<String, String>?,
    ): String {
        return runBlocking {
            client.get(url) {
                buildParams(params)
                buildHeaders(headers)
            }.bodyAsText()
        }
    }

    public actual inline fun <reified T> get(
        url: String,
        params: Map<String, Any>?,
        headers: Map<String, String>?,
    ): T {
        return this.get(url, params, headers).fromJson<T>()
    }

    public actual inline fun <reified T> post(
        url: String,
        formBody: Map<String, Any>?,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): T {
        return runBlocking {
            client.post(url) {
                buildParams(params)
                buildHeaders(headers)
                setBody(FormDataContent(Parameters.build {
                    formBody?.forEach {
                        append(it.key, it.value.toString())
                    }
                }))
            }.bodyAsText().fromJson<T>()
        }
    }

    public actual inline fun <reified T> post(
        url: String,
        jsonBody: String,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): T {
        return this.post(url, jsonBody, headers, params).fromJson<T>()
    }

    public actual fun post(
        url: String,
        jsonBody: String,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): String {
        return runBlocking {
            client.post(url) {
                contentType(ContentType.Application.Json)
                setBody(jsonBody)
                buildHeaders(headers)
                buildParams(params)
            }.bodyAsText()
        }
    }

    public actual inline fun <reified T> put(
        url: String,
        jsonBody: String,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): T {
        return this.put(url, jsonBody, headers, params).fromJson<T>()
    }

    public actual fun put(
        url: String,
        jsonBody: String,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): String {
        return runBlocking {
            client.put(url) {
                contentType(ContentType.Application.Json)
                setBody(jsonBody)
                buildHeaders(headers)
                buildParams(params)
            }.bodyAsText()
        }
    }

    public actual inline fun <reified T> delete(
        url: String,
        jsonBody: String?,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): T {
        return this.delete(url, jsonBody, headers, params).fromJson<T>()
    }

    public actual fun delete(
        url: String,
        jsonBody: String?,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): String {
        return runBlocking {
            client.delete(url) {
                contentType(ContentType.Application.Json)
                setBody(jsonBody)
                buildHeaders(headers)
                buildParams(params)
            }.bodyAsText()
        }
    }
}