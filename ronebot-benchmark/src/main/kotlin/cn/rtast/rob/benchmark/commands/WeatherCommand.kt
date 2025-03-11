/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/12
 */

package cn.rtast.rob.benchmark.commands

import cn.rtast.rob.command.BaseCommand
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.event.raw.text
import kotlinx.coroutines.delay
import kotlin.random.Random

public class WeatherCommand : BaseCommand() {
    override val commandNames: List<String> = listOf("/weather")

    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        delay(Random.nextLong(1000, 1600))
        println("weather ${message.text}")
    }
}