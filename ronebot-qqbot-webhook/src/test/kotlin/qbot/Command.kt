/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package qbot

import cn.rtast.rob.qqbot.command.BaseCommand
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent

class TestCommand: BaseCommand() {
    override val commandNames = listOf("/测试")

    override suspend fun executeGroup(message: GroupAtMessageCreateEvent, args: List<String>) {
        println(message.d.content)
        println("111")
    }
}