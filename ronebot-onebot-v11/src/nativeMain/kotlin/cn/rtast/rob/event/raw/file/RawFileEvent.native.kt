/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

@file:OptIn(ExperimentalROneBotApi::class)

package cn.rtast.rob.event.raw.file

import cn.rtast.rob.annotations.ExperimentalROneBotApi
import io.ktor.client.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.io.Buffer
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem
import cn.rtast.rob.util.ws.getClientEngine

private val client = HttpClient(getClientEngine())

internal actual suspend fun readBytes(url: String): ByteArray {
    return client.get(url).bodyAsBytes()
}

internal actual suspend fun saveFile(path: Path, bytes: ByteArray): Path {
    val buffer = Buffer().apply { write(bytes) }
    SystemFileSystem.sink(path).use { it.write(buffer, buffer.size) }
    return path
}