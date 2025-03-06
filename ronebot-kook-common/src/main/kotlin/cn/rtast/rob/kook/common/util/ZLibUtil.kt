/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/7
 */

package cn.rtast.rob.kook.common.util

import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.util.zip.Inflater
import java.util.zip.InflaterInputStream

public fun InputStream.inflate(): String {
    return InflaterInputStream(this).use { inflaterStream ->
        String(inflaterStream.readBytes())
    }
}

public fun ByteArray.inflate(): String {
    return Inflater().run {
        setInput(this@inflate)
        ByteArrayOutputStream().use { output ->
            val buffer = ByteArray(1024)
            while (!finished()) {
                val count = inflate(buffer)
                output.write(buffer, 0, count)
            }
            String(output.toByteArray())
        }
    }
}
