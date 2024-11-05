/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.entity.custom.ErrorEvent
import cn.rtast.rob.enums.AIRecordCharacter
import cn.rtast.rob.util.ob.MessageChain
import cn.rtast.rob.util.ob.OneBotListener
import kotlin.time.Duration.Companion.seconds

class TestClient : OneBotListener {

    override suspend fun onGroupMessage(message: GroupMessage, json: String) {
        val a = message.action.getAIRecord(
            message.groupId,
            AIRecordCharacter.XiaoXin,
            "爱发奶龙的小朋友们, 你们好啊，你要是再发你那死妈奶龙我直接引爆你的手机"
        )
        println(a)
        message.action.sendGroupMessage(message.groupId, MessageChain.Builder().addRecord(a!!).build())
    }

    override suspend fun onWebsocketErrorEvent(event: ErrorEvent) {
        event.exception.printStackTrace()
    }
}

val commands = listOf(
    EchoCommand(), DelayCommand(), MatchedCommand(),
)
val permissionCommands = listOf(
    PCommand()
)

suspend fun main() {
    val client = TestClient()
//    val wsAddress = "ws://127.0.0.1:7767"
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken, client)
    instance1.addListeningGroups(985927054)
    commands.forEach { ROneBotFactory.commandManager.register(it) }
    permissionCommands.forEach { ROneBotFactory.commandManager.register(it) }
}