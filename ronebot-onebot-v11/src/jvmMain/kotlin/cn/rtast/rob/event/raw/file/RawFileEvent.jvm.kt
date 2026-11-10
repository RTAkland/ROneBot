/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

@file:JvmName("RawFileEventUtil")

package cn.rtast.rob.event.raw.file

import cn.rtast.rob.commonCoroutineScope
import cn.rtast.rob.event.raw.file.RawFileEvent.File
import kotlinx.coroutines.*
import kotlinx.io.files.Path
import java.io.ByteArrayOutputStream
import java.net.URI
import java.io.File as JvmFile
import java.nio.file.Path as JvmPath

internal actual suspend fun readBytes(url: String): ByteArray = withContext(Dispatchers.IO) {
    val connection = URI(url).toURL().openConnection()
    connection.inputStream.use { inputStream ->
        ByteArrayOutputStream().use { outputStream ->
            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }
            outputStream.toByteArray()
        }
    }
}

public actual suspend fun saveFile(path: Path, bytes: ByteArray): Path {
    JvmFile(path.toString()).writeBytes(bytes)
    return path
}

/**
 * 在JVM平台上特有的使用[File]对象保存文件
 */
public suspend fun RawFileEvent.saveTo(file: JvmFile): JvmFile {
    file.writeBytes(readBytes())
    return file
}

/**
 * 兼容Java而写的保存文件的方法
 * 并且使用Java的[File]对象
 */
public fun jvmSaveTo(event: RawFileEvent, file: JvmFile): JvmFile = runBlocking { event.saveTo(file) }

/**
 * 异步的使用[File]对象保存文件
 */
public suspend fun RawFileEvent.saveToAsync(file: JvmFile) {
    saveToAsync(Path(file.toString()))
}

/**
 * 兼容Java而写的异步保存文件的方法
 * 并且使用Java的[File]对象
 */
public fun jvmSaveToAsync(event: RawFileEvent, file: JvmFile): Job =
    commonCoroutineScope.launch { event.saveToAsync(file) }

/**
 * 使用[java.nio.file.Path]对象来保存文件
 */
public suspend fun RawFileEvent.saveTo(path: JvmPath): JvmFile {
    return saveTo(path.toFile())
}

/**
 * 兼容Java而写的保存文件的方法
 * 并且使用Java的[java.nio.file.Path]对象
 */
public fun jvmSaveTo(event: RawFileEvent, path: JvmPath): JvmFile = runBlocking { event.saveTo(path) }

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
public fun jvmSaveToAsync(event: RawFileEvent, path: JvmPath): Job =
    commonCoroutineScope.launch { event.saveToAsync(path) }