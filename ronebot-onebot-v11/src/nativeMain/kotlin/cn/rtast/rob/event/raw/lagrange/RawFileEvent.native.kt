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

private val client = HttpClient(CIO)

internal actual suspend fun readBytes(url: String): ByteArray {
    return client.get(url).bodyAsBytes()
}