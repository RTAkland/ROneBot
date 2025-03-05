/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/7
 */


package cn.rtast.rob.util

import cn.rtast.rob.gson
import com.google.gson.reflect.TypeToken

public fun Any.toJson(): String {
    return gson.toJson(this)
}

public inline fun <reified T> String.fromJson(): T {
    return gson.fromJson(this, T::class.java)
}

public inline fun <reified T> String.fromArrayJson(): T {
    return gson.fromJson(this, object : TypeToken<T>() {}.type)
}
