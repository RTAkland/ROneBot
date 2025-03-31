/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/31
 */

package cn.rtast.rob.starter.backend.util

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText

object Http {

    val client: HttpClient = HttpClient(CIO)

    suspend inline fun <reified T> get(url: String): T {
        return client.get(url).bodyAsText().fromJson<T>()
    }
}