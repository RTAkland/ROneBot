/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/12
 */

package cn.rtast.rob.benchmark

import cn.rtast.rob.OneBotFactory
import cn.rtast.rob.benchmark.commands.EchoCommand
import cn.rtast.rob.benchmark.commands.WeatherCommand

public suspend fun main() {
    val bot = OneBotFactory.createServer(9090, "114514")
    OneBotFactory.commandManager.register(EchoCommand())
    OneBotFactory.commandManager.register(WeatherCommand())
}