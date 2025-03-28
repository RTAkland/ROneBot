/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/22
 */

@file:OptIn(ExperimentalSerializationApi::class)

package cn.rtast.rob.util

import cn.rtast.rob.annotations.InternalROBApi
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

@InternalROBApi
public val json: Json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
    classDiscriminator = "_json_type_"
    encodeDefaults = true
    coerceInputValues = true
    decodeEnumsCaseInsensitive = true
}

@InternalROBApi
public inline fun <reified T> T.toJson(): String {
    return json.encodeToString(this)
}

@InternalROBApi
public inline fun <reified T> String.fromJson(): T {
    return json.decodeFromString<T>(this)
}

@InternalROBApi
public inline fun <reified T> String.fromArrayJson(): T {
    return json.decodeFromString<T>(this)
}
