/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/30
 */

package cn.rtast.rob.qqbot.util

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken

public var rtastUtilGson: Gson = GsonBuilder()
    .setPrettyPrinting()
    .disableHtmlEscaping()
    .create()

public fun Any.toJson(): String {
    return rtastUtilGson.toJson(this)
}

public inline fun <reified T> String.fromJson(): T {
    return rtastUtilGson.fromJson(this, T::class.java)
}

public inline fun <reified T> String.fromArrayJson(): T {
    return rtastUtilGson.fromJson(this, object : TypeToken<T>() {}.type)
}