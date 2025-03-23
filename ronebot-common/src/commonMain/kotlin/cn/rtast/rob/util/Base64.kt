/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/22
 */

@file:OptIn(ExperimentalEncodingApi::class)

package cn.rtast.rob.util

import kotlin.io.encoding.Base64
import kotlin.io.encoding.ExperimentalEncodingApi

public fun String.encodeToBase64(): String {
    return Base64.encode(this.encodeToByteArray())
}

public fun ByteArray.encodeToBase64(): String {
    return Base64.encode(this)
}
