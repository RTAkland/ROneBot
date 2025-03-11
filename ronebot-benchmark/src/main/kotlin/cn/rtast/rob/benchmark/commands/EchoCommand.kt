/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/3/12
 */

package cn.rtast.rob.benchmark.commands

import cn.rtast.rob.command.BaseCommand
import cn.rtast.rob.event.raw.GroupMessage
import cn.rtast.rob.event.raw.text

public class EchoCommand : BaseCommand() {
    override val commandNames: List<String> = listOf("/echo")

    override suspend fun executeGroup(message: GroupMessage, args: List<String>) {
        println("echo ${message.text}")
    }
}