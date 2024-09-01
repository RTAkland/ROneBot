/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/26
 */


package cn.rtast.rob.util

import cn.rtast.rob.gson
import com.google.gson.JsonParser
import com.google.gson.JsonSyntaxException

internal fun Any.toJson(): String {
    return gson.toJson(this)
}

internal inline fun <reified T> String.fromJson(): T {
    return gson.fromJson(this, T::class.java)
}


internal fun Any.isJsonArray(): Boolean {
    return try {
        val jsonElement = JsonParser.parseString(this.toJson())
        jsonElement.isJsonArray
    } catch (_: JsonSyntaxException) {
        false
    }
}