/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

package cn.rtast.rob.event.raw.lagrange

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsBytes
import okio.NodeJsFileSystem
import okio.Path

private val client = HttpClient(CIO)

public actual suspend fun readBytes(url: String): ByteArray {
    return client.get(url).bodyAsBytes()
}

public actual suspend fun saveFile(path: Path, bytes: ByteArray) {
    NodeJsFileSystem.write(path) {
        write(bytes)
    }
}