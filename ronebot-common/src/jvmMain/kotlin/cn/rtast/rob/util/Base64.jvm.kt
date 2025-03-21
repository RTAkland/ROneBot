/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/10
 */

@file:Suppress("unused")

package cn.rtast.rob.util

import java.util.*


public actual fun String.encodeToBase64(): String {
    return Base64.getEncoder().encodeToString(this.toByteArray(Charsets.UTF_8))
}

public actual fun ByteArray.encodeToBase64(): String {
    return Base64.getEncoder().encodeToString(this)
}

public actual fun String.decodeToString(): String {
    return String(Base64.getDecoder().decode(this), Charsets.UTF_8)
}

public actual fun String.decodeToByteArray(): ByteArray {
    return Base64.getDecoder().decode(this)
}
