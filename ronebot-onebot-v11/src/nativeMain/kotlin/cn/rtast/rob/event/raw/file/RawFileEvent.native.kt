/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

@file:OptIn(ExperimentalROneBotApi::class)

package cn.rtast.rob.event.raw.file

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import cn.rtast.rob.io.RFile
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.io.Buffer
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

private val client = HttpClient(CIO)

internal actual suspend fun readBytes(url: String): ByteArray {
    return client.get(url).bodyAsBytes()
}

internal actual suspend fun saveFile(path: Path, bytes: ByteArray): Path {
    val buffer = Buffer().apply { write(bytes) }
    SystemFileSystem.sink(path).use { it.write(buffer, buffer.size) }
    return path
}

internal actual suspend fun saveFile(file: RFile): RFile {
    val buffer = Buffer().apply { write(file.readBytes()) }
    SystemFileSystem.sink(file.toPath()).use { it.write(buffer, buffer.size) }
    return file
}