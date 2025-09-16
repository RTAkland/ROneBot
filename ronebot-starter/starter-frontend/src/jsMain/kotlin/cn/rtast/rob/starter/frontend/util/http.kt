/*
 * Copyright © 2025 RTAkland & 小满1221
 * Date: 9/16/25, 12:37 AM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


package cn.rtast.rob.starter.frontend.util

import cn.rtast.rob.starter.frontend.backend
import dev.fritz2.remote.Request
import dev.fritz2.remote.http

public fun httpRequest(url: String): Request = http(backend + url)

public fun extHttp(url: String): Request = http(url)

public fun Request.jsonContentType(): Request = this.contentType("application/json")

public inline fun <reified T> Request.body(body: T): Request = this.body(body.toJson())

public inline fun <reified T> Request.setBody(body: T): Request = body(body)