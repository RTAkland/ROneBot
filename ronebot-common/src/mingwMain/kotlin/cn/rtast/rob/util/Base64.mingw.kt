/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/22
 */


package cn.rtast.rob.util

import diglol.encoding.decodeBase64ToBytes
import diglol.encoding.encodeBase64ToString

public actual fun String.encodeToBase64(): String = this.encodeToByteArray().encodeToBase64()

public actual fun ByteArray.encodeToBase64(): String = this.encodeBase64ToString()

public actual fun String.decodeToString(): String = this.decodeBase64ToBytes()!!.decodeToString()

public actual fun String.decodeToByteArray(): ByteArray = this.decodeBase64ToBytes()!!