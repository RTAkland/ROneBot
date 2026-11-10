/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/22
 */

package cn.rtast.rob.util

import cn.rtast.rob.annotations.InternalROneBotApi
import kotlinx.serialization.json.Json

@InternalROneBotApi
public val json: Json = Json {
    ignoreUnknownKeys = true
    explicitNulls = false
    classDiscriminator = "_class"
    encodeDefaults = true
    coerceInputValues = true
    decodeEnumsCaseInsensitive = true
    isLenient = true
}

@InternalROneBotApi
public inline fun <reified T> T.toJson(): String {
    return json.encodeToString(this)
}

@InternalROneBotApi
public inline fun <reified T> String.fromJson(): T {
    return json.decodeFromString<T>(this)
}

@InternalROneBotApi
public inline fun <reified T> String.fromArrayJson(): T {
    return json.decodeFromString<T>(this)
}
