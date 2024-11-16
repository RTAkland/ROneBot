/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */

@file:Suppress("unused")

package cn.rtast.rob.qqbot.qbot

import cn.rtast.rob.common.ext.Http
import cn.rtast.rob.common.ext.SendActionExt
import cn.rtast.rob.qqbot.ACCESS_TOKEN_URL
import cn.rtast.rob.qqbot.BotInstance
import cn.rtast.rob.qqbot.ROOT_API_URL
import cn.rtast.rob.qqbot.entity.Keyboard
import cn.rtast.rob.qqbot.entity.Markdown
import cn.rtast.rob.qqbot.entity.internal.GetAccessTokenPayload
import cn.rtast.rob.qqbot.entity.internal.GetAccessTokenResponse
import cn.rtast.rob.qqbot.entity.outbound.SendKeyboardMessage
import cn.rtast.rob.qqbot.entity.outbound.SendMarkdownMessage
import cn.rtast.rob.qqbot.entity.outbound.SendPlainTextMessage
import cn.rtast.rob.util.toJson

class QQBotAction internal constructor(
    private val appId: String,
    private val clientSecret: String,
    private val botInstance: BotInstance,
) : SendActionExt {

    private var _count: Int = 0

    internal var messageSeq: Int
        get() {
            _count += 1
            return _count
        }
        private set(value) {
            _count = value
        }

    internal fun getAccessToken(): String {
        val payload = GetAccessTokenPayload(appId, clientSecret).toJson()
        val response = Http.post<GetAccessTokenResponse>(ACCESS_TOKEN_URL, payload)
        return response.accessToken
    }

    override suspend fun send(api: String, payload: Any?): String {
        val newPayload = payload?.toJson() ?: "{}"
        val response = Http.post(
            "$ROOT_API_URL/$api", newPayload,
            headers = mapOf("Authorization" to "QQBot ${this.getAccessToken()}")
        )
        return response
    }

    suspend fun sendPrivatePlainTextMessage(
        openId: String,
        content: String,
        eventId: String,
        msgId: String,
    ) {
        val payload = SendPlainTextMessage(content, eventId, msgId, messageSeq)
        this.send("v2/users/$openId/messages", payload)
    }

    suspend fun sendPrivateMarkdownMessage(
        openId: String,
        content: Markdown,
        eventId: String,
        msgId: String,
    ) {
        val payload = SendMarkdownMessage(content, eventId, msgId, messageSeq)
        this.send("v2/users/$openId/messages", payload)
    }

    suspend fun sendPrivateKeyboardMessage(
        openId: String,
        content: Keyboard,
        eventId: String,
        msgId: String,
    ) {
        val payload = SendKeyboardMessage(content, eventId, msgId, messageSeq)
        this.send("v2/users/$openId/messages", payload)
    }

    suspend fun sendGroupPlainTextMessage(openId: String, content: String, eventId: String, msgId: String) {
        val payload = SendPlainTextMessage(content, eventId, msgId, messageSeq)
        this.send("v2/groups/$openId/messages", payload)
    }

    suspend fun sendGroupMarkdownMessage(
        openId: String,
        content: Markdown,
        eventId: String,
        msgId: String,
    ) {
        val payload = SendMarkdownMessage(content, eventId, msgId, messageSeq)
        this.send("v2/groups/$openId/messages", payload)
    }

    suspend fun sendGroupKeyboardMessage(
        openId: String,
        content: Keyboard,
        eventId: String,
        msgId: String,
    ) {
        val payload = SendKeyboardMessage(content, eventId, msgId, messageSeq)
        this.send("v2/groups/$openId/messages", payload)
    }
}