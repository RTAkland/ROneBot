/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/31
 */

package cn.rtast.rob.starter.backend.util

import kotlinx.io.*
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

fun Path.writeBytes(bytes: ByteArray) {
    val buffer = Buffer().apply { write(bytes) }
    SystemFileSystem.sink(this).use { it.write(buffer, buffer.size) }
}

fun Path.writeText(text: String) {
    val buffer = Buffer().apply { writeString(text) }
    SystemFileSystem.sink(this).use { it.write(buffer, buffer.size) }
}

fun Path.readBytes(): ByteArray {
    return SystemFileSystem.source(this).use { it.buffered().readByteArray() }
}

fun Path.readText(): String {
    return SystemFileSystem.source(this).use { it.buffered().readString() }
}

fun Path.exists(): Boolean {
    return SystemFileSystem.exists(this)
}

fun Path.mkdirs(): Path {
    SystemFileSystem.createDirectories(this)
    return this
}

fun Path.deleteRecursively() {
    SystemFileSystem.list(this).forEach {
        if (SystemFileSystem.metadataOrNull(it)!!.isDirectory) {
            it.deleteRecursively()
        }
        SystemFileSystem.delete(it)
    }
}

fun Path.delete() {
    SystemFileSystem.delete(this)
}