/*
 * Copyright © 2025 RTAkland
 * Author: RTAkland
 * Date: 2025/2/27
 */

package qbot

import cn.rtast.rob.entity.IMessageChain
import cn.rtast.rob.qqbot.command.BaseCommand
import cn.rtast.rob.qqbot.entity.inbound.C2CMessageCreateEvent
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent

class TestCommand: BaseCommand() {
    override val commandNames = listOf("/测试")

    override suspend fun executeGroup(message: GroupAtMessageCreateEvent, args: List<String>) {
        println(message.d.content)
        println("111")
    }

    override suspend fun C2CMessageCreateEvent.reject(reason: IMessageChain) {
        TODO("Not yet implemented")
    }

    override suspend fun GroupAtMessageCreateEvent.reject(reason: IMessageChain) {
        TODO("Not yet implemented")
    }

    override suspend fun onGroupSession(msg: GroupAtMessageCreateEvent) {
        TODO("Not yet implemented")
    }

    override suspend fun onPrivateSession(msg: C2CMessageCreateEvent) {
        TODO("Not yet implemented")
    }

    override suspend fun C2CMessageCreateEvent.skipSession() {
        TODO("Not yet implemented")
    }

    override suspend fun GroupAtMessageCreateEvent.skipSession() {
        TODO("Not yet implemented")
    }

    override suspend fun GroupAtMessageCreateEvent.startSession() {
        TODO("Not yet implemented")
    }

    override suspend fun C2CMessageCreateEvent.startSession() {
        TODO("Not yet implemented")
    }
}