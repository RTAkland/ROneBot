/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/22
 */

package cn.rtast.rob.util

public expect fun String.encodeToBase64(): String

public expect fun ByteArray.encodeToBase64(): String

public expect fun String.decodeToString(): String

public expect fun String.decodeToByteArray(): ByteArray
