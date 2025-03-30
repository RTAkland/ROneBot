/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/28
 */

package cn.rtast.rob.ext.file

import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.io.readString
import kotlinx.io.writeString

/**
 * 读取纯文本[String]
 */
public fun Path.readText(): String {
    return SystemFileSystem.source(this).use { it.buffered().readString() }
}

/**
 * 读取[ByteArray]
 */
public fun Path.readByteArray(): ByteArray {
    return SystemFileSystem.source(this).use { it.buffered().readByteArray() }
}

/**
 * 写入[ByteArray]
 */
public fun Path.writeByteArray(bytes: ByteArray) {
    SystemFileSystem.sink(this).use { it.buffered().write(bytes) }
}

/**
 * 写入[String]
 */
public fun Path.writeText(text: String) {
    SystemFileSystem.sink(this).use { it.buffered().writeString(text) }
}

/**
 * 创建文件夹
 */
public expect fun Path.mkdirs()

/**
 * 创建新文件(空)
 */
public expect fun Path.createFile()