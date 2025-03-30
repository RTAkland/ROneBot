/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

package cn.rtast.rob.event.raw.lagrange

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

private val client = HttpClient(CIO)

internal actual suspend fun readBytes(url: String): ByteArray {
    return client.get(url).bodyAsBytes()
}

internal actual suspend fun saveFile(path: Path, bytes: ByteArray) {
    SystemFileSystem.sink(path).use { it.buffered().write(bytes) }
}