/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/28
 */

package cn.rtast.rob.ext.bytearray

public operator fun ByteArray.plus(other: ByteArray): ByteArray =
    this.toMutableList().apply { addAll(other.toList()) }.toByteArray()

/**
 * 自己实现的将ByteArray转为UTF-8字符串的方法
 */
public fun ByteArray.toUtf8String(): String {
    val chars = mutableListOf<Char>()
    for (byte in this) {
        chars.add(byte.toInt().toChar())
    }
    return chars.joinToString("")
}

public fun String.toByteArray(): ByteArray {
    val bytes = mutableListOf<Byte>()
    for (i in this) {
        bytes.add(i.code.toByte())
    }
    return bytes.toByteArray()
}

public fun ByteArray.toLong(): Long {
    var value: Long = 0
    for (i in this.indices) {
        value = value or ((this[i].toLong() and 0xFF) shl (56 - i * 8))
    }
    return value
}

public fun ByteArray.toLong(startIndex: Int): Long {
    var value: Long = 0
    for (i in 0 until 8) {
        value = value or ((this[startIndex + i].toLong() and 0xFF) shl (56 - i * 8))
    }
    return value
}