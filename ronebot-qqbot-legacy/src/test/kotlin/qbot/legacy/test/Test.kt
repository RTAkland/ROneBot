/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/12
 */


package qbot.legacy.test

import cn.rtast.rob.qqbot.legacy.QQBotFactory

suspend fun main() {
    val appId = System.getenv("QQ_APP_ID")
    val clientSecret = System.getenv("QQ_SECRET")
    QQBotFactory.createClient(appId, clientSecret)
}