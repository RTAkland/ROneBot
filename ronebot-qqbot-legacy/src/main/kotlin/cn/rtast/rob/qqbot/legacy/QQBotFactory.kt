/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */


package cn.rtast.rob.qqbot.legacy

import cn.rtast.rob.BotFactory
import cn.rtast.rob.common.ext.Http
import cn.rtast.rob.qqbot.legacy.entity.inbound.GetAccessToken
import cn.rtast.rob.qqbot.legacy.entity.outbound.GetAccessTokenOutbound
import cn.rtast.rob.util.toJson

object QQBotFactory : BotFactory {

    suspend fun createClient(appId: String, clientSecret: String): BotInstance {
        val payload = GetAccessTokenOutbound(appId, clientSecret).toJson()
        val accessToken = Http.post<GetAccessToken>(GET_ACCESS_TOKEN_URL, payload).accessToken
        val gatewayAddress = Http.get("$BASE_API_URL/gateway", headers = mapOf("Authorization" to "QQBot $accessToken"))
        println(gatewayAddress)
        return BotInstance(accessToken, gatewayAddress).createBot()
    }
}