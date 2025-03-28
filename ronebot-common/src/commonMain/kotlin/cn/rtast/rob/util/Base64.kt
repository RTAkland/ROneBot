/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/22
 */

@file:OptIn(ExperimentalEncodingApi::class)

package cn.rtast.rob.util

import cn.rtast.rob.ext.bytearray.toUtf8String
import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

/**
 * 将字符串编码为Base64
 */
public fun String.encodeToBase64(): String {
    return Base64.encode(this.encodeToByteArray())
}

/**
 * 将[ByteArray]解码为Base64
 */
public fun ByteArray.encodeToBase64(): String {
    return Base64.encode(this)
}

/**
 * 将[ByteArray]解码为Base64字符串
 */
public fun ByteArray.decodeToBase64(): String {
    return Base64.decode(this).toUtf8String()
}

/**
 * 将[ByteArray]解码为Base64[ByteArray]
 */
public fun ByteArray.decodeToByteArrayBase64(): ByteArray {
    return Base64.decode(this)
}

/**
 * 将Base64字符串解码为UTF-8字符串
 */
public fun String.decodeToBase64(): String {
    return Base64.decode(this).toUtf8String()
}

/**
 * 将Base64字符串解码为[ByteArray]
 */
public fun String.decodeToByteArrayBase64(): ByteArray {
    return Base64.decode(this)
}
