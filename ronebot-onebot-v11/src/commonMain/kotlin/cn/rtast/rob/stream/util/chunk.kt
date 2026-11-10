/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 3:44 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.stream.util

import kotlinx.io.Buffer
import kotlinx.io.readByteArray

internal data class StreamChunk(
    val totalSize: Long,
    val sha256: String,
    val chunks: List<ByteArray>,
    val totalChunks: Int,
)

/**
 * 分片
 * @param size size kb
 */
internal fun Buffer.chunkedBySize(size: Long = 64 * 1024): StreamChunk {
    val totalSize = this.size
    val chunks = mutableListOf<ByteArray>()
    val sha256Stream = SHA256Context.create()
    var readBytes = 0L

    while (readBytes < totalSize) {
        val remaining = (totalSize - readBytes).coerceAtMost(size).toInt()
        val chunk = this.readByteArray(remaining)
        chunks += chunk
        sha256Stream.append(chunk)
        readBytes += chunk.size
    }

    val sha256Hash = sha256Stream.finalizeBytes().toHexString()
    return StreamChunk(totalSize, sha256Hash, chunks, chunks.size)
}

internal fun ByteArray.chunkedBySize(size: Long = 64 * 1024): StreamChunk =
    Buffer().apply { write(this@chunkedBySize) }.chunkedBySize(size)

