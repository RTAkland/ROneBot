/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 2025/4/11 13:25
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.event.raw.file

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import kotlinx.io.Buffer
import kotlinx.io.buffered
import kotlinx.io.files.Path
import kotlinx.io.files.SystemFileSystem

private val client = HttpClient(CIO)

public actual suspend fun saveFile(path: Path, bytes: ByteArray): Path {
    val buffer = Buffer().apply { write(bytes) }
    SystemFileSystem.sink(path).buffered().use { it.write(buffer, buffer.size) }
    return path
}

public actual suspend fun readBytes(url: String): ByteArray {
    return client.get(url).bodyAsBytes()
}