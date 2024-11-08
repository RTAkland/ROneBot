/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.entity.*
import cn.rtast.rob.entity.custom.ErrorEvent
import cn.rtast.rob.segment.AT
import cn.rtast.rob.segment.Face
import cn.rtast.rob.segment.Image
import cn.rtast.rob.segment.NewLine
import cn.rtast.rob.segment.Text
import cn.rtast.rob.util.ob.OneBotListener
import cn.rtast.rob.util.ob.plus

class TestClient : OneBotListener {

    override suspend fun onGroupMessage(message: GroupMessage, json: String) {
        message.reply(
            AT(3458671395) +
                    Text("114514") +
                    Image("https://static.rtast.cn/images/%E5%8F%88%E6%8B%8D%E4%BA%91_logo2.png") +
                    Face(666) +
                    NewLine()
        )
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
    ROneBotFactory.interceptor = CustomInterceptor()
    instance1.addListeningGroups(985927054)
    commands.forEach { ROneBotFactory.commandManager.register(it) }
    permissionCommands.forEach { ROneBotFactory.commandManager.register(it) }
}