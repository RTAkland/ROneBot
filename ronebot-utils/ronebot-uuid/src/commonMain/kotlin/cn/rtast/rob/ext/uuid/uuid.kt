/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/28
 */

@file:OptIn(ExperimentalUuidApi::class)

package cn.rtast.rob.ext.uuid

import kotlin.uuid.ExperimentalUuidApi
import kotlin.uuid.Uuid

private fun ByteArray.toLong(): Long {
    return foldIndexed(0L) { index, acc, byte ->
        acc or ((byte.toLong() and 0xFF) shl ((7 - index) * 8))
    }
}

/**
 * UUID最高位
 */
public val Uuid.msb: Long get() = this.toByteArray().sliceArray(0..7).toLong()

/**
 * UUID最低位
 */
public val Uuid.lsb: Long get() = this.toByteArray().sliceArray(8..15).toLong()

public fun uuidOf(msb: Long, lsb: Long): Uuid {
    return Uuid.fromLongs(msb, lsb)
}