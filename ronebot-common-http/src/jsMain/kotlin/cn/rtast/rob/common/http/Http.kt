/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 12:18
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

@file:Suppress("unused")

package cn.rtast.rob.common.http

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*

public actual object Http {

    public val client: HttpClient = HttpClient(CIO)

    public fun HttpRequestBuilder.buildParams(params: Map<String, Any>?) {
        TODO()
    }

    public fun HttpRequestBuilder.buildHeaders(headers: Map<String, String>?) {
        TODO()
    }

    public actual fun get(
        url: String,
        params: Map<String, Any>?,
        headers: Map<String, String>?,
    ): String {
        TODO()
    }

    public actual inline fun <reified T> get(
        url: String,
        params: Map<String, Any>?,
        headers: Map<String, String>?,
    ): T {
        TODO()
    }

    public actual inline fun <reified T> post(
        url: String,
        formBody: Map<String, Any>?,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): T {
        TODO()
    }

    public actual inline fun <reified T> post(
        url: String,
        jsonBody: String,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): T {
        TODO()
    }

    public actual fun post(
        url: String,
        jsonBody: String,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): String {
        TODO()
    }

    public actual inline fun <reified T> put(
        url: String,
        jsonBody: String,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): T {
        TODO()
    }

    public actual fun put(
        url: String,
        jsonBody: String,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): String {
        TODO()
    }

    public actual inline fun <reified T> delete(
        url: String,
        jsonBody: String?,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): T {
        TODO()
    }

    public actual fun delete(
        url: String,
        jsonBody: String?,
        headers: Map<String, String>?,
        params: Map<String, Any>?,
    ): String {
        TODO()
    }
}