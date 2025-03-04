/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/8/28
 */

package test

import cn.rtast.rob.ROneBotFactory
import cn.rtast.rob.event.events.GroupMessageEvent
import cn.rtast.rob.event.listener.registerEvent
import cn.rtast.rob.event.onEvent
import cn.rtast.rob.onebot.dsl.messageChain
import cn.rtast.rob.onebot.dsl.text
import cn.rtast.rob.segment.Text

suspend fun main() {
    val wsAddress = System.getenv("WS_ADDRESS")
    val wsAccessToken = System.getenv("WS_ACCESS_TOKEN")
    val instance1 = ROneBotFactory.createClient(wsAddress, wsAccessToken)
    instance1.addListeningGroup(985927054)
    instance1.onEvent<GroupMessageEvent> {
        it.message.sendMessage("这是一段纯文本")
        it.message.sendMessage(Text("这是一个消息段纯文本"))
        it.message.sendMessage(messageChain {
            text("这是一段消息链纯文本")
        })
    }
    instance1.listeners {
        registerEvent<GroupMessageEvent> {
            println(it)
        }
    }
}