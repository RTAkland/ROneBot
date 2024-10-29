/*
 * Copyright © 2024 RTAkland
 * Author: RTAkland
 * Date: 2024/10/27
 */


package cn.rtast.rob.kritor

import io.grpc.Channel
import io.grpc.ManagedChannelBuilder
import io.grpc.Metadata
import io.kritor.authentication.AuthenticationServiceGrpcKt
import io.kritor.authentication.authenticateRequest
import io.kritor.common.Element.ElementType
import io.kritor.common.Scene
import io.kritor.common.contact
import io.kritor.common.element
import io.kritor.common.textElement
import io.kritor.event.EventServiceGrpcKt
import io.kritor.event.EventType
import io.kritor.event.requestPushEvent
import io.kritor.message.MessageServiceGrpcKt
import io.kritor.message.sendMessageRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.asExecutor

suspend fun await(channel: Channel) {
    val stub = AuthenticationServiceGrpcKt.AuthenticationServiceCoroutineStub(channel)
    val rsp = stub.authenticate(authenticateRequest {
        account = "3781274982"
        ticket = "114514ghpA@"
    })
    println(rsp)
}

private val authMetadataKey = Metadata.Key.of("Authorization", Metadata.ASCII_STRING_MARSHALLER)

//fun createAuthInterceptor(apiToken: String): ClientInterceptor {
//    val metadata = Metadata().apply {
//        put(authMetadataKey, "Bearer $apiToken")
//    }
//    return MetadataUtils.newAttachHeadersInterceptor(metadata)
//}

suspend fun main() {
    val channel = ManagedChannelBuilder
        .forAddress("127.0.0.1", 9000)
        .usePlaintext()
        .enableRetry() // 允许尝试
        .executor(Dispatchers.IO.asExecutor()) // 使用协程的调度器
        .build()
//    val authInterceptor = createAuthInterceptor("114514ghpA@")
//    val interceptedChannel = intercept(channel, authInterceptor)
    val stub = MessageServiceGrpcKt.MessageServiceCoroutineStub(channel)
    val metadata = Metadata().apply {
        put(authMetadataKey, "Bearer 114514ghpA@")
    }
    stub.sendMessage(sendMessageRequest {
        this.contact = contact {
            this.scene = Scene.GROUP
            this.peer = "985927054"
        }
        this.elements.add(element {
            type = ElementType.TEXT
            text = textElement {
                this.text = "114514"
            }
        })
    }, metadata)
    EventServiceGrpcKt.EventServiceCoroutineStub(channel).registerActiveListener(
        requestPushEvent { type = EventType.EVENT_TYPE_MESSAGE }
    ).collect { println(it) }

    EventServiceGrpcKt.EventServiceCoroutineStub(channel).registerActiveListener(
        requestPushEvent { type = EventType.EVENT_TYPE_NOTICE }
    ).collect { println(it) }

    EventServiceGrpcKt.EventServiceCoroutineStub(channel).registerActiveListener(
        requestPushEvent { type = EventType.EVENT_TYPE_CORE_EVENT }
    ).collect { println(it) }
}