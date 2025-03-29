/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/15
 */

@file:OptIn(ExperimentalSerializationApi::class)

package cn.rtast.rob.starter.frontend.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

public val json: Json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
    classDiscriminator = "_json_type_"
    encodeDefaults = true
    coerceInputValues = true
    decodeEnumsCaseInsensitive = true
    isLenient = true
}

public inline fun <reified  T> JsString.fromJson(): T {
    return json.decodeFromString<T>(this.toString())
}

public inline fun <reified  T> String.fromJson(): T {
    return json.decodeFromString<T>(this)
}