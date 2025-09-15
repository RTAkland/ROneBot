/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 12:38 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(ExperimentalSerializationApi::class)

package cn.rtast.rob.starter.frontend.util

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

public val json: Json = Json {
    prettyPrint = true
    ignoreUnknownKeys = true
    explicitNulls = false
    classDiscriminator = "_json_type_"
    encodeDefaults = true
    coerceInputValues = true
    decodeEnumsCaseInsensitive = true
    isLenient = true
}

public inline fun <reified T> T.toJson(): String {
    return json.encodeToString(this)
}

public inline fun <reified T> String.fromJson(): T {
    return json.decodeFromString<T>(this)
}