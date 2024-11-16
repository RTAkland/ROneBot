/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/15
 */


package cn.rtast.rob.qqbot.qbot

import cn.rtast.rob.common.ext.Http
import cn.rtast.rob.common.ext.SendActionExt
import cn.rtast.rob.qqbot.ACCESS_TOKEN_URL
import cn.rtast.rob.qqbot.BotInstance
import cn.rtast.rob.qqbot.ROOT_API_URL
import cn.rtast.rob.qqbot.entity.Markdown
import cn.rtast.rob.qqbot.entity.internal.GetAccessTokenPayload
import cn.rtast.rob.qqbot.entity.internal.GetAccessTokenResponse
import cn.rtast.rob.qqbot.entity.outbound.SendPrivateMarkdownMessage
import cn.rtast.rob.qqbot.entity.outbound.SendPrivatePlainTextMessage
import cn.rtast.rob.util.toJson

class QQBotAction internal constructor(
    private val appId: String,
    private val clientSecret: String,
    private val botInstance: BotInstance,
) : SendActionExt {

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
        msgSeq: Int = 1
    ) {
        val payload = SendPrivatePlainTextMessage(content, eventId, msgId, msgSeq)
        this.send("v2/users/$openId/messages", payload)
    }

    suspend fun sendPrivateMarkdownMessage(
        openId: String,
        content: Markdown,
        eventId: String,
        msgId: String,
        msgSeq: Int = 1
    ) {
        val payload = SendPrivateMarkdownMessage(content, eventId, msgId, msgSeq)
        this.send("v2/users/$openId/messages", payload)
    }
}