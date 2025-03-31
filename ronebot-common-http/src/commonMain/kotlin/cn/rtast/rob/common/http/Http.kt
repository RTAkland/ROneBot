/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/31
 */

@file:Suppress("unused")

package cn.rtast.rob.common.http

public expect object Http {
    public fun get(
        url: String,
        params: Map<String, Any>? = null,
        headers: Map<String, String>? = null,
    ): String

    public inline fun <reified T> get(
        url: String,
        params: Map<String, Any>? = null,
        headers: Map<String, String>? = null,
    ): T

    public inline fun <reified T> post(
        url: String,
        formBody: Map<String, Any>? = null,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): T

    public inline fun <reified T> post(
        url: String,
        jsonBody: String,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): T

    public fun post(
        url: String,
        jsonBody: String,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): String

    public inline fun <reified T> put(
        url: String,
        jsonBody: String,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): T

    public fun put(
        url: String,
        jsonBody: String,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): String

    public inline fun <reified T> delete(
        url: String,
        jsonBody: String? = null,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): T

    public fun delete(
        url: String,
        jsonBody: String? = null,
        headers: Map<String, String>? = null,
        params: Map<String, Any>? = null,
    ): String
}