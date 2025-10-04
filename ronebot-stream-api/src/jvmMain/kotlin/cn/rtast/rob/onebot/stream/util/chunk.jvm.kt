/*
 * Copyright © 2025 RTAkland
 * Date: 10/5/25, 4:01 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.onebot.stream.util

import java.io.File
import java.io.FileInputStream

/**
 * chunk extended for java.io.File
 */
internal fun File.chunkedBySize(chunkSize: Int = 64 * 1024): StreamChunk {
    val totalSize = this.length()
    val chunks = mutableListOf<ByteArray>()
    val sha256Stream = Sha256Stream()
    FileInputStream(this).use { input ->
        val buffer = ByteArray(chunkSize)
        while (true) {
            val bytesRead = input.read(buffer)
            if (bytesRead <= 0) break
            val chunk = if (bytesRead == chunkSize) buffer.copyOf() else buffer.copyOf(bytesRead)
            chunks += chunk
            sha256Stream.update(chunk)
        }
    }
    val sha256Hash = sha256Stream.finalize().toHexString()
    return StreamChunk(totalSize, sha256Hash, chunks, chunks.size)
}