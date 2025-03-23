/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/22
 */

package cn.rtast.rob.util

import kotlinx.serialization.json.Json

public val json: Json = Json {
    // disable pretty printing to save bandwidth
//    prettyPrint = true
    ignoreUnknownKeys = true
    explicitNulls = false
    classDiscriminator = "_json_type_"
    encodeDefaults = true
}

public inline fun <reified T> T.toJson(): String {
    return json.encodeToString(this)
}

public inline fun <reified T> String.fromJson(): T {
    return json.decodeFromString<T>(this)
}

public inline fun <reified T> String.fromArrayJson(): T {
    return json.decodeFromString<T>(this)
}
