/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/29
 */


package cn.rtast.rob.kritor.util

import cn.rtast.rob.kritor.BotInstance
import io.kritor.event.EventServiceGrpcKt
import io.kritor.event.EventStructure
import io.kritor.event.EventType
import io.kritor.event.requestPushEvent

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

    private fun EventStructure.onMessage() {
        println(this)
    }

    private fun EventStructure.onNotice() {

    }

    private fun EventStructure.onRequest() {

    }

    private fun EventStructure.onCoreEvent() {

    }
}