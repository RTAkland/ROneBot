/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */

@file:Suppress("unused")

package cn.rtast.rob.common.ext

import cn.rtast.rob.util.fromJson
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody


/**
 * 封装好的Http工具类, 包含了大部分的http方法
 * 需要http请求来发送消息/操作的模块可以引入
 */
public object Http {

    public val jsonMediaType: MediaType = "application/json; charset=utf-8".toMediaType()
    public val jsonHeader: Map<String, String> = mapOf(
        "Content-Type" to "application/json; charset=utf-8",
        "Accept" to "application/json"
    )
    public val okHttpClient: OkHttpClient = OkHttpClient()

    public fun buildParams(url: String, params: Map<String, Any>?): String {
        val paramsUrl = StringBuilder("$url?")
        params?.let {
            it.forEach { (key, value) ->
                paramsUrl.append("$key=$value&")
            }
            paramsUrl.dropLast(1)
        }
        return if (params != null) paramsUrl.toString() else url
    }

    public fun addHeaders(request: Request.Builder, headers: Map<String, String>?): Request.Builder {
        headers?.let {
            it.forEach { (key, value) ->
                request.addHeader(key, value)
            }
        }
        return request
    }

    public inline fun <reified T> executeRequest(request: Request): T {
        val result = okHttpClient.newCall(request).execute().body.string()
        return result.fromJson<T>()
    }

    public fun executeRequest(request: Request): String {
        return okHttpClient.newCall(request).execute().body.string()
    }

    @JvmOverloads
    public fun get(
        url: String,
        params: Map<String, Any>? = null,
        headers: Map<String, String>? = null,
    ): String {
        val paramsUrl = buildParams(url, params)
        val request = Request.Builder()
            .url(paramsUrl)
            .get()
        val headerRequest = addHeaders(request, headers)
        return this.executeRequest(headerRequest.build())
    }

    @JvmOverloads
    public inline fun <reified T> get(
        url: String,
        params: Map<String, Any>? = null,
        headers: Map<String, String>? = null,
    ): T {
        return get(url, params, headers).fromJson<T>()
    }

    @JvmOverloads
    public inline fun <reified T> post(
        url: String,
        formBody: Map<String, Any>? = null,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): T {
        val body = FormBody.Builder()
        val paramsUrl = buildParams(url, params)
        formBody?.let {
            it.forEach { (key, value) ->
                body.add(key, value.toString())
            }
        }
        val request = Request.Builder()
            .post(body.build())
            .url(paramsUrl)
        val headerRequest = addHeaders(request, headers)
        return this.executeRequest<T>(headerRequest.build())
    }

    @JvmOverloads
    public inline fun <reified T> post(
        url: String,
        jsonBody: String,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): T {
        val result = post(url, jsonBody, headers, params)
        return result.fromJson<T>()
    }

    @JvmOverloads
    public fun post(
        url: String,
        jsonBody: String,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): String {
        val body = jsonBody.toRequestBody(jsonMediaType)
        val paramsUrl = buildParams(url, params)
        val request = Request.Builder()
            .post(body)
            .url(paramsUrl)
        this.addHeaders(request, jsonHeader)
        val headerRequest = addHeaders(request, headers)
        return this.executeRequest(headerRequest.build())
    }

    @JvmOverloads
    public inline fun <reified T> post(
        url: String,
        form: FormBody,
        headers: Map<String, String>? = null,
    ): T {
        val request = Request.Builder()
            .post(form)
            .url(buildParams(url, headers))
        val headerRequest = addHeaders(request, headers)
        return this.executeRequest(headerRequest.build()).fromJson<T>()
    }

    @JvmOverloads
    public inline fun <reified T> post(
        url: String,
        form: MultipartBody,
        headers: Map<String, String>? = null,
    ): T {
        val request = Request.Builder()
            .post(form)
            .url(buildParams(url, headers))
        val headerRequest = addHeaders(request, headers)
        return this.executeRequest(headerRequest.build()).fromJson<T>()
    }

    public inline fun <reified T> put(
        url: String,
        jsonBody: String,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): T {
        val paramsUrl = buildParams(url, params)
        val request = Request.Builder()
            .url(paramsUrl)
            .put(jsonBody.toRequestBody(jsonMediaType))
        val headerRequest = addHeaders(request, headers)
        return this.executeRequest(headerRequest.build()).fromJson<T>()
    }

    public fun put(
        url: String,
        jsonBody: String,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): String {
        val paramsUrl = buildParams(url, params)
        val request = Request.Builder()
            .url(paramsUrl)
            .put(jsonBody.toRequestBody(jsonMediaType))
        val headerRequest = addHeaders(request, headers)
        return this.executeRequest(headerRequest.build())
    }

    public fun buildDeleteRequest(
        url: String,
        jsonBody: String? = null,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): Request {
        val paramsUrl = buildParams(url, params)
        val requestBuilder = Request.Builder()
            .url(paramsUrl)

        if (jsonBody != null) {
            requestBuilder.delete(jsonBody.toRequestBody(jsonMediaType))
        } else {
            requestBuilder.delete()
        }

        return addHeaders(requestBuilder, headers).build()
    }

    @JvmOverloads
    public inline fun <reified T> delete(
        url: String,
        jsonBody: String? = null,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): T {
        val request = buildDeleteRequest(url, jsonBody, headers, params)
        return executeRequest(request).fromJson<T>()
    }

    @JvmOverloads
    public fun delete(
        url: String,
        jsonBody: String? = null,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): String {
        val request = buildDeleteRequest(url, jsonBody, headers, params)
        return executeRequest(request)
    }
}