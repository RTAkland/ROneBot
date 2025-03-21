/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/7
 */


package cn.rtast.rob.util

import cn.rtast.rob.gson
import com.google.gson.reflect.TypeToken

public actual fun Any.toJson(): String {
    return gson.toJson(this)
}

public actual inline fun <reified T> String.fromJson(): T {
    return gson.fromJson(this, T::class.java)
}

public actual inline fun <reified T> String.fromArrayJson(): T {
    return gson.fromJson(this, object : TypeToken<T>() {}.type)
}