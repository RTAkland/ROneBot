/*
 * Copyright © 2025 RTAkland
 * Date: 11/19/25, 7:07 PM
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(ExperimentalJsExport::class, ExperimentalTime::class, DelicateCoroutinesApi::class)

package cn.rtast.rob

import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import org.w3c.fetch.Request
import org.w3c.fetch.Response
import kotlin.js.Promise
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@JsExport
@JsName("setGlobalWebSocket")
public fun setGlobalWebSocket(ws: dynamic) {
    globalWebSocket = ws
}

@JsExport
@JsName("globalWebSocket")
public var globalWebSocket: dynamic = null

@JsExport
public object CloudflareWorkerFactory {
    public fun fetch(request: Request, env: dynamic, ctx: dynamic): Promise<Response> = GlobalScope.promise {

        return@promise Response("OK ${Clock.System.now().epochSeconds}")
    }
}