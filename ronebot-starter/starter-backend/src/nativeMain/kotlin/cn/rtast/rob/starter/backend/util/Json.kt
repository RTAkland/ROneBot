/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/31
 */

@file:OptIn(ExperimentalSerializationApi::class)

package cn.rtast.rob.starter.backend.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json


val json: Json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
    classDiscriminator = "_json_type_"
    encodeDefaults = true
    coerceInputValues = true
    decodeEnumsCaseInsensitive = true
    isLenient = true
}

inline fun <reified T> T.toJson(): String {
    return json.encodeToString(this)
}

inline fun <reified T> String.fromJson(): T {
    return json.decodeFromString<T>(this)
}