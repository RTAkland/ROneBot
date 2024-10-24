/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/29
 */

package rob

import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.util.BaseCommand
import cn.rtast.rob.util.ob.OneBotListener
import kotlinx.coroutines.delay

class EchoCommand : BaseCommand() {
    // A simple echo message command
    override val commandNames = listOf("/echo", "/eee")

    override suspend fun executeGroup(listener: OneBotListener, message: GroupMessage, args: List<String>) {
        message.reply(args.joinToString(" "))
    }
}

class DelayCommand : BaseCommand() {
    override val commandNames = listOf("d", "/d")

    override suspend fun executeGroup(listener: OneBotListener, message: GroupMessage, args: List<String>) {
        delay(3000L)
        message.reply("延迟3秒")
        println("延迟3秒")
    }
}

class MatchedCommand : BaseCommand() {
    override val commandNames = listOf("match", "matches")

    override suspend fun executeGroup(
        listener: OneBotListener,
        message: GroupMessage,
        args: List<String>,
        matchedCommand: String
    ) {
        println(matchedCommand)
    }
}