/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

@file:OptIn(ExperimentalROneBotApi::class)

package cn.rtast.rob.event.raw.file

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.annotations.JvmOnly
import cn.rtast.rob.commonCoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.io.files.Path
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI
import kotlin.io.writeBytes
import kotlin.use
import java.nio.file.Path as JvmPath

internal actual suspend fun readBytes(url: String): ByteArray {
    val connection = URI(url).toURL().openConnection()
    connection.inputStream.use { inputStream ->
        ByteArrayOutputStream().use { outputStream ->
            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            return outputStream.toByteArray()
        }
    }
}

public actual suspend fun saveFile(path: Path, bytes: ByteArray): Path {
    File(path.toString()).writeBytes(bytes)
    return path
}

/**
 * 在JVM平台上特有的使用[File]对象保存文件
 */
public suspend fun RawFileEvent.saveTo(file: File): File {
    file.writeBytes(readBytes())
    return file
}

/**
 * 兼容Java而写的保存文件的方法
 * 并且使用Java的[File]对象
 */
@JvmOnly
public fun jvmSaveTo(event: RawFileEvent, file: File): File = runBlocking { event.saveTo(file) }

/**
 * 异步的使用[File]对象保存文件
 */
public suspend fun RawFileEvent.saveToAsync(file: File) {
    saveToAsync(Path(file.toString()))
}

/**
 * 兼容Java而写的异步保存文件的方法
 * 并且使用Java的[File]对象
 */
@JvmOnly
public fun jvmSaveToAsync(event: RawFileEvent, file: File): Job =
    commonCoroutineScope.launch { event.saveToAsync(file) }

/**
 * 使用[java.nio.file.Path]对象来保存文件
 */
public suspend fun RawFileEvent.saveTo(path: JvmPath): File {
    return saveTo(path.toFile())
}

/**
 * 兼容Java而写的保存文件的方法
 * 并且使用Java的[java.nio.file.Path]对象
 */
@JvmOnly
public fun jvmSaveTo(event: RawFileEvent, path: JvmPath): File = runBlocking { event.saveTo(path) }

/**
 * 异步的使用[java.nio.file.Path]对象来保存文件
 */
public suspend fun RawFileEvent.saveToAsync(path: JvmPath) {
    saveToAsync(path.toFile())
}

/**
 * 兼容Java而写的异步保存文件的方法
 * 并且使用Java的[java.nio.file.Path]对象
 */
@JvmOnly
public fun jvmSaveToAsync(event: RawFileEvent, path: JvmPath): Job =
    commonCoroutineScope.launch { event.saveToAsync(path) }