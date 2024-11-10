/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */


package cn.rtast.rob.kritor.kritor

import cn.rtast.rob.SendAction
import cn.rtast.rob.kritor.BotInstance
import io.kritor.common.Scene
import io.kritor.common.contact
import io.kritor.message.MessageServiceGrpcKt
import io.kritor.message.sendMessageRequest

class KritorAction internal constructor(
    private val botInstance: BotInstance
) : SendAction {

    private val stub = MessageServiceGrpcKt.MessageServiceCoroutineStub(botInstance.interceptedChannel)

    override suspend fun send(message: Any) {}

    override suspend fun send(message: String) {
    }

    suspend fun sendGroupMessage(groupId: Long, message: MessageChain) {
        stub.sendMessage(sendMessageRequest {
            this.contact = contact {
                this.scene = Scene.GROUP
                this.peer = groupId.toString()
            }
            this.elements.addAll(message.elements)
        })
    }
}