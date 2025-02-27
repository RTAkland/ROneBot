/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/11/11
 */


package qbot

import cn.rtast.rob.qqbot.QBotFactory
import cn.rtast.rob.qqbot.entity.inbound.C2CMessageCreateEvent
import cn.rtast.rob.qqbot.entity.inbound.FriendAddEvent
import cn.rtast.rob.qqbot.entity.inbound.GroupAtMessageCreateEvent
import cn.rtast.rob.qqbot.qbot.QQBotListener

class Bot : QQBotListener {
    override suspend fun onGroupMessage(message: GroupAtMessageCreateEvent) {

    }

    override suspend fun onC2CMessage(message: C2CMessageCreateEvent) {
        message.reply("111")
    }

    override suspend fun onFriendAdd(event: FriendAddEvent) {
        println(event)
    }

}

suspend fun main() {
    val appId = System.getenv("QQ_APP_ID")
    val clientSecret = System.getenv("QQ_APP_SECRET")
    QBotFactory.commandManager.register(TestCommand())
    QBotFactory.createServer(8080, appId, clientSecret, Bot())
}