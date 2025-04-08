/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/31
 */

@file:OptIn(ExperimentalForeignApi::class)

package cn.rtast.rob.starter.backend.util

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.cstr
import kotlinx.cinterop.usePinned
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import kotlinx.io.readByteArray
import kotlinx.io.readString
import kotlinx.io.writeString
import platform.posix.*

fun Path.writeBytes(bytes: ByteArray) {
    SystemFileSystem.sink(this).buffered().use { it.write(bytes) }
}

fun Path.writeText(text: String) {
//    println(this.toString() + text)
    SystemFileSystem.sink(this).buffered().use { it.writeString(text) }
//    val filePath = this.toString()
//    val file = fopen(filePath, "w") ?: error("无法打开文件 $filePath")
//    try {
//        text.cstr.usePinned { pinned ->
//            fputs(text, file)
//        }
//    } finally {
//        fclose(file)
//    }
}

fun Path.readBytes(): ByteArray {
    return SystemFileSystem.source(this).buffered().use { it.readByteArray() }
}

fun Path.readText(): String {
    return SystemFileSystem.source(this).buffered().use { it.readString() }
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