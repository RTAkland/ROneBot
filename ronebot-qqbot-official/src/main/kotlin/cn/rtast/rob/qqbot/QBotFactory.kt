/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */


package cn.rtast.rob.qqbot

import cn.rtast.rob.BotFactory
import cn.rtast.rob.qqbot.util.HttpServer

object QBotFactory : BotFactory {

    fun createServer(port: Int, appId: String, clientSecret: String) {
        HttpServer(port, clientSecret).apply { startHttpServer() }
    }
}