/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/15
 */

package cn.rtast.rob.starter.frontend.util

import kotlinx.serialization.json.Json

public inline fun <reified  T> JsString.fromJson(): T {
    return Json.decodeFromString<T>(this.toString())
}

public inline fun <reified  T> String.fromJson(): T {
    return Json.decodeFromString<T>(this)
}