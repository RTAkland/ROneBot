/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/10
 */


package cn.rtast.rob.util

import java.util.*


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
