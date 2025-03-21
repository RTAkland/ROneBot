/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/28
 */


package test

import cn.rtast.rob.OneBotFactory

suspend fun main() {
    val bot = OneBotFactory.createServer(8888, "114514ghpA@")
    println(bot.isActionInitialized)
}