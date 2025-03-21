/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/22
 */


package cn.rtast.rob.util

import kotlinx.serialization.json.Json

public val json: Json = Json { prettyPrint = true }

public actual fun Any.toJson(): String {
    return json.encodeToString(this)
}

public actual inline fun <reified T> String.fromJson(): T {
    return json.decodeFromString<T>(this)
}

public actual inline fun <reified T> String.fromArrayJson(): T {
    return json.decodeFromString<T>(this)
}