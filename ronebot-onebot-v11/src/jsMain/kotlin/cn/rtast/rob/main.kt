/*
 * Copyright © 2026 RTAkland
 * Date: 2026/1/4 01:35
 * Open Source Under Apache-2.0 License
 * https://www.apache.org/licenses/LICENSE-2.0
 */


@file:OptIn(DelicateCoroutinesApi::class, ExperimentalJsExport::class)

/**
 * Test file
 */

package cn.rtast.rob

import cn.rtast.rob.event.raw.message.GroupMessage
import cn.rtast.rob.event.raw.message.text
import cn.rtast.rob.onebot.OneBotListener
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.promise
import org.w3c.dom.events.EventListener
import org.w3c.fetch.Request
import org.w3c.fetch.Response
import kotlin.js.Promise

public fun main() {
    @Suppress("unused_expression")
    val eventListener = EventListener { event ->
        val dyn = event.asDynamic()
        event.asDynamic().respondWith(handleRequest(dyn.request as Request))
        Unit
    }
    js("addEventListener('fetch', eventListener)")
}

@JsExport
public fun handleRequest(request: Request): Promise<Response> = GlobalScope.promise {
    OneBotFactory.createWorkerBot("114514", object : OneBotListener{
        override suspend fun onGroupMessage(message: GroupMessage) {
            if (message.text.contains("Hi")) {
                message.reply("Hello")
            }
        }
    })
    return@promise workerApplication.handle(request)
}