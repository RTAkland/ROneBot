/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/12
 */

package cn.rtast.rob.benchmark.commands.func

import cn.rtast.rob.annotations.command.functional.GroupCommandHandler
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.event.raw.text
import kotlinx.coroutines.delay
import kotlin.random.Random

@GroupCommandHandler(["/delay"])
public suspend fun delayCommand(message: GroupMessage) {
    delay(Random.nextLong(100, 700))
    println("delay ${message.text}")
}