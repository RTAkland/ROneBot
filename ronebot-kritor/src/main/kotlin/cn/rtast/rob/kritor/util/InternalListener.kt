/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */


package cn.rtast.rob.kritor.util

import cn.rtast.rob.kritor.BotInstance
import cn.rtast.rob.kritor.util.msg.MessageChain
import io.kritor.event.EventServiceGrpcKt
import io.kritor.event.EventStructure
import io.kritor.event.EventType
import io.kritor.event.requestPushEvent
import java.net.URI
import java.util.Base64

class InternalListener internal constructor(
    private val botInstance: BotInstance
) {
    suspend fun init(): InternalListener {
        EventServiceGrpcKt.EventServiceCoroutineStub(botInstance.interceptedChannel).registerActiveListener(
            requestPushEvent { type = EventType.EVENT_TYPE_MESSAGE }
        ).collect { it.onMessage() }
        EventServiceGrpcKt.EventServiceCoroutineStub(botInstance.interceptedChannel).registerActiveListener(
            requestPushEvent { type = EventType.EVENT_TYPE_NOTICE }
        ).collect { it.onNotice() }
        EventServiceGrpcKt.EventServiceCoroutineStub(botInstance.interceptedChannel).registerActiveListener(
            requestPushEvent { type = EventType.EVENT_TYPE_REQUEST }
        ).collect { it.onRequest() }
        EventServiceGrpcKt.EventServiceCoroutineStub(botInstance.interceptedChannel).registerActiveListener(
            requestPushEvent { type = EventType.EVENT_TYPE_CORE_EVENT }
        ).collect { it.onCoreEvent() }
        return this
    }

    private suspend fun EventStructure.onMessage() {
        val url = Base64.getEncoder().encodeToString(URI("https://static.rtast.cn/images/%E5%8F%88%E6%8B%8D%E4%BA%91_logo7.png").toURL().readBytes())
        val msg = MessageChain.Builder().addImage(url).build()
        botInstance.action.sendGroupMessage(985927054, msg)
        println(this)
    }

    private suspend fun EventStructure.onNotice() {

    }

    private suspend fun EventStructure.onRequest() {

    }

    private suspend fun EventStructure.onCoreEvent() {

    }
}