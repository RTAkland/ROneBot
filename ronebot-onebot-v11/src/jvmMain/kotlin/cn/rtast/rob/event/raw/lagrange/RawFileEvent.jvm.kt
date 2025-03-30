/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/23
 */

package cn.rtast.rob.event.raw.lagrange

import kotlinx.io.files.Path
import java.io.ByteArrayOutputStream
import java.io.File
import java.net.URI

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

public actual suspend fun saveFile(path: Path, bytes: ByteArray) {
    File(path.toString()).writeBytes(bytes)
}