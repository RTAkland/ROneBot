/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/30
 */

@file:Suppress("unused")

package test

import cn.rtast.rob.gson
import com.google.gson.reflect.TypeToken
import java.net.URI
import java.net.URL
import java.util.*

fun Any.toJson(): String {
    return gson.toJson(this)
}

inline fun <reified T> String.fromJson(): T {
    return gson.fromJson(this, T::class.java)
}

inline fun <reified T> String.fromArrayJson(): T {
    return gson.fromJson(this, object : TypeToken<T>() {}.type)
}


fun String.encodeToBase64(): String {
    return Base64.getEncoder().encodeToString(this.toByteArray(Charsets.UTF_8))
}

fun ByteArray.encodeToBase64(): String {
    return Base64.getEncoder().encodeToString(this)
}

fun String.decodeToString(): String {
    return String(Base64.getDecoder().decode(this), Charsets.UTF_8)
}

fun String.decodeToByteArray(): ByteArray {
    return Base64.getDecoder().decode(this)
}

private val urlRegex = Regex(
    "^(https?://)?" +
            "([\\w\\-]+\\.)+[a-zA-Z]{2,6}" +
            "|(https?://)?" +
            "((25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}" +
            "(25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)" +
            "(:\\d{1,5})?" +
            "(/.*)?$"
)

fun String.toURL(): URL {
    return URI(this).toURL()
}

fun String.toURI(): URI {
    return URI(this)
}

fun String.isValidUrl(): Boolean {
    return urlRegex.matches(this)
}