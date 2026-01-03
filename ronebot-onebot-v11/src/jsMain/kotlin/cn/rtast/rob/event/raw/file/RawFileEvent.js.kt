/*
 * Copyright © 2026 RTAkland
 * Date: 2026/1/4 00:55
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */

package cn.rtast.rob.event.raw.file

import cn.rtast.cfworker.client.fetch
import cn.rtast.cfworker.util.toByteArray
import cn.rtast.rob.exceptions.UnsupportedOperationException
import kotlinx.coroutines.await
import kotlinx.io.files.Path
import org.w3c.fetch.RequestInit

internal actual suspend fun saveFile(path: Path, bytes: ByteArray): Path {
    throw UnsupportedOperationException("不支持在Cloudflare worker平台中保存文件")
}

internal actual suspend fun readBytes(url: String): ByteArray {
    return fetch(url, RequestInit("GET")).await().arrayBuffer().await().toByteArray()
}