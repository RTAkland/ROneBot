/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */


package qbot

import cn.rtast.rob.qqbot.QBotFactory

fun main() {
    QBotFactory.createServer(8080, System.getenv("QQ_APP_ID"), System.getenv("QQ_APP_SECRET"))
}