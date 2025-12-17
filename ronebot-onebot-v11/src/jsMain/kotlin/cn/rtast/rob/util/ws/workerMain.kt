/*
 * Copyright © 2025 RTAkland
 * Date: 2025/12/16 17:11
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(ExperimentalJsExport::class)

package cn.rtast.rob.util.ws

import org.w3c.dom.WebSocket
import org.w3c.dom.url.URL
import org.w3c.fetch.Request
import org.w3c.fetch.Response
import org.w3c.fetch.ResponseInit
import kotlin.js.Promise

@JsExport
public fun fetch(request: Request, env: dynamic, ctx: dynamic): Promise<Response> {
    val url = URL(request.url)
    return when (url.pathname) {
        "/rob" -> websocketHandler(request)
        else -> Promise.resolve(Response("Not found"))
    }
}

private fun websocketHandler(request: Request): Promise<Response> {
    val upgrade = request.headers.get("Upgrade")
    if (upgrade != "websocket") return Promise.resolve(Response("Expected websocket", ResponseInit(status = 400)))
    val pair = js("new WebsocketPair()")
    val client = pair[0] as WebSocket
    val server = pair[1] as WebSocket
    handleSession(server)
    return Promise.resolve(Response(body = null))
}

private fun handleSession(webSocket: dynamic) {
    webSocket.accept()
    webSocket.addEventListener("message") { event ->
        val data = event.data as String
        webSocket.send(data)
    }

    webSocket.addEventListener("close") { evt ->
        console.log("WebSocket closed", evt)
    }
}