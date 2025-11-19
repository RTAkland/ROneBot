/*
 * Copyright © 2025 RTAkland
 * Date: 11/19/25, 6:53 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.event.raw.file

import kotlinx.browser.window
import kotlinx.coroutines.await
import org.khronos.webgl.Int8Array

internal actual suspend fun saveFile(path: kotlinx.io.files.Path, bytes: ByteArray): kotlinx.io.files.Path {
    throw IllegalStateException("Wasm/JS target not support save file to disk")
}

internal actual suspend fun readBytes(url: String): ByteArray {
    val response = window.fetch(url).await()
    val buffer = response.arrayBuffer().await()
    return Int8Array(buffer).unsafeCast<ByteArray>()
}