/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

@file:OptIn(ExperimentalROneBotApi::class)

package cn.rtast.rob.event.raw.file

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.io.RFile
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

public actual suspend fun saveFile(file: RFile): RFile {
    file.toFile().writeBytes(file.readBytes())
    return file
}

/**
 * 在JVM平台上特有的使用[File]对象保存文件
 */
public suspend fun RawFileEvent.saveTo(file: File): File {
    file.writeBytes(readBytes())
    return file
}

/**
 * 异步的使用[File]对象保存文件
 */
public suspend fun RawFileEvent.saveToAsync(file: File) {
    saveToAsync(Path(file.toString()))
}

/**
 * 使用[java.nio.file.Path]对象来保存文件
 */
public suspend fun RawFileEvent.saveTo(path: JvmPath): File {
    return saveTo(path.toFile())
}

/**
 * 异步的使用[java.nio.file.Path]对象来保存文件
 */
public suspend fun RawFileEvent.saveToAsync(path: JvmPath) {
    saveToAsync(path.toFile())
}