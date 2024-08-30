/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/29
 */


import cn.rtast.rob.entity.GroupMessage
import cn.rtast.rob.util.BaseCommand
import cn.rtast.rob.util.ob.OBMessage

class EchoCommand : BaseCommand() {
    // A simple echo message command
    override val commandNames = listOf("/echo", "/eee")

    override suspend fun executeGroup(listener: OBMessage, message: GroupMessage, args: List<String>) {
        listener.sendGroupMessage(message.groupId, args.joinToString(" "))
    }
}